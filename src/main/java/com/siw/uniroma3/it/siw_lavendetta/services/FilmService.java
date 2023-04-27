package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
        List<Film> findAll();

        Optional<Film> findById(Long id);
        List<Film> findByTitle(String title);
        List<Film> findByYear(Integer year);
        void saveOrUpdate(Film film);
        void delete(Long id);
    }
