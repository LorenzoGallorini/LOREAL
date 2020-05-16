package com.example.cinemhub.models;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Movie {



    private int id;
    private String title;
    private boolean adult;
    private int runtime;
    private List<String> genres;
    private String release_date;
    private double vote_average;
    private String poster_path;
    private CrewApiTmdbResponse[] directors;//da valutare il tipo
    private CastApiTmdbResponse[] actors;//da valutare il tipo
    private String description;
    private int budget;
    private String original_language;
    private String original_title;
    private double popularity;
    private String status;
    private int vote_count;

    private String home_page;
    private int revenue;

    public Movie(MovieApiTmdbResponse movieApiTmdbResponse){
        this.id=movieApiTmdbResponse.getId();
        this.poster_path = movieApiTmdbResponse.getPoster_path();
        this.adult = movieApiTmdbResponse.isAdult();
        this.release_date = movieApiTmdbResponse.getRelease_date();

        this.original_title = movieApiTmdbResponse.getOriginal_title();
        this.original_language = movieApiTmdbResponse.getOriginal_language();
        this.title = movieApiTmdbResponse.getTitle();
        this.popularity = movieApiTmdbResponse.getPopularity();
        this.vote_count = movieApiTmdbResponse.getVote_count();
        this.vote_average = movieApiTmdbResponse.getVote_average();
        this.runtime = movieApiTmdbResponse.getRuntime();
        this.directors = movieApiTmdbResponse.getDirectors();
        this.actors = movieApiTmdbResponse.getActors();
        this.description = movieApiTmdbResponse.getOverview();
        this.budget = movieApiTmdbResponse.getBudget();
        this.status = movieApiTmdbResponse.getStatus();
        this.home_page = movieApiTmdbResponse.getHome_page();
        this.revenue = movieApiTmdbResponse.getRevenue();

        Genre[] gen=movieApiTmdbResponse.getGenres();
        genres=new ArrayList<String>();
        if(gen!=null) {
            for (int i = 0; i < gen.length; i++){
                this.genres.add(gen[i].getName());
            }
        }
    }

    public Movie(int id, String title, boolean adult, int runtime, List<String> genres, String release_date, double vote_average, String poster_path, CrewApiTmdbResponse[] directors, CastApiTmdbResponse[] actors, String description, int budget, String original_language, String original_title, double popularity, String status, int vote_count, String home_page, int revenue) {
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
        this.home_page = home_page;
        this.revenue = revenue;
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
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

    public String getGenresTostring()
    {
        String ret="";
        for (int i = 0;i< this.getGenres().size();i++)
            ret+=this.getGenres().get(i)+" | ";
        return ret.substring(0,ret.length()-3);
    }
    public String getRevenueFORMATTED()
    {
        return NumberFormat.getInstance().format(this.revenue);

    }
    public String getReleaseDateFORMATTED()
    {
        String date=getRelease_date();
        SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date newDate=spf.parse(date);
            spf= new SimpleDateFormat("dd/MM/yyyy");
            date = spf.format(newDate);
            return date;
        }
        catch (Exception e)
        {
            return getRelease_date();
        }



    }
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", adult=" + adult +
                ", runtime=" + runtime +
                ", genres=" + genres +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", poster_path='" + poster_path + '\'' +
                ", directors=" + Arrays.toString(directors) +
                ", actors=" + Arrays.toString(actors) +
                ", description='" + description + '\'' +
                ", budget=" + budget +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", popularity=" + popularity +
                ", status='" + status + '\'' +
                ", vote_count=" + vote_count +
                ", home_page='" + home_page + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}
