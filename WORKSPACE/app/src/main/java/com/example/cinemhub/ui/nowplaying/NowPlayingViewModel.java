package com.example.cinemhub.ui.nowplaying;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class NowPlayingViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Movie>>> movieNowPlaying;
    private int page=1;
    private int currentResults;
    private boolean isLoading;
    private String region_called;

    public LiveData<Resource<List<Movie>>> getMovieNowPlaying(String language, boolean checkAdult, String region){
        if(!region.equals(region_called)){
            clear();
        }
        if(movieNowPlaying==null){
            movieNowPlaying=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getNowPlaying(movieNowPlaying, language, page, checkAdult, region);
        }
        return movieNowPlaying;
    }
    public LiveData<Resource<List<Movie>>> getMoreMovieNowPlaying(String language, boolean checkAdult, String region){
        TmdbRepository.getInstance().getNowPlaying(movieNowPlaying, language, page, checkAdult, region);
        return movieNowPlaying;
    }
    public MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
        return movieNowPlaying;
    }

    private void clear(){
        movieNowPlaying=null;
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
