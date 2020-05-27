package com.example.cinemhub.ui.shake2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class Shake2ViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieOnShake;

    public LiveData<List<Movie>> getMovieOnShake(int movie_id, String language, int page, boolean checkAdult){
        if(movieOnShake==null){
            movieOnShake=new MutableLiveData<List<Movie>>();
            TmdbRepository.getInstance().getRecommendations(movieOnShake, movie_id,language, page, checkAdult);
        }
        return movieOnShake;
    }



    // TODO: Implement the ViewModel
}
