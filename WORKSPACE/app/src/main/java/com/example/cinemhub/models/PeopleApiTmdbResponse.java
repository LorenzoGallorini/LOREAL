package com.example.cinemhub.models;
/**
 * Classe PeopleApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class PeopleApiTmdbResponse {
    private String birthday;
    private String known_for_department;
    private String deathday;
    private int id;
    private String name;
    private String[] also_known_as;
    private int gender;
    private String biography;
    private double popularity;
    private String place_of_birth;
    private String profile_path;
    private boolean adult;
    private String imdb_id;
    private String homepage;

    /**
     * costruttore della classe PeopleApiTmdbResponse
     * @param birthday stringa che contiene la data di nascita
     * @param known_for_department stringa che contiene il dipartimento per il quale la persona è conosciuta
     * @param deathday stringa che contiene la data di morte della persona
     * @param id intero che indica in maniera univoca l'ID della persona
     * @param name stringa che contiene il nome della persona
     * @param also_known_as array di stringhe che contiene gli altri nomi con i quali è conosciuta la persona nel mondo
     * @param gender intero che indica il sesso della persona
     * @param biography stringa che contiene la biografia della persona
     * @param popularity di tipo double che indica la popolarità della persona
     * @param place_of_birth stringa che contiene il luogo di nascita
     * @param profile_path stringa che contiene parte dell'URL per la foto profilo
     * @param adult booleano per il Parental Control
     * @param imdb_id string che contiene l'ID del film per il DB IMDB
     * @param homepage stringa che contiene l'homepage della persona
     */
    public PeopleApiTmdbResponse(String birthday, String known_for_department, String deathday, int id,
                                 String name, String[] also_known_as, int gender, String biography,
                                 double popularity, String place_of_birth, String profile_path, boolean adult,
                                 String imdb_id, String homepage) {
        this.birthday = birthday;
        this.known_for_department = known_for_department;
        this.deathday = deathday;
        this.id = id;
        this.name = name;
        this.also_known_as = also_known_as;
        this.gender = gender;
        this.biography = biography;
        this.popularity = popularity;
        this.place_of_birth = place_of_birth;
        this.profile_path = profile_path;
        this.adult = adult;
        this.imdb_id = imdb_id;
        this.homepage = homepage;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
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

    public String[] getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(String[] also_known_as) {
        this.also_known_as = also_known_as;
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

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
