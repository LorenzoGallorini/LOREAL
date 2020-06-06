package com.example.cinemhub.ui.searchresult.searchmovieresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class SearchMovieResultViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Movie>>> movieSearch;
    private int moviePage =1;
    private int movieCurrentResults;
    private boolean movieIsLoading;

    public  void reset() {
        movieSearch = null;
        moviePage = 1;
        movieCurrentResults = 0;
        movieIsLoading = false;
    }

    public LiveData<Resource<List<Movie>>> getMovieSearch(String language, boolean checkAdult, String query, String region, int year){
        if(movieSearch ==null){
            movieSearch = new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getSearchMovie(movieSearch, language, moviePage, checkAdult,query,region,year);
        }
        return movieSearch;
    }
    public LiveData<Resource<List<Movie>>> getMoreMovieSearch(String language, boolean checkAdult,String query,String region,int year){
        TmdbRepository.getInstance().getSearchMovie(movieSearch, language, moviePage, checkAdult,query,region,year);
        return movieSearch;
    }
    public MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
        return movieSearch;
    }


    public int getMoviePage() {
        return moviePage;
    }

    public void setMoviePage(int moviePage) {
        this.moviePage = moviePage;
    }

    public int getMovieCurrentResults() {
        return movieCurrentResults;
    }

    public void setMovieCurrentResults(int movieCurrentResults) {
        this.movieCurrentResults = movieCurrentResults;
    }

    public boolean isMovieIsLoading() {
        return movieIsLoading;
    }

    public void setMovieIsLoading(boolean movieIsLoading) {
        this.movieIsLoading = movieIsLoading;
    }
}
