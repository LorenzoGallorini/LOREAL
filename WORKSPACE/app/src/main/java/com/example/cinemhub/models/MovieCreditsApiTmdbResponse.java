package com.example.cinemhub.models;

public class MovieCreditsApiTmdbResponse {
    private int movie_id;
    private CastApiTmdbResponse[] cast;
    private CrewApiTmdbResponse[] crew;

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
