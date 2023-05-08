package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.FilmImage;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmImageRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmImageImpl implements FilmImageService {
    @Autowired
    private FilmImageRepository filmImageRepository;



    @Override
    public List<FilmImage> findAll() {
        return filmImageRepository.findAll();
    }

    @Override
    public void saveOrUpdate(FilmImage filmImage) {
        FilmImage previous= filmImageRepository.findByFilePathContaining(filmImage.getFilePath());
        if (previous != null){
            filmImageRepository.delete(previous);
        }
        filmImageRepository.save(filmImage) ;

    }

    @Override
    public void delete(FilmImage filmImage) {
        filmImageRepository.delete(filmImage);
    }

    @Override
    public Optional<FilmImage> findById(Long Id) {
        return filmImageRepository.findById(Id);
    }

    @Override
    public List<FilmImage> findByFilm(Long filmId) {
        return filmImageRepository.findByFilmId(filmId);
    }
}
