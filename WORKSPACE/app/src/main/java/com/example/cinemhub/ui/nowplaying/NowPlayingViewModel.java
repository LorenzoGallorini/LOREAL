package com.example.cinemhub.ui.nowplaying;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class NowPlayingViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Movie>>> movieNowPlaying;

    public LiveData<Resource<List<Movie>>> getMovieNowPlaying(String language, int page, boolean checkAdult, Resource<List<Movie>> movies){
        if(movieNowPlaying==null){
            movieNowPlaying=new MutableLiveData<Resource<List<Movie>>>();
            if(movies==null){
                TmdbRepository.getInstance().getNowPlaying(movieNowPlaying, language, page, checkAdult);
            }else{
                movieNowPlaying.postValue(movies);
            }
        }
        return movieNowPlaying;
    }
}
