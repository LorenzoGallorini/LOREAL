package com.example.cinemhub.models;

public class GetVideosApiTmdbResponse {
    private int id;
    private VideoApiTmdbResponse[] results;

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
