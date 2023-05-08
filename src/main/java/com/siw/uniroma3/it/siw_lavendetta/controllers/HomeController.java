package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private FilmService filmService;

    private UserRepository userRepository;

    @Autowired
    public HomeController(FilmService filmService,UserRepository userRepository) {
        this.filmService = filmService;
        this.userRepository=userRepository;
    }

    @GetMapping(value="")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        List<Film> films = filmService.findAll();
        modelAndView.addObject("films", films);
        modelAndView.setViewName("home");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if (auth != null && auth.isAuthenticated()) {
            modelAndView.addObject("authorization", auth);
             User user= userRepository.findByUsername(auth.getName());
             if (user != null) {
                 modelAndView.addObject("profile_pic", user.getImage());
             }else {
                 modelAndView.addObject("profile_pic", null);
             }
        }

        return modelAndView;
    }
}