package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {


    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    @Override
    public List<Film> findByTitle(String title) {
        return filmRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Film> findByReleaseYear(Integer year) {
        return filmRepository.findByReleaseYear(year);
    }

    @Override
    public void saveOrUpdate(Film film) {
        filmRepository.save(film);
    }

    @Override
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}