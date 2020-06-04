package com.example.cinemhub.ui.comingsoon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class ComingSoonViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Movie>>> movieComingSoon;
    private int page=1;
    private int currentResults;
    private boolean isLoading;
    private String region_called;


    public LiveData<Resource<List<Movie>>> getMovieComingSoon(String language, boolean checkAdult, String region){
        if(!(region.equals(region_called))){
            clear();
        }
        if(movieComingSoon==null ){
            movieComingSoon = new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult, region);
            region_called=region;
        }
        return movieComingSoon;
    }

    public LiveData<Resource<List<Movie>>> getMoreMovieComingSoon(String language, boolean checkAdult, String region){
        TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult, region);
        return movieComingSoon;
    }

    public MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
        return movieComingSoon;
    }

    private void clear(){
        movieComingSoon=null;
        page=1;
        currentResults=0;
        isLoading=false;
        region_called="";
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
