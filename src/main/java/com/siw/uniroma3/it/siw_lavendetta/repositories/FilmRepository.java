package com.siw.uniroma3.it.siw_lavendetta.repositories;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByTitleContainingIgnoreCase(String title);

    List<Film> findByYear(int year);

    @Query("SELECT f FROM Film f WHERE f.director.id = :directorId")
    List<Film> findByDirector(@Param("directorId") Long directorId);
}
