package com.siw.uniroma3.it.siw_lavendetta.repositories;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRatingAndFilm(Integer rating, Film film);

    List<Review> findAllByUser(User userId);

    List<Review> findAllByFilm(Film film);

    List<Review> findAllByUserAndFilm(User user, Optional<Film> film);

    List<Review> findAllByRating(Integer rating);

    List<Review> findAllByUserAndRating(User user, Integer rating);


    List<Review> findAllByUserAndRatingAndFilm(User user, Integer rating, Film film);
}
