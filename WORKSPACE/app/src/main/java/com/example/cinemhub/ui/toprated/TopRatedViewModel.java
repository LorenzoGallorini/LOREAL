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
    private String region_called;

    LiveData<Resource<List<Movie>>> getMovieTopRated(String language, boolean checkAdult, String region){
        if(!region.equals(region_called)){
            clear();
        }
        if(movieTopRated==null){
            movieTopRated= new MutableLiveData<>();
            TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult, region);
        }
        return movieTopRated;
    }
    void getMoreMovieTopRated(String language, boolean checkAdult, String region){
        TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult, region);
    }
    MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
        return movieTopRated;
    }

    private void clear(){
        movieTopRated=null;
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
