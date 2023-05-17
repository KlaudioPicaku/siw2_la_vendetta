package com.siw.uniroma3.it.siw_lavendetta.repositories;
import com.siw.uniroma3.it.siw_lavendetta.models.FilmDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FilmDescriptionRepository extends JpaRepository<FilmDescription,Long> {
    Optional<FilmDescription> findByFilmId(Long filmId);

}
