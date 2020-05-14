package com.example.cinemhub.ui.moviecard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.repositories.TmdbRepository;

public class MovieCardViewModel extends ViewModel {
    private MutableLiveData<Movie> movieCardViewModel;

    public LiveData<Movie> getMovieDetails(int movie_id, String language){
        if(movieCardViewModel==null){
            movieCardViewModel=new MutableLiveData<Movie>();
            TmdbRepository.getInstance().getMovieDetails(movieCardViewModel, movie_id, language);
        }
        return movieCardViewModel;
    }
}
