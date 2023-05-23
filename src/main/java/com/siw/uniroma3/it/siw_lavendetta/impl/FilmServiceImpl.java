package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.*;
import com.siw.uniroma3.it.siw_lavendetta.repositories.*;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmDescriptionService;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@Qualifier("filmServiceImpl")
public class FilmServiceImpl implements FilmService {


    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FilmDescriptionRepository filmDescriptionRepository;
    @Autowired
    private FilmDescriptionService filmDescriptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ReviewRepository reviewRepository;



    @Override
    public List<Film> findAll() {return filmRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));}

    @Override
    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    @Override
    public List<Film> findByTitle(String title) {
        return filmRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Film> findByReleaseYear(Integer year) {
        return filmRepository.findByReleaseYear(year);
    }

    @Override
    public void saveOrUpdate(Film film) {
        filmRepository.save(film);
    }

    @Override
    public void delete(Long id) {

//        List<Review> reviews= reviewRepository.findAllByFilm(filmRepository.findById(id).get());
//        for(Review r : reviews){
//            reviewRepository.delete(r);
//        }
        Film film = filmRepository.findById(id).get();
        film.clearActors();
        filmRepository.delete(film);
    }

    @Override
    public List<Film> findByDirector(Director director){
        return filmRepository.findByDirector(director);
    }

    @Override
    public String getAverageRating(Film film) {
        List<Review> reviews = reviewService.findAllByFilm(film);
        OptionalDouble averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average();

        if (averageRating.isPresent()) {
            double average = averageRating.getAsDouble();

            return String.valueOf( Math.round(average*100.0)/100.0);
        }

        return "No Ratings";
    }

    @Override
    public Double getAveragDoubleRating(Film film) {
        List<Review> reviews = reviewService.findAllByFilm(film);
        OptionalDouble averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average();
        if(averageRating.isPresent()){
            return (Math.round(averageRating.getAsDouble()*100.0)/100.0);

        }
        return null;
    }


    @Override
    public List<Film> findByActor(Actor actor) {
        return filmRepository.findByActorsContaining(actor);
    }

    @Override
    public List<Film> findTopThree() {
        List<Film> films = this.findAll();
        //tenendo conto che getAverageDouble può ritornare null, in quel caso la media delle review sarà 0
        films.sort(Comparator.comparingDouble(film -> {
            Double averageRating = getAveragDoubleRating((Film) film);
            return (averageRating != null) ? averageRating : 0.0;
        }).reversed());

        return films.subList(0, Math.min(films.size(), 3));
    }

    @Override
    public List<Film> searchByTerm(String term) {
        return filmRepository.findByTitleContainingIgnoreCase(term);
    }

    public String filmDescriptionByFilm(Film film){
        Optional<FilmDescription> filmDescription=filmDescriptionService.findByFilmId(film.getId());
        if (filmDescription.isPresent()){
            return  filmDescription.get().getBody();
        }
        return "";
    }

}