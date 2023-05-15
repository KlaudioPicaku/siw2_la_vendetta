package com.siw.uniroma3.it.siw_lavendetta.dto;

public class FilmRating{
    private String average;
    private int ratings;

    public FilmRating(String average, int ratings) {
        this.average = average;
        this.ratings = ratings;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }
}
