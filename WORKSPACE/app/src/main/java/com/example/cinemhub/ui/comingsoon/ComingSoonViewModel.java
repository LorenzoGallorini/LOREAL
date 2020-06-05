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


    LiveData<Resource<List<Movie>>> getMovieComingSoon(String language, boolean checkAdult, String region){
        if(!(region.equals(region_called))){
            clear();
        }
        if(movieComingSoon==null ){
            movieComingSoon = new MutableLiveData<>();
            TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult, region);
            region_called=region;
        }
        return movieComingSoon;
    }

    void getMoreMovieComingSoon(String language, boolean checkAdult, String region){
        TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult, region);
    }

    MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
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

    int getCurrentResults() {
        return currentResults;
    }

    void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    boolean isLoading() {
        return isLoading;
    }

    void setLoading(boolean loading) {
        isLoading = loading;
    }
}
