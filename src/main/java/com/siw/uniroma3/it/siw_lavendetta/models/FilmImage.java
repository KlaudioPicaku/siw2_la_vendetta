package com.siw.uniroma3.it.siw_lavendetta.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "film_image")
public class FilmImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    public FilmImage(){}

    public FilmImage( String filePath, Film film) {
        super();
        this.filePath = filePath;
        this.film = film;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilename(String filename) {
        this.filePath = filename;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmImage)) return false;
        FilmImage filmImage = (FilmImage) o;
        return Objects.equals(filePath, filmImage.filePath) && Objects.equals(film, filmImage.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, film);
    }
}
