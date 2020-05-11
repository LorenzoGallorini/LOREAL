package com.example.cinemhub.models;

import java.util.Date;

public class People {
    private int id;
    private String name;
    private Date birth_date;
    private String[] role;
    private Date death_date;
    private int gender;
    private String biography;
    private double popularity;
    private String birth_place;
    private String profile_path;
    private boolean adult;
    private int[] filmography;

    public People(int id, String name, Date birth_date, String[] role, Date death_date, int gender,
                  String biography, double popularity, String birth_place, String profile_path,
                  boolean adult, int[] filmography) {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
        this.role = role;
        this.death_date = death_date;
        this.gender = gender;
        this.biography = biography;
        this.popularity = popularity;
        this.birth_place = birth_place;
        this.profile_path = profile_path;
        this.adult = adult;
        this.filmography = filmography;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public Date getDeath_date() {
        return death_date;
    }

    public void setDeath_date(Date death_date) {
        this.death_date = death_date;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int[] getFilmography() {
        return filmography;
    }

    public void setFilmography(int[] filmography) {
        this.filmography = filmography;
    }
}
