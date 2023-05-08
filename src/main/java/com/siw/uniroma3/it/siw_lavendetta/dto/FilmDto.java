package com.siw.uniroma3.it.siw_lavendetta.dto;

import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class FilmDto {

    @NotEmpty
    private String title;

    @NotNull
    private int releaseYear;

    @NotNull
    private Long director;

    @NotNull
    private List<Long> actors;

    @NotEmpty
    private List<MultipartFile> images;

    public FilmDto(){}

    public FilmDto(String title, int releaseYear, Long director, List<Long> actors, List<MultipartFile> images) {
        super();
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.actors = actors;
        this.images = images;
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

    public List<Long> getActors() {
        return actors;
    }

    public void setActors(List<Long> actors) {
        this.actors = actors;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
