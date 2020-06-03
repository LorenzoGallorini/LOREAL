package com.example.cinemhub.ui.toprated;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class TopRatedViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Movie>>> movieTopRated;
    private int page=1;
    private int currentResults;
    private boolean isLoading;

    public LiveData<Resource<List<Movie>>> getMovieTopRated(String language, boolean checkAdult, String region){
        if(movieTopRated==null){
            movieTopRated=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult, region);
        }
        return movieTopRated;
    }
    public LiveData<Resource<List<Movie>>> getMoreMovieTopRated(String language, boolean checkAdult, String region){
        TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult, region);
        return movieTopRated;
    }
    public MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
        return movieTopRated;
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
