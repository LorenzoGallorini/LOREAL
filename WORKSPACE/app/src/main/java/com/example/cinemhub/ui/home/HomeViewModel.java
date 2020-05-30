package com.example.cinemhub.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Movie>>> movieNowPlaying;
    private MutableLiveData<Resource<List<Movie>>> movieTopRated;
    private MutableLiveData<Resource<List<Movie>>> movieComingSoon;



    public LiveData<Resource<List<Movie>>> getMovieNowPlaying(String language, int page, boolean checkAdult){
        if(movieNowPlaying==null){
            movieNowPlaying=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getNowPlaying(movieNowPlaying, language, page, checkAdult);
        }
        return movieNowPlaying;
    }

    public LiveData<Resource<List<Movie>>> getMovieTopRated(String language, int page, boolean checkAdult){
        if(movieTopRated==null){
            movieTopRated=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult);
        }
        return movieTopRated;
    }

    public LiveData<Resource<List<Movie>>> getMovieComingSoon(String language, int page, boolean checkAdult){
        if(movieComingSoon==null){
            movieComingSoon=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult);
        }
        return movieComingSoon;
    }

}