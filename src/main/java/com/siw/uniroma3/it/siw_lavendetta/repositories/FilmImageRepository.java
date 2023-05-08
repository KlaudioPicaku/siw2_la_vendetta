package com.siw.uniroma3.it.siw_lavendetta.repositories;

import com.siw.uniroma3.it.siw_lavendetta.models.FilmImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmImageRepository extends JpaRepository<FilmImage,Long> {

    public Optional<FilmImage> findById(Long filmId);

    public FilmImage findByFilePathContaining(String title);

    public List<FilmImage> findAll();

    public List<FilmImage> findByFilmId(Long filmId);
}
