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

    public LiveData<Resource<List<Movie>>> getMovieTopRated(String language, int page, boolean checkAdult, Resource<List<Movie>> movies){
        if(movieTopRated==null){
            movieTopRated=new MutableLiveData<Resource<List<Movie>>>();
            if(movies==null){
                TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult);
            }else{
                movieTopRated.postValue(movies);
            }
        }
        return movieTopRated;
    }
}
