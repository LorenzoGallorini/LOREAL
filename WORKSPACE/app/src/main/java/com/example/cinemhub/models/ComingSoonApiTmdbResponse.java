package com.example.cinemhub.models;

import java.util.List;
/**
 * Classe CastApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class ComingSoonApiTmdbResponse {
    private int page;
    private List<MovieApiTmdbResponse> results;
    private int total_pages;
    private int total_results;

    /**
     * costruttore della classe CastApiTmdbResponse
     * @param page intero che indica il numero della pagina dei risultati della quale si vogliono i dati
     * @param results List di MovieApiTmdbResponse che contiene i risultati della chiamata
     * @param total_pages intero che indica le pagine totali di risultato
     * @param total_results intero che indica il totale dei risultati
     */
    public ComingSoonApiTmdbResponse(int page, List<MovieApiTmdbResponse> results, int total_pages,
                                     int total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
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

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
