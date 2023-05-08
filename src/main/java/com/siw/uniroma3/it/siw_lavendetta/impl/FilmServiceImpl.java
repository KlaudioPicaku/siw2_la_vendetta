package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ActorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.DirectorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("filmServiceImpl")
public class FilmServiceImpl implements FilmService {


    private FilmRepository filmRepository;

    private UserRepository userRepository;

    private ActorRepository actorRepository;

    private DirectorRepository directorRepository;

    public FilmServiceImpl(FilmRepository filmRepository,
                           UserRepository userRepository,
                           ActorRepository actorRepository,
                           DirectorRepository directorRepository) {
        super();
        this.filmRepository = filmRepository;
        this.actorRepository=actorRepository;
        this.directorRepository=directorRepository;
        this.userRepository=userRepository;
    }

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