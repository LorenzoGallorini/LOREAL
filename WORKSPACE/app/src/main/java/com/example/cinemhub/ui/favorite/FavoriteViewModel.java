package com.example.cinemhub.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieMutableLiveData;


    public LiveData<List<Movie>> getFavoriteMovie(List<Integer> favoriteMovies, String language){
        if(movieMutableLiveData==null){
            movieMutableLiveData=new MutableLiveData<List<Movie>>();
            MutableLiveData<Movie> app=new MutableLiveData<Movie>();
            List<Movie> movies=new ArrayList<Movie>();
            for (int i=0;i<favoriteMovies.size();i++){
                TmdbRepository.getInstance().getMovieDetails(app, favoriteMovies.get(i), language);
                movies.add(app.getValue());
            }
            movieMutableLiveData.postValue(movies);
        }
        return movieMutableLiveData;
    }
}
