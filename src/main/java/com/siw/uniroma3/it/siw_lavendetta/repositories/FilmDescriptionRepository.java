package com.siw.uniroma3.it.siw_lavendetta.repositories;
import com.siw.uniroma3.it.siw_lavendetta.models.FilmDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmDescriptionRepository extends JpaRepository<FilmDescription,Long> {
    Optional<FilmDescription> findByFilmId(Long filmId);

}
