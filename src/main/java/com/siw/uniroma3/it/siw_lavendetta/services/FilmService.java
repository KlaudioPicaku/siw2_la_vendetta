package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
        List<Film> findAll();

        Optional<Film> findById(Long id);
        List<Film> findByTitle(String title);
        List<Film> findByReleaseYear(Integer year);
        void saveOrUpdate(Film film);
        void delete(Long id);

    List<Film> findByDirector(Director director);

    String getAverageRating(Film film);

    Double getAveragDoubleRating(Film film);

    List<Film> findByActor(Actor actor);

    List<Film> findTopThree();

    List<Film> searchByTerm(String term);
}
