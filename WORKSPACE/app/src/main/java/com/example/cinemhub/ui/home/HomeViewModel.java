package com.example.cinemhub.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieNowPlaying;



    public LiveData<List<Movie>> getMovieNowPlaying(String language, int page){
        if(movieNowPlaying==null){
            movieNowPlaying=new MutableLiveData<List<Movie>>();
            TmdbRepository.getInstance().getNowPlaying(movieNowPlaying, language, page);
        }
        return movieNowPlaying;
    }
}