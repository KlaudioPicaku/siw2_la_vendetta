package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.FilmDescription;
import com.siw.uniroma3.it.siw_lavendetta.models.FilmImage;
import com.siw.uniroma3.it.siw_lavendetta.repositories.*;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmDescriptionService;
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


    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmDescriptionRepository filmDescriptionRepository;
    @Autowired
    private FilmDescriptionService filmDescriptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DirectorRepository directorRepository;


    @Override
    public List<Film> findAll() {return filmRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));}

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

    @Override
    public List<Film> findByDirector(Director director){
        return filmRepository.findByDirector(director);
    }

    public String filmDescriptionByFilm(Film film){
        Optional<FilmDescription> filmDescription=filmDescriptionService.findByFilmId(film.getId());
        if (filmDescription.isPresent()){
            return  filmDescription.get().getBody();
        }
        return "";
    }

}