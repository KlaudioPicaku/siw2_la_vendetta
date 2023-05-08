package com.siw.uniroma3.it.siw_lavendetta.models;

import javax.persistence.*;

import com.siw.uniroma3.it.siw_lavendetta.constants.StaticURLs;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "film")
public class Film {
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Id
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(name = "release_year", nullable = false, columnDefinition = "INT(4)")
    private int releaseYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilmImage> images = new HashSet<>();

    @OneToMany(mappedBy = "film")
    private Set<Review> reviews = new HashSet<>();

    public Film(){}

    public Film(String title, int releaseYear, Director director) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.actors=new HashSet<>();
        this.images= new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return releaseYear == film.releaseYear && Objects.equals(id, film.id) && Objects.equals(title, film.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseYear);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<FilmImage> getImages() {
        return images;
    }

    public void setImages(Set<FilmImage> images) {
        this.images = images;
    }

    public void addImage(FilmImage image) {
        images.add(image);
        image.setFilm(this);
    }

    public void removeImage(FilmImage image) {
        images.remove(image);
        image.setFilm(null);
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString(){
        return  this.getTitle()+"("+this.getReleaseYear()+")";
    }

    public String getAbsoluteUrl(){ return StaticURLs.FILM_DETAIL_URL+this.getId(); }
}
