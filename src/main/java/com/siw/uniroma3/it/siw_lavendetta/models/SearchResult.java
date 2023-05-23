package com.siw.uniroma3.it.siw_lavendetta.models;

public class SearchResult {
    private String name;

    private String imagePath;


    private String absoluteUrl;

    public SearchResult(){}

    public SearchResult(Film film){
        this.name=film.toString();
        this.imagePath= film.getCoverPath();
        this.absoluteUrl=film.getAbsoluteUrl();
    }

    public SearchResult(Director director){
        this.name=director.getFullName();
        this.imagePath=director.getImagePath();
        this.absoluteUrl=director.getAbsoluteUrl();
    }
    public  SearchResult(Actor actor){
        this.name=actor.getFullName();
        this.imagePath= actor.getImagePath();
        this.absoluteUrl=actor.getAbsoluteUrl();
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }
}
