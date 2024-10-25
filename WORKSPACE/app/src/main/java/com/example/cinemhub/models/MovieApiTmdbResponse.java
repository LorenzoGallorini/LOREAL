package com.example.cinemhub.models;




/**
 * Classe MovieApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class MovieApiTmdbResponse {



    private String poster_path;//
    private boolean adult;//
    private String overview;//
    private String release_date;//
    private int[] genre_id;
    private Genre[] genres;
    private int id;
    private String original_title;//
    private String original_language;//
    private String title;//
    private String backdrop_path;//
    private double popularity;//
    private int vote_count;//
    private boolean video;//
    private double vote_average;//
    private int runtime;//
    private CrewApiTmdbResponse[] directors;//da valutare il tipo
    private CastApiTmdbResponse[] actors;//da valutare il tipo
    private String description;
    private int budget;//
    private String status;//

    private String home_page;
    private int revenue;

    /**
     * costruttore della classe MovieApiTmdbResponse
     * @param poster_path stringa che contiene parte dell'URL dove trovare la locandina del film
     * @param adult booleano per il Parental Control
     * @param overview stringa che contiene la descrizione del film
     * @param release_date stringa che contiene la data di rilascio del film
     * @param genre_id array che contiente gli ID che indicano i generi del film
     * @param genres lista di string che contiene i generi
     * @param id intero che indica in maniera univoca l'ID del film
     * @param original_title stringa che contiene il titolo originale del film
     * @param original_language stringa che contiene la lingua originale del film
     * @param title stringa che contiene il titolo del film
     * @param backdrop_path stringa che contiene parte dell'URL dove trovare un'immagine del film
     * @param popularity di tipo double che indica la popolarità del film
     * @param vote_count intero che indica il numero di voti che ha ricevuto il film
     * @param video booleano che indica se il video è presente
     * @param vote_average di tipo double utilizzato per la valutazione del film
     * @param runtime runtime intero che indica la durata del film
     * @param directors Array di CrewApiTmdbResponse che contiene i registi di un film
     * @param actors Array di CastApiTmdbResponse che contiene gli attori di un film
     * @param description stringa che contiene la descrizione del film
     * @param budget intero che indica il budget del film
     * @param status stringa che indica in che stato della produziona si trova il film
     * @param home_page stringa che contiene l'homepage del film
     * @param revenue intero che indica l'incasso del film
     */
    public MovieApiTmdbResponse(String poster_path, boolean adult, String overview, String release_date,
                                int[] genre_id, Genre[] genres, int id, String original_title, String original_language,
                                String title, String backdrop_path, double popularity, int vote_count,
                                boolean video, double vote_average, int runtime, CrewApiTmdbResponse[] directors,
                                CastApiTmdbResponse[] actors, String description, int budget, String status, String home_page, int revenue) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_id = genre_id;
        this.genres = genres;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
        this.runtime = runtime;
        this.directors = directors;
        this.actors = actors;
        this.description = description;
        this.budget = budget;
        this.status = status;
        this.home_page = home_page;
        this.revenue = revenue;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int[] getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int[] genre_id) {
        this.genre_id = genre_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public CrewApiTmdbResponse[] getDirectors() {
        return directors;
    }

    public void setDirectors(CrewApiTmdbResponse[] directors) {
        this.directors = directors;
    }

    public CastApiTmdbResponse[] getActors() {
        return actors;
    }

    public void setActors(CastApiTmdbResponse[] actors) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public void setGenres(Genre[] genres) {
        this.genres = genres;
    }

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
