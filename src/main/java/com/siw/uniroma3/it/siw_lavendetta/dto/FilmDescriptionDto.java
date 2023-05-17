package com.siw.uniroma3.it.siw_lavendetta.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FilmDescriptionDto {


    @NotEmpty
    private String body;


    private int filmId;

    public FilmDescriptionDto() {

    }
    public FilmDescriptionDto(String body, int filmId) {
        super();
        this.body = body;
        this.filmId = filmId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
}
