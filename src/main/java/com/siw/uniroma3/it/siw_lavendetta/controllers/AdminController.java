package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.repositories.*;
import com.siw.uniroma3.it.siw_lavendetta.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
