package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.FilmImage;

import java.util.List;
import java.util.Optional;

public interface FilmImageService {

    List<FilmImage> findAll();

    void saveOrUpdate(FilmImage filmImage);

    void delete(FilmImage filmImage);

    Optional<FilmImage> findById(Long Id);

    List<FilmImage> findByFilm(Long filmId);
}
