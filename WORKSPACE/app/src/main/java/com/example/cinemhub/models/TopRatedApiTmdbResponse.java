package com.example.cinemhub.models;

import java.util.List;
/**
 * Classe TopRatedApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class TopRatedApiTmdbResponse {
    private int page;
    private List<MovieApiTmdbResponse> results;
    private int total_results;
    private int total_pages;

    /**
     * costruttore della classe TopRatedApiTmdbResponse
     * @param page intero che indica il numero della pagina della quale si vogliono avere i risultati
     * @param results List di MovieApiTmdbResponse che contiene i risultati della chiamata
     * @param total_pages intero per il numero totale delle pagine che contengono risultati
     * @param total_results intero che indica il numero totale dei risultati
     */
    public TopRatedApiTmdbResponse(int page, List<MovieApiTmdbResponse> results, int total_results,
                                   int total_pages) {
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
