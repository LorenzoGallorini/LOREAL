package com.example.cinemhub.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * classe che rappresenta un film scaricato da https://developers.themoviedb.org/3/
 */
public class Movie<integer> implements Parcelable {
    private int id;
    private String title;
    private boolean adult;
    private int runtime;
    private List<String> genres;
    private int[] genre_ids;
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

    /**
     * costruttore che istanzia solo gli attributi utili per la visualizzazione del film nel FavoriteFragmnet
     * @param id stringa che indica in maniera univoca l'ID del film da visualizzare
     * @param title stringa che contiene il titolo del film da visualizzare
     * @param poster_path stringa che contiene parte dell'URL per la visualizzazione del poster del film
     */
    public Movie(String id, String title, String poster_path){
        this.id=Integer.parseInt(id);
        this.title=title;
        this.poster_path=poster_path;
    }

    /**
     * Costruttore della classe Movie che prende in input un oggetto di tipo MovieApiTmdbResponse
     * @param movieApiTmdbResponse oggetto di tipo MovieApiTmdbResponse
     */
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
            for (Genre genre : gen) {
                this.genres.add(genre.getName());
            }
        }
    }
    /**
     * costruttore della classe Movie
     * @param id intero che indica in maniera univoca l'ID del film
     * @param title stringa che contiene il titolo del film
     * @param adult booleano per il Parental Control
     * @param runtime intero che indica la durata del film
     * @param genres lista di string che contiene i generi
     * @param release_date stringa che contiene la data di rilascio del film
     * @param vote_average di tipo double utilizzato per la valutazione del film
     * @param poster_path stringa che contiene parte dell'URL dove trovare la locandina del film
     * @param directors Array di CrewApiTmdbResponse che contiene i registi di un film
     * @param actors Array di CastApiTmdbResponse che contiene gli attori di un film
     * @param description stringa che contiene la descrizione del film
     * @param budget intero che indica il budget del film
     * @param original_language stringa che contiene la lingua originale del film
     * @param original_title stringa che contiene il titolo originale del film
     * @param popularity di tipo double che indica la popolarità del film
     * @param status stringa che indica in che stato della produziona si trova il film
     * @param vote_count intero che indica il numero di voti che ha ricevuto il film
     * @param home_page stringa che contiene l'homepage del film
     * @param revenue intero che indica l'incasso del film
     * @param genres_ids array che contiente gli ID che indicano i generi del film
     */
    public Movie(int id, String title, boolean adult, int runtime, List<String> genres,
                 String release_date, double vote_average, String poster_path, CrewApiTmdbResponse[] directors,
                 CastApiTmdbResponse[] actors, String description, int budget, String original_language,
                 String original_title, double popularity, String status, int vote_count, String home_page, int revenue, int[] genres_ids) {
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
        this.genre_ids = genres_ids;
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


    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
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

    /**
     * metodo che trasforma i generi in un'unica stringa che andrà settata nelle informazioni del film
     * @return String contenente tutti i generi
     */
    public String getGenresTostring()
    {

        String ret="";
        if(this.getGenres()==null){
            return ret;
        }
        for (int i = 0;i< this.getGenres().size();i++){
            ret+=this.getGenres().get(i)+" | ";
        }
        if(ret.length()>4){
            return ret.substring(0,ret.length()-3);
        }else{
            return "";
        }
    }

    /**
     * formatta l'attributo revenue nel Movie Card
     */
    public String getRevenueFORMATTED()
    {
        return NumberFormat.getInstance().format(this.revenue);
    }

    /**
     * formatta la data nella MovieCard
     */
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

    /**
     * trasforma una lista di movie in un array di movie
     * @param movies List di Movie
     * @return Array di Movie
     */
    public static Movie[] fromListToArray(List<Movie> movies){
        Movie[] ris=new Movie[movies.size()];
        for (int i=0;i<movies.size();i++){
            ris[i]=movies.get(i);
        }
        return ris;
    }

    /**
     * trasforma un Array di MovieApiTmdbResponse in una List di Movie filtrando in base al Parental Control
     * @param movies Array di MovieApiTmdbResponse
     * @param checkAdult valore per controllare se il Parental Control è attivo
     * @return List di Movie
     */
    public static List<Movie> toList(MovieApiTmdbResponse[] movies, boolean checkAdult){
        List<Movie> ris=new ArrayList<Movie>();
        for (MovieApiTmdbResponse movie : movies) {
            Movie new_movie=new Movie(movie);
            boolean found=false;
            int i=0;
            while (i<ris.size() && !found){
                if(ris.get(i).getId()==new_movie.getId()){
                    found=true;
                }
                i++;
            }
            if((!checkAdult || !movie.isAdult() )&& !found){
                ris.add(new_movie);
            }
        }
        return ris;
    }

    /**
     * trasforma un Array di Movie in una List di Movie filtrando in base al Parental Control
     * @param movies Array di Movie
     * @param checkAdult valore per controllare se il Parental Control è attivo
     * @return List di Movie
     */
    public static List<Movie> toList(Movie[] movies, boolean checkAdult){
        List<Movie> ris= new ArrayList<>();
        for (Movie movie : movies) {

            if(!checkAdult || !movie.isAdult()){
                ris.add(movie);
            }
        }
        return ris;
    }

    /**
     * Metodo che ordina i film da visualizzare nella filmografia in base alla popolarità
     */
    public static class MoviePopularityComparator implements Comparator<Movie>{
        public int compare(Movie p1, Movie p2) {
            return Double.compare(p2.getPopularity(), p1.getPopularity());
        }
    }

    /**
     * I seguenti metodi sono richiesti per l'interfaccia Parcelable
     */
    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        adult = in.readByte() != 0x00;
        runtime = in.readInt();
        if (in.readByte() == 0x01) {
            genres = new ArrayList<String>();
            in.readList(genres, String.class.getClassLoader());
        } else {
            genres = null;
        }
        release_date = in.readString();
        vote_average = in.readDouble();
        poster_path = in.readString();
        description = in.readString();
        budget = in.readInt();
        original_language = in.readString();
        original_title = in.readString();
        popularity = in.readDouble();
        status = in.readString();
        vote_count = in.readInt();
        home_page = in.readString();
        revenue = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeByte((byte) (adult ? 0x01 : 0x00));
        dest.writeInt(runtime);
        if (genres == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genres);
        }
        dest.writeString(release_date);
        dest.writeDouble(vote_average);
        dest.writeString(poster_path);
        dest.writeString(description);
        dest.writeInt(budget);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeDouble(popularity);
        dest.writeString(status);
        dest.writeInt(vote_count);
        dest.writeString(home_page);
        dest.writeInt(revenue);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
