package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.FilmDescription;

import java.util.Optional;

public interface FilmDescriptionService {

    void saveOrUpdate(FilmDescription filmDescription);

    Optional<FilmDescription> findByFilmId(Long filmId);
    Optional<FilmDescription> findById(Long id);

}
