package com.siw.uniroma3.it.siw_lavendetta.repositories;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRatingAndFilm(Integer rating, Film film);

    List<Review> findAllByAuthor(User userId);

    List<Review> findAllByFilm(Film film);
}
