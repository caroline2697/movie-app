package net.caroline.movieapp;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title, poster, overview, backdrop, release;
    private Double rating;

    public Movie(int id, String title, String poster, String overview, String backdrop, String release, Double rating) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.backdrop = backdrop;
        this.release = release;
        this.rating = rating;
    }

    public  int getId(){return id;}
    public  void  setId(int id){this.id=id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

