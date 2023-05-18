package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.models.*;
import com.siw.uniroma3.it.siw_lavendetta.repositories.*;
import com.siw.uniroma3.it.siw_lavendetta.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private FilmService filmService;

    private DirectorService directorService;


    private ActorService actorService;

    private ReviewService reviewService;

    private UserService userService;

    private FilmRepository filmRepository;

    private DirectorRepository directorRepository;


    private ActorRepository actorRepository;

    private ReviewRepository reviewRepository;

    private UserRepository userRepository;

    @Autowired
    public AdminController(FilmService filmService, DirectorService directorService, ActorService actorService,
                           ReviewService reviewService, UserService userService, FilmRepository filmRepository,
                           DirectorRepository directorRepository, ActorRepository actorRepository,
                           ReviewRepository reviewRepository, UserRepository userRepository) {
        this.filmService = filmService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/panel")
    public String adminPanel(){
        return "admin_panel";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/review/list_view")
    public String getReviewsList(@RequestParam(name = "page") int page,
                                 @RequestParam(name = "film", required = false) Long filmId,
                                 @RequestParam(name = "rating", required = false) Integer rating,
                                 @RequestParam(name = "order", defaultValue = "asc") String order,
                                 Model model) {
//        System.out.println(page);
//        System.out.println(rating);
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
            reviews = reviewService.findAllByFilm(film);
            model.addAttribute("currentFilm", film);
            model.addAttribute("currentRating", null);
        } else if (rating != null && film == null){
            reviews = reviewService.findAllByRating(rating);
            model.addAttribute("currentFilm", null);
        }else if(rating !=null && film !=null) {
            reviews=reviewService.findAllByRatingForFilm(film,rating);
            model.addAttribute("currentFilm", film);

        }else{
            reviews=reviewService.findAll();
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
//        System.out.println(page);
//        System.out.println(rating);
//        System.out.println(order);
//        System.out.println(maxNumberOfPages);
//        System.out.println(reviewSublist.size());
        return "admin_review_list";
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/reviews/delete_confirm")
    public String confirmDeleteReview(@RequestParam("id")Long id,Model model){
        Optional<Review> review= reviewService.findById(id);
        if(!review.isPresent()){
            return "error/404";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user=userService.getUserByUsername(auth.getName());

        boolean isAdmin=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if(!(user.getUsername().equals(review.get().getuser().getUsername())) && !isAdmin){
            return "error/403";
        }
        Review reviewdto=review.get();
        ReviewPublic reviewPublic=new ReviewPublic(reviewdto.getTitle(),reviewdto.getBody(),
                reviewdto.getuser().getUsername(),reviewdto.getRating(),reviewdto.getuser().getImage()
                ,reviewdto.getId(),reviewdto.getFilm());
        model.addAttribute("object", reviewPublic);
        return "review_delete_confirm";

    }
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam("id")Long id){
        Optional<Review> review= reviewService.findById(id);
        if(!review.isPresent()){
            return "error/404";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user=userService.getUserByUsername(auth.getName());

        boolean isAdmin=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if(!(user.getUsername().equals(review.get().getuser().getUsername())) && !isAdmin){
            return "error/403";
        }
        reviewService.delete(review.get().getId());
        return "redirect: /review/list_view?page=1&film=&rating=&order=";
    }

}
