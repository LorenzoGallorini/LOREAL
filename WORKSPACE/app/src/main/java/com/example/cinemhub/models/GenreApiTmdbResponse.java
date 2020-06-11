package com.example.cinemhub.models;

import java.util.List;
/**
 * Classe CastApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class GenreApiTmdbResponse {
    private List<Genre> genres;

    public GenreApiTmdbResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
