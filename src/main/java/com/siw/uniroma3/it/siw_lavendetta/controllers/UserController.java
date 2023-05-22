package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.dto.UserDto;
import com.siw.uniroma3.it.siw_lavendetta.dto.UserProfileDto;
import com.siw.uniroma3.it.siw_lavendetta.impl.UserServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.impl.token.VerificationTokenImpl;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.ReviewPublic;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.models.tokens.VerificationToken;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import com.siw.uniroma3.it.siw_lavendetta.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

//    @Autowired
//    private VerificationTokenImpl verificationToken;

    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private FilmService filmService;

    @Autowired
    private ReviewService reviewService;



    @GetMapping("/verify-token")
    public String verifyToken(@RequestParam("token") String token) {
        VerificationToken verificationTokenLocal = verificationTokenService.findByToken(token);
        if (verificationTokenLocal == null || !verificationTokenLocal.isValid()) {
            // Handle invalid token
            return "redirect:/activation/failed?token="+token;
        } else {
            User user = verificationTokenLocal.getUser();
            user.setEnabled(true);
            userService.save(user);
            verificationTokenLocal.burnToken();
            verificationTokenService.saveOrUpdate(verificationTokenLocal);
            // Handle successful verification
            return "redirect:/activation/success?token=" + token;
        }
    }

    @GetMapping("/profile")
    public String getProfile(@RequestParam(name = "page") int page,
                             @RequestParam(name = "film", required = false) Long filmId,
                             @RequestParam(name = "rating", required = false) Integer rating,
                             @RequestParam(name = "order", defaultValue = "asc") String order,
                             Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user=userService.getUserByUsername(auth.getName());
        if(!user.isPresent()){
            return "error/404";
        }
        User userLocal=user.get();
        UserProfileDto userPublic = new UserProfileDto(userLocal.getUsername(),userLocal.getImage(),userLocal.getFirstName(), userLocal.getLastName());
        Film film=null;
        if(filmId!=null) {
            film = filmService.findById(filmId).orElse(null);
//            System.out.println(film.toString());
        }

        List<Review> reviews= new ArrayList<>();
        if (page<1){
            return  "error/422";
        }
        if (rating == null && film != null) {
            reviews = reviewService.findAllByFilmAndUser(film,user);
            model.addAttribute("currentFilm", film);
            model.addAttribute("currentRating", null);
        } else if (rating != null && film == null){
            reviews = reviewService.findAllByRatingAndUser(rating,user);
            model.addAttribute("currentFilm", null);
        }else if(rating !=null && film !=null) {
            reviews=reviewService.findAllByUserAndRatingForFilm(film,rating,user);
            model.addAttribute("currentFilm", film);

        }else{
            reviews=reviewService.findAllByUser(user.get());
            model.addAttribute("currentFilm", null);
        }

        // ordinamento per data di creazione
        if (order.equals("asc") && !reviews.isEmpty()) {
            reviews.sort(Comparator.comparing(Review::getCreatedOn));
        } else if (order.equals("desc") && !reviews.isEmpty()) {
            reviews.sort(Comparator.comparing(Review::getCreatedOn).reversed());

        }

        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, reviews.size());

        List<Review> reviewSublist;
        if (startIndex <= endIndex) {
            reviewSublist=reviews.subList(startIndex, endIndex);
        } else {
            reviewSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(reviewSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) reviews.size() / pageSize);;
        }

        List<ReviewPublic> reviewsPublic = reviewSublist.stream()
                .map(r -> new ReviewPublic(r.getTitle(), r.getBody(), r.getuser().getUsername(), r.getRating(), r.getuser().getImage(),r.getId(),r.getFilm()))
                .collect(Collectors.toList());

        model.addAttribute("reviews",reviewsPublic);
        model.addAttribute("page",page);
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);
        model.addAttribute("currentOrdering",order);
        model.addAttribute("currentRating", rating);
        List<Film> films=filmService.findAll();
        model.addAttribute("films",films);
        model.addAttribute("user",userPublic);
        return "user_profile";
    }

    @GetMapping("/reviews/edit")
    public String editReview(@RequestParam("id") Long id,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Review> review = reviewService.findById(id);
        Optional<User> user=userService.getUserByUsername(auth.getName());
        if(user.isPresent() && review.isPresent() && !(user.get().getUsername().equals(review.get().getuser().getUsername()))){
            return "error/403";
        }
        Review reviewLocal=review.get();
        ReviewPublic reviewPublic=new ReviewPublic(reviewLocal.getTitle(),reviewLocal.getBody(),
                reviewLocal.getuser().getUsername(),reviewLocal.getRating(),reviewLocal.getuser().getImage()
                ,reviewLocal.getId(),reviewLocal.getFilm());
        if(user.isPresent()) {
            User userLocal = user.get();
            UserProfileDto userPublic = new UserProfileDto(userLocal.getUsername(), userLocal.getImage(), userLocal.getFirstName(), userLocal.getLastName());
            System.out.println(reviewPublic.toString());
            model.addAttribute("user", userPublic);
            model.addAttribute("review", reviewPublic);
            return "edit_review";
        }
        else{
            return "error/404";
        }
    }
    @PostMapping("/edit/review")
    public String editReview(@ModelAttribute("review") ReviewPublic review, BindingResult results){
        System.out.println(review.toString());
        if(results.hasErrors()){
            System.out.println(results.getAllErrors());
            return "redirect:/reviews/edit?id="+review.getReviewId();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long reviewID=review.getReviewId();
        Optional<Review> reviewLocal = reviewService.findById(reviewID);
        Optional<User> user=userService.getUserByUsername(auth.getName());
        System.out.println("-------------------");
        System.out.println(reviewLocal.isPresent());
        System.out.println(reviewLocal.get().getuser().getUsername());
        System.out.println(user.get().getUsername());
        System.out.println("------------------");
        if(reviewLocal.isPresent() && !reviewLocal.get().getuser().getUsername().equals(user.get().getUsername())){
            return "error/403";
        }
        reviewLocal.get().setRating(review.getReviewRating());
        reviewLocal.get().setBody(review.getReviewBody());
        reviewLocal.get().setTitle(review.getReviewTitle());
        reviewService.save(reviewLocal.get());
        return "redirect:/profile?page=1&film=&rating=&order=";
    }


}
