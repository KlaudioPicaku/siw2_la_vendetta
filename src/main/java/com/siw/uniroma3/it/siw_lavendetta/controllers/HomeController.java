package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private FilmService filmService;
    @Autowired
    public HomeController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value="")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        List<Film> films = filmService.findAll();
        modelAndView.addObject("films", films);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}