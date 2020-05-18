package com.example.cinemhub.ui.moviecard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.repositories.TmdbRepository;

public class MovieCardViewModel extends ViewModel {
    private MutableLiveData<Movie> movieCardViewModel;
    private MutableLiveData<MovieCreditsApiTmdbResponse> movieCreditsViewModel;
    private MutableLiveData<GetVideosApiTmdbResponse> getVideosMutableLiveData;


    public LiveData<Movie> getMovieDetails(int movie_id, String language){
        if(movieCardViewModel==null){
            movieCardViewModel=new MutableLiveData<Movie>();
            TmdbRepository.getInstance().getMovieDetails(movieCardViewModel, movie_id, language);
        }
        return movieCardViewModel;
    }
    public LiveData<MovieCreditsApiTmdbResponse> getMovieCredits(int movie_id){
        if(movieCreditsViewModel==null){
            movieCreditsViewModel=new MutableLiveData<MovieCreditsApiTmdbResponse>();
            TmdbRepository.getInstance().getMovieCredits(movieCreditsViewModel, movie_id);
        }
        return movieCreditsViewModel;
    }
    public LiveData<GetVideosApiTmdbResponse> getVideos(int movie_id, String language){
        if(getVideosMutableLiveData==null){
            getVideosMutableLiveData=new MutableLiveData<GetVideosApiTmdbResponse>();
            TmdbRepository.getInstance().getVideos(getVideosMutableLiveData,movie_id,language);
        }
        return getVideosMutableLiveData;
    }


}
