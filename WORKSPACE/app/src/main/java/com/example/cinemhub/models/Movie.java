package com.example.cinemhub.models;

import java.util.Date;

public class Movie {
    private int id;
    private String title;
    private boolean adult;
    private int runtime;
    private String[] genres;
    private Date release_date;
    private double vote_average;
    private String poster_path;
    private int[] directors;//da valutare il tipo
    private int[] actors;//da valutare il tipo
    private String description;
    private int budget;
    private String original_language;
    private String original_title;
    private double popularity;
    private String status;
    private int vote_count;

    public Movie(int id, String title, boolean adult, int runtime, String[] genres,
                 Date release_date, double vote_average, String poster_path, int[] directors,
                 int[] actors, String description, int budget, String original_language,
                 String original_title, double popularity, String status, int vote_count) {
        this.id = id;
        this.title = title;
        this.adult = adult;
        this.runtime = runtime;
        this.genres = genres;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.directors = directors;
        this.actors = actors;
        this.description = description;
        this.budget = budget;
        this.original_language = original_language;
        this.original_title = original_title;
        this.popularity = popularity;
        this.status = status;
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int[] getDirectors() {
        return directors;
    }

    public void setDirectors(int[] directors) {
        this.directors = directors;
    }

    public int[] getActors() {
        return actors;
    }

    public void setActors(int[] actors) {
        this.actors = actors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
