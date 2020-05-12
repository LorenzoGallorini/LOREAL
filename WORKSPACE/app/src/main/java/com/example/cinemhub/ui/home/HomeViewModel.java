package com.example.cinemhub.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieNowPlaying;



    public LiveData<List<Movie>> getMovieNowPlaying(){
        if(movieNowPlaying==null){
            movieNowPlaying=new MutableLiveData<List<Movie>>();
            //TODO: recuperare in maniera asincrona i dati
        }
        return movieNowPlaying;
    }
}