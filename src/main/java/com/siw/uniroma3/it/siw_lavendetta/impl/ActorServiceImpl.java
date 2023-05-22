package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ActorRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Optional<Actor> findById(Long id) {
        return actorRepository.findById(id);
    }

    @Override
    public List<Actor> findByName(String name) {
        List<Actor> actors=actorRepository.findByFirstNameContainingOrLastNameContaining(name,name);
        return actors;
    }

    @Override
    public void saveOrUpdate(Actor actor) {
        actorRepository.save(actor);

    }

    @Override
    public void delete(Long id) {
        Actor actor=actorRepository.findById(id).get();
        Set<Film> films = actor.getFilms();
        for (Film film : films) {
            film.getActors().remove(actor);
        }
        this.deleteActorImage(actor.getAbsoluteImageUrl());
        actorRepository.deleteById(id);
    }

    @Override
    public void deleteActorImage(String imagePath) {
        String filePath="";
        if(imagePath!=null || !imagePath.isEmpty()){
            filePath=imagePath;
        }
        if(!filePath.isEmpty()){
            String baseDirectory = System.getProperty("user.dir");
            Path path = Paths.get(baseDirectory,filePath);
//            System.out.println(path);

            try {
                Files.delete(path);
                System.out.println("File deleted successfully.");
            } catch (IOException e) {
                System.out.println("Failed to delete the file: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Actor> findByFilm(Film film) {
        return actorRepository.findByFilmsContaining(film);
    }


}
