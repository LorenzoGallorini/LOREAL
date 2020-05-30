package com.example.cinemhub.ui.shake2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class Shake2ViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Movie>>> movieOnShake;
    private MutableLiveData<Resource<List<Movie>>> movieTopRated;
    public void reset()
    {
        movieOnShake=null;
    }

    public LiveData<Resource<List<Movie>>> getMovieOnShake(int movie_id, String language, int page, boolean checkAdult){
        if(movieOnShake==null){
            movieOnShake=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getRecommendations(movieOnShake, movie_id,language, page, checkAdult);
        }
        return movieOnShake;
    }

    public LiveData<Resource<List<Movie>>> getMovieTopRated(String language, int page, boolean checkAdult){
        if(movieTopRated==null){
            movieTopRated=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult);
        }
        return movieTopRated;
    }}
