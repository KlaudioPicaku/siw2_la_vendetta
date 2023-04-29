package com.siw.uniroma3.it.siw_lavendetta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
public class Review {
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Id
    private Long id;
    @Column(name = "title", nullable = false, length = 20)
    private String title;
    @Column(name = "rating", nullable = false, length = 5)
    private Integer rating;
    @Column(name = "body", nullable = false, length = 500)
    private String body;
    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;
    @Column(name = "author", nullable = false)
    private User author;
    @ManyToOne
    private Film film;

    // costruttori, getter, setter, equals, hashCode, toString
    public Review(){

    }
    public Review(Long id, String title, Integer rating, String body, User author, Film film) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.body = body;
        this.createdOn =LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
        this.author = author;
        this.film = film;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
