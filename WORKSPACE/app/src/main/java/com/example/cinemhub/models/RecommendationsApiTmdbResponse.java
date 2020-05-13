package com.example.cinemhub.models;

import java.util.List;

public class RecommendationsApiTmdbResponse {
    private int page;
    private List<MovieApiTmdbResponse> results;
    private int total_results;
    private int total_pages;

    public RecommendationsApiTmdbResponse(int page, List<MovieApiTmdbResponse> results, int total_results, int total_pages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieApiTmdbResponse> getResults() {
        return results;
    }

    public void setResults(List<MovieApiTmdbResponse> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
