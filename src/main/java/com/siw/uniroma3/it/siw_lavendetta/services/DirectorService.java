package com.siw.uniroma3.it.siw_lavendetta.services;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorService {

    List<Director> findAll();
    Optional<Director> findById(Long id);
    List<Director> findByName(String name);
    void saveOrUpdate(Director director);
    void delete(Long id);

    void deleteDirectorImage(String absoluteImageUrl);

    List<Director> searchByTerm(String term);
}
