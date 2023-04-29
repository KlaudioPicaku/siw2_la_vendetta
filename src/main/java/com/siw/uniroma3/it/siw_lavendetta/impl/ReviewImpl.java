package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ReviewRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Override
    public List<Review> findAllByRatingForFilm(Film film, Integer rating) {
        return reviewRepository.findByRatingAndFilm(rating, film);
    }

    @Override
    public List<Review> findAllByUser(User user) {
        return reviewRepository.findAllByAuthor(user);
    }

    @Override
    public List<Review> findAllByFilm(Film film){
        return reviewRepository.findAllByFilm(film);
    }

    @Override
    public void saveOrUpdate(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);

    }
}
