package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.dto.DirectorDto;
import com.siw.uniroma3.it.siw_lavendetta.dto.FilmDto;
import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.repositories.*;
import com.siw.uniroma3.it.siw_lavendetta.services.ActorService;
import com.siw.uniroma3.it.siw_lavendetta.services.DirectorService;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FilmController {

    private FilmService filmService;

    private DirectorService directorService;


    private ActorService actorService;

    private ReviewService reviewService;

    private FilmRepository filmRepository;

    private DirectorRepository directorRepository;


    private ActorRepository actorRepository;

    private ReviewRepository reviewRepository;

    private UserRepository userRepository;

    private FilmDto filmDto;
    @Autowired
    public FilmController(FilmService filmService, DirectorService directorService,
                          ActorService actorService, ReviewService reviewService,
                          FilmRepository filmRepository, DirectorRepository directorRepository,
                          ActorRepository actorRepository, ReviewRepository reviewRepository,
                          UserRepository userRepository) {
        this.filmService = filmService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.reviewService = reviewService;
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/films/list_view")
    public String adminFilmList(Model model){
        List<Film> films = filmRepository.findAll();
        model.addAttribute("films",films);
        return "admin_film_list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/films/create")
    public String adminFilmCreate(Model model){
        model.addAttribute("film",new FilmDto());
        List<Actor> actors=actorRepository.findAll();
        List<Director> directors= directorRepository.findAll();
        model.addAttribute("directors",directors);
        model.addAttribute("actors",actors);
        return "admin_film_create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/films/create")
    public String adminMovieCreation(@Valid @ModelAttribute("film") FilmDto filmDto, BindingResult result){
        if (result.hasErrors()) {
            return "/admin_film__create";
        }

        return "redirect:/admin/films/list_view";
    }


}
