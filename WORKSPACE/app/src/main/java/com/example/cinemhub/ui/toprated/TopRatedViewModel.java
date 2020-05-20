package com.example.cinemhub.ui.toprated;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class TopRatedViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieTopRated;

    public LiveData<List<Movie>> getMovieTopRated(String language, int page){
        if(movieTopRated==null){
            movieTopRated=new MutableLiveData<List<Movie>>();
            TmdbRepository.getInstance().getTopRated(movieTopRated, language, page);
        }
        return movieTopRated;
    }
}
