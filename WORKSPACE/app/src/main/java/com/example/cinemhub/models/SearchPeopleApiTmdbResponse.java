package com.example.cinemhub.models;

public class SearchPeopleApiTmdbResponse {
    private int page;
    private People[] results;
    private int total_results;
    private int total_pages;

    public SearchPeopleApiTmdbResponse(int page, People[] results, int total_results, int total_pages) {
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

    public People[] getResults() {
        return results;
    }

    public void setResults(People[] results) {
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
