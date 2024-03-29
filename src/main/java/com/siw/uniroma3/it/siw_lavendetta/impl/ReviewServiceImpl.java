package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ReviewRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.UserRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("reviewServiceImpl")
public class ReviewServiceImpl implements ReviewService {


    ReviewRepository reviewRepository;


    FilmRepository filmRepository;

    UserRepository userRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository, FilmRepository filmRepository, UserRepository userRepository) {
        super();
        this.reviewRepository = reviewRepository;
        this.filmRepository = filmRepository;
        this.userRepository= userRepository;
    }

    @Override
    public List<Review> findAllByRatingForFilm(Film film, Integer rating) {
        return reviewRepository.findByRatingAndFilm(rating, film);
    }

    @Override
    public List<Review> findAllByUser(User user) {
        return reviewRepository.findAllByUser(user);
    }

    @Override
    public List<Review> findAllByFilm(Film film){
        return reviewRepository.findAllByFilm(film);
    }

    @Override
    public Review save(Review review) {
       return this.reviewRepository.save(review);
    }

    @Override
    public void saveOrUpdate(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);

    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findAllByRating(Integer rating) {
        return reviewRepository.findAllByRating(rating);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAllByFilmAndUser(Film film, Optional<User> user) {
        return reviewRepository.findAllByUserAndFilm(user.get(), Optional.ofNullable(film));
    }

    @Override
    public List<Review> findAllByRatingAndUser(Integer rating, Optional<User> user) {
       return reviewRepository.findAllByUserAndRating(user.get(),rating);
    }

    @Override
    public List<Review> findAllByUserAndRatingForFilm(Film film, Integer rating, Optional<User> user) {
        return reviewRepository.findAllByUserAndRatingAndFilm(user.get(),rating,film);
    }
}
