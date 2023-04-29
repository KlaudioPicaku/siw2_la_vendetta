package com.siw.uniroma3.it.siw_lavendetta.services;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.User;

import java.util.List;

public interface ReviewService {

    List<Review> findAllByRatingForFilm(Film film, Integer Rating);

    List<Review> findAllByUser(User user);

    List<Review> findAllByFilm(Film film);

    void saveOrUpdate(Review review);

    void delete(Long id);

}
