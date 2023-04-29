package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.repositories.DirectorRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;


    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    public Optional<Director> findById(Long id) {
        return directorRepository.findById(id);
    }

    @Override
    public List<Director> findByName(String name) {
        List<Director> directors = directorRepository.findByFirstNameContainingOrLastNameContaining(name,name);
        return directors;
    }

    @Override
    public void saveOrUpdate(Director director) {
        directorRepository.save(director);
    }

    @Override
    public void delete(Long id) {
        directorRepository.deleteById(id);
    }
}
