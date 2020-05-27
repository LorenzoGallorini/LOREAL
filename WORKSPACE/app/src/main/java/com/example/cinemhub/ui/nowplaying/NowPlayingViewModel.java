package com.example.cinemhub.ui.nowplaying;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class NowPlayingViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieNowPlaying;

    public LiveData<List<Movie>> getMovieNowPlaying(String language, int page, boolean checkAdult){
        if(movieNowPlaying==null){
            movieNowPlaying=new MutableLiveData<List<Movie>>();
            TmdbRepository.getInstance().getNowPlaying(movieNowPlaying, language, page, checkAdult);
        }
        return movieNowPlaying;
    }
}
