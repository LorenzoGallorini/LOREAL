package com.example.cinemhub.models;

import java.util.Date;

public class People {
    private int id;
    private String name;
    private String birth_date;

    //private String[] role; l'array di stringhe si riferisce a come è conosciuto nel mondo(AKA)
    private String known_for_department;

    private String death_date;
    private int gender;
    private String biography;
    private double popularity;
    private String place_of_birth;
    private String profile_path;
    private boolean adult;
    private int[] filmography;

    public People(PeopleApiTmdbResponse peopleApiTmdbResponse){
        this.birth_date = peopleApiTmdbResponse.getBirthday();
        this.death_date = peopleApiTmdbResponse.getDeathday();
        this.id = peopleApiTmdbResponse.getId();
        this.name = peopleApiTmdbResponse.getName();
        this.gender = peopleApiTmdbResponse.getGender();
        this.biography = peopleApiTmdbResponse.getBiography();
        this.popularity = peopleApiTmdbResponse.getPopularity();
        this.profile_path = peopleApiTmdbResponse.getProfile_path();
        this.adult = peopleApiTmdbResponse.isAdult();
        this.place_of_birth = peopleApiTmdbResponse.getPlace_of_birth();
        this.known_for_department = peopleApiTmdbResponse.getKnown_for_department();

        //this.imdb_id = peopleApiTmdbResponse.getImdb_id();
        //this.homepage = peopleApiTmdbResponse.getHomepage();
        //this.also_known_as = peopleApiTmdbResponse.getAlso_known_as();
    }

    public People(int id, String name, String birth_date, String known_for_department, String death_date, int gender, String biography, double popularity, String place_of_birth, String profile_path, boolean adult, int[] filmography) {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
        this.known_for_department = known_for_department;
        this.death_date = death_date;
        this.gender = gender;
        this.biography = biography;
        this.popularity = popularity;
        this.place_of_birth = place_of_birth;
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


    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getDeath_date() {
        return death_date;
    }

    public void setDeath_date(String death_date) {
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

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
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
