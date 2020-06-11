package com.example.cinemhub.models;
/**
 * Classe PeopleCreditsApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class PeopleCreditsApiTmdbResponse {
    private int movie_id;
    private MovieApiTmdbResponse[] cast;
    private MovieApiTmdbResponse[] crew;

    /**
     * costruttore della classe PeopleCreditsApiTmdbResponse
     */
    public PeopleCreditsApiTmdbResponse(int movie_id, MovieApiTmdbResponse[] cast, MovieApiTmdbResponse[] crew) {
        this.movie_id = movie_id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public MovieApiTmdbResponse[] getCast() {
        return cast;
    }

    public void setCast(MovieApiTmdbResponse[] cast) {
        this.cast = cast;
    }

    public MovieApiTmdbResponse[] getCrew() {
        return crew;
    }

    public void setCrew(MovieApiTmdbResponse[] crew) {
        this.crew = crew;
    }
}
