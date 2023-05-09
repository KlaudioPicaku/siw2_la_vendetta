package com.siw.uniroma3.it.siw_lavendetta.dto;

import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FilmDto {

    @NotEmpty
    private String title;

    @NotNull
    private int releaseYear;

    @NotNull
    private Long director;

    @NotNull
    private Set<Actor> actors;

    @NotEmpty
    private List<MultipartFile> images;

    public FilmDto(){}

    public FilmDto(String title, int releaseYear, Long director) {
        super();
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.actors = new HashSet<>();;
        this.images =new LinkedList<>();
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

    public Long getDirector() {
        return director;
    }

    public void setDirector(Long director) {
        this.director = director;
    }

    public Set<Actor> getActors() {
        return this.actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
