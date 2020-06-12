package com.example.cinemhub.models;
/**
 * Classe GetVideosApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class GetVideosApiTmdbResponse {
    private int id;
    private VideoApiTmdbResponse[] results;

    /**
     * costruttore della classe GetVideosApiTmdbResponse
     * @param id intero che indica in maniera univoca l'ID del film del quale si vuole avere il video
     * @param results Array di oggetti VideoApiTmdbResponse
     */
    public GetVideosApiTmdbResponse(int id, VideoApiTmdbResponse[] results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VideoApiTmdbResponse[] getResults() {
        return results;
    }

    public void setResults(VideoApiTmdbResponse[] results) {
        this.results = results;
    }
}
