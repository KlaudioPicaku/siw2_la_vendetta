package com.siw.uniroma3.it.siw_lavendetta.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReviewDto {
    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private Integer rating;

    @NotNull
    @NotEmpty
    private String body;

    private Long authorId;
    private Long filmId;



    // Constructors, getters, and setters
    public ReviewDto(){
    }

    public ReviewDto( String title, Integer rating, String body, Long authorId, Long filmId) {

        this.title = title;
        this.rating = rating;
        this.body = body;
        this.authorId = authorId;
        this.filmId = filmId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getFilmId() {
        return filmId;
    }


    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", body='" + body + '\'' +
                ", authorId=" + authorId +
                ", filmId=" + filmId +
                '}';
    }
}
