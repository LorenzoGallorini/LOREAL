package com.example.cinemhub.ui.comingsoon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class ComingSoonViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieComingSoon;

    public LiveData<List<Movie>> getMovieComingSoon(String language, int page, boolean checkAdult, Movie[] movies){
        if(movieComingSoon==null){
            movieComingSoon = new MutableLiveData<List<Movie>>();

            if(movies==null) {
                TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult);
            }else{
                movieComingSoon.postValue(Movie.toList(movies, checkAdult));
            }
        }
        return movieComingSoon;
    }
}
