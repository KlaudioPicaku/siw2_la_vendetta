package com.siw.uniroma3.it.siw_lavendetta.repositories;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findByTitleContainingIgnoreCase(String title);

    List<Film> findByReleaseYear(Integer year);
}
