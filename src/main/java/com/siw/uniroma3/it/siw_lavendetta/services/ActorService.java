package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<Actor> findAll();
    Optional<Actor> findById(Long id);
    List<Actor> findByName(String name);
    void saveOrUpdate(Actor actor);
    void delete(Long id);


}
