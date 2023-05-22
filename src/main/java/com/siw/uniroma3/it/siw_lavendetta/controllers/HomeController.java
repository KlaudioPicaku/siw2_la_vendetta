package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.ReviewPublic;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private FilmService filmService;

    private ReviewService reviewService;

    private UserRepository userRepository;

    @Autowired
    public HomeController(FilmService filmService,UserRepository userRepository,ReviewService reviewService) {
        this.filmService = filmService;
        this.userRepository=userRepository;
        this.reviewService=reviewService;
    }

    @GetMapping(value = "")
    public String home(Model model) {
        Map<Film, String> filmRatings = new LinkedHashMap<>();
        Map<Film, ReviewPublic> top3Films = new LinkedHashMap<>();
        List<Film> films = filmService.findAll();

        // Sort
        films.sort(Comparator.comparingInt(Film::getReleaseYear).reversed());

        // prendo i primi 5
        List<Film> top5Films = films.stream().limit(5).collect(Collectors.toList());

        List<Film> top3HighestRated=filmService.findTopThree();
        for(Film f: top3HighestRated){
            List<Review> reviews= reviewService.findAllByFilm(f);
            Random random = new Random();
            int randomIndex = random.nextInt(reviews.size());
            Review randomReview = reviews.get(randomIndex);
//            System.out.println(randomReview.toString());
            ReviewPublic reviewPublic=new ReviewPublic(randomReview.getTitle(),randomReview.getBody(),
                    randomReview.getuser().getUsername(), randomReview.getRating(),
                    randomReview.getuser().getImage(),randomReview.getId(),
                    randomReview.getFilm());
//            System.out.println(reviewPublic.toString());

            top3Films.put(f,reviewPublic);
        }
        System.out.println(top3Films.size());

        top5Films.forEach(film -> {
            String rating = filmService.getAverageRating(film);
            filmRatings.put(film, rating);
        });
//        System.out.println(top3Films);
        model.addAttribute("carouselFilms",top3Films);
        model.addAttribute("filmRatings", filmRatings);

        return "home";
    }
}