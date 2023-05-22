package com.siw.uniroma3.it.siw_lavendetta.models;

import javax.persistence.*;

@Entity
@Table(name = "film_description")
public class FilmDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Column(name = "body", nullable = false, length = 4000)
    private String body;
    public FilmDescription(){

    }

    public FilmDescription(Film film, String body) {
        super();
        this.film = film;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(Long id) {
        this.id=id;
    }
}
