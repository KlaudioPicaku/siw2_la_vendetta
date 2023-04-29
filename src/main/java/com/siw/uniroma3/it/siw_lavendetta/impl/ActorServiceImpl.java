package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ActorRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        actorRepository.deleteById(id);
    }


}
