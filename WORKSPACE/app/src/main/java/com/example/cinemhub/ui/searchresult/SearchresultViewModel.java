package com.example.cinemhub.ui.searchresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class SearchresultViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Movie>>> movieSearch;
    private int page=1;
    private int currentResults;
    private boolean isLoading;
    public  void reset()
    {
        movieSearch =null;
        page=1;
        currentResults=0;
        isLoading=false;
    }

    public LiveData<Resource<List<Movie>>> getMovieSearch(String language, boolean checkAdult,String query,String region,int year){
        if(movieSearch ==null){
            movieSearch = new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getSearcMovie(movieSearch, language, page, checkAdult,query,region,year);
        }
        return movieSearch;
    }

    public LiveData<Resource<List<Movie>>> getMoreMovieSearch(String language, boolean checkAdult,String query,String region,int year){
        TmdbRepository.getInstance().getSearcMovie(movieSearch, language, page, checkAdult,query,region,year);
        return movieSearch;
    }

    public MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
        return movieSearch;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
