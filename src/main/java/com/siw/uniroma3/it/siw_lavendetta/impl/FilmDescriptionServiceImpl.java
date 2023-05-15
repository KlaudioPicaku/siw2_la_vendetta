package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.FilmDescription;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmDescriptionRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Qualifier("filmDescriptionServiceImpl")
public class FilmDescriptionServiceImpl implements FilmDescriptionService {
    @Autowired
    private FilmDescriptionRepository filmDescriptionRepository;

    @Override
    public void saveOrUpdate(FilmDescription filmDescription) {
        filmDescriptionRepository.save(filmDescription);
    }

    @Override
    public Optional<FilmDescription> findByFilmId(Long filmId) {
        return filmDescriptionRepository.findByFilmId(filmId);
    }

    @Override
    public Optional<FilmDescription> findById(Long id) {
        return filmDescriptionRepository.findById(id);
    }
}
