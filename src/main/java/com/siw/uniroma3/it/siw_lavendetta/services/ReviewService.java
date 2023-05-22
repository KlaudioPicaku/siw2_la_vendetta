package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> findAllByRatingForFilm(Film film, Integer Rating);

    List<Review> findAllByUser(User user);

    List<Review> findAllByFilm(Film film);

    Review save(Review review);

    void saveOrUpdate(Review review);

    void delete(Long id);

    List<Review> findAll();

    List<Review> findAllByRating(Integer rating);

    Optional<Review> findById(Long id);

    List<Review> findAllByFilmAndUser(Film film, Optional<User> user);

    List<Review> findAllByRatingAndUser(Integer rating, Optional<User> user);

    List<Review> findAllByUserAndRatingForFilm(Film film, Integer rating, Optional<User> user);
}
