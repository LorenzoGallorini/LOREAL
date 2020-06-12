package com.example.cinemhub.models;
/**
 * Classe MovieCreditsApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class MovieCreditsApiTmdbResponse {
    private int movie_id;
    private CastApiTmdbResponse[] cast;
    private CrewApiTmdbResponse[] crew;

    /**
     * costruttore della classe MovieCreditsApiTmdbResponse
     * @param movie_id intero che indica in maniera univoca l'ID del film
     * @param cast Array di CastApiTmdbResponse che contiene i membri del cast
     * @param crew Array di CrewApiTmdbResponse che contiene i membri della crew
     */
    public MovieCreditsApiTmdbResponse(int movie_id, CastApiTmdbResponse[] cast, CrewApiTmdbResponse[] crew) {
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

    public CastApiTmdbResponse[] getCast() {
        return cast;
    }

    public void setCast(CastApiTmdbResponse[] cast) {
        this.cast = cast;
    }

    public CrewApiTmdbResponse[] getCrew() {
        return crew;
    }

    public void setCrew(CrewApiTmdbResponse[] crew) {
        this.crew = crew;
    }
}
