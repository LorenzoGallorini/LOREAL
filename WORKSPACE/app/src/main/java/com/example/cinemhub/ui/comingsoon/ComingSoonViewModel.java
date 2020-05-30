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

    public LiveData<Resource<List<Movie>>> getMovieComingSoon(String language, int page, boolean checkAdult, Resource<List<Movie>> movies){
        if(movieComingSoon==null){
            movieComingSoon = new MutableLiveData<Resource<List<Movie>>>();

            if(movies==null) {
                TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult);
            }else{

                movieComingSoon.postValue(movies);
            }
        }
        return movieComingSoon;
    }
}
