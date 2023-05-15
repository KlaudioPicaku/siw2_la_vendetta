package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.dto.FilmRating;
import com.siw.uniroma3.it.siw_lavendetta.dto.ReviewDto;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.ReviewPublic;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import com.siw.uniroma3.it.siw_lavendetta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;




@RestController
public class ReviewController {
    UserService userService;

    ReviewService reviewService;
    FilmService filmService;


    @Autowired
    public ReviewController(UserService userService, ReviewService reviewService, FilmService filmService){
        this.userService=userService;
        this.reviewService=reviewService;
        this.filmService=filmService;

    }


//    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping(path = "/api/reviews/create")
    public ResponseEntity<String> createReview(
            @Valid @ModelAttribute ("review") ReviewDto reviewDto,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Validation errors occurred");
        }
        System.out.println(reviewDto.toString());
        System.out.println(bindingResult.hasErrors());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        boolean isAdminOrUser = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") || role.getAuthority().equals("ROLE_USER"));

        if(!isAdminOrUser){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user=  userService.getUserByUsername(authentication.getName());
        Optional<Film> film= filmService.findById(reviewDto.getFilmId());
        if (film != null && user != null) {
            Review review = new Review(reviewDto.getTitle(), reviewDto.getRating(), reviewDto.getBody(), user, film.get());
            reviewService.save(review);
            System.out.println(review.toString());

        }else{
            return ResponseEntity.badRequest().body("There was a problem fetching the author or film entity ");
        }
        return ResponseEntity.ok("Review saved successfully");
    }
    @GetMapping("/review-count")
    public FilmRating getReviewCount(@RequestParam(name = "film") int filmId){
        double average = reviewService.findAllByFilm(filmService.findById((long) filmId).get()).stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0.0);
        if (average>0) {
            return new FilmRating(String.valueOf(Math.round(average*100.0)/100.0),reviewService.findAllByFilm(filmService.findById((long)filmId).get()).size());
        }

        return new FilmRating("No ratings",0);
    }

    @GetMapping("/api/reviews/load")
    public List<ReviewPublic> reviewDisplay(@RequestParam(name = "page") int page,
                                            @RequestParam(name = "film") int filmId,
                                            @RequestParam(name = "rating", required = false) Integer rating) {
        Film film = filmService.findById((long) filmId).get();
        List<Review> reviews;

        if (rating == null) {
            reviews = reviewService.findAllByFilm(film);
        } else {
            reviews = reviewService.findAllByRatingForFilm(film, rating);
        }

        List<ReviewPublic> reviewsPublic = reviews.stream()
                .map(r -> new ReviewPublic(r.getTitle(), r.getBody(), r.getuser().getUsername(), r.getRating(), r.getuser().getImage()))
                .collect(Collectors.toList());

        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, reviewsPublic.size());
        if(startIndex<=endIndex) {
            return reviewsPublic.subList(startIndex, endIndex);
        }
        else{
            return null;
        }
    }

}
