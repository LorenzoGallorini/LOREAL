package com.example.cinemhub.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class People {
    private int id;
    private String name;
    private String birth_date;
    private String known_for_department;
    private String death_date;
    private int gender;
    private String biography;
    private double popularity;
    private String place_of_birth;
    private String profile_path;
    private boolean adult;
    private int[] filmography;
    private String role;

    public People(CastApiTmdbResponse castApiTmdbResponse){
        //this.cast_id = castApiTmdbResponse.getCast_id();
        this.role = castApiTmdbResponse.getCharacter();
        //this.credit_id = castApiTmdbResponse.getCredit_id();
        this.gender = castApiTmdbResponse.getGender();
        this.id = castApiTmdbResponse.getId();
        this.name = castApiTmdbResponse.getName();
        //this.order = castApiTmdbResponse.getOrder();
        this.profile_path = castApiTmdbResponse.getProfile_path();
    }

    public People(CrewApiTmdbResponse crewApiTmdbResponse){
        //this.credit_id = crewApiTmdbResponse.getCredit_id();
        this.known_for_department = crewApiTmdbResponse.getDepartment();
        this.gender = crewApiTmdbResponse.getGender();
        this.id = crewApiTmdbResponse.getId();
        this.role = crewApiTmdbResponse.getJob();
        this.name = crewApiTmdbResponse.getName();
        this.profile_path = crewApiTmdbResponse.getProfile_path();
    }

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

    public People(int id, String name, String birth_date, String known_for_department, String death_date, int gender, String biography, double popularity, String place_of_birth, String profile_path, boolean adult, int[] filmography, String role) {
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
        this.role=role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static List<People> toList(CastApiTmdbResponse[] castApiTmdbResponse){
        List<People> ris= new ArrayList<People>();
        for(int i=0; i<castApiTmdbResponse.length;i++){
            ris.add(new People(castApiTmdbResponse[i]));
        }
        return ris;
    }

    public static int isInList(List<People> peoples, int id){
        boolean found=false;
        int i=0;
        while (i<peoples.size() && !found){
            if(peoples.get(i).getId()==id){
                found=true;
            }
            i++;
        }
        if(found){
            return i-1;
        }
        return -1;
    }

    public static List<People> toList(CrewApiTmdbResponse[] crewApiTmdbResponses){
        List<People> ris= new ArrayList<People>();
        for(int i=0; i<crewApiTmdbResponses.length;i++){
            if(crewApiTmdbResponses[i].getDepartment().equals("Directing")|crewApiTmdbResponses[i].getDepartment().equals("Writing")){
                int index=isInList(ris, crewApiTmdbResponses[i].getId());
                if(index>=0){
                    ris.get(index).setRole(ris.get(index).getRole()+" | "+crewApiTmdbResponses[i].getJob());
                }else{
                    ris.add(new People(crewApiTmdbResponses[i]));
                }
            }
        }
        return ris;
    }
}