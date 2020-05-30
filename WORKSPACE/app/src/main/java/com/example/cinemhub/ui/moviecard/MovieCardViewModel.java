package com.example.cinemhub.ui.moviecard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

public class MovieCardViewModel extends ViewModel {
    private MutableLiveData<Resource<Movie>> movieCardViewModel;
    private MutableLiveData<Resource<MovieCreditsApiTmdbResponse>> movieCreditsViewModel;
    private MutableLiveData<Resource<GetVideosApiTmdbResponse>> getVideosMutableLiveData;


    public LiveData<Resource<Movie>> getMovieDetails(int movie_id, String language){
        if(movieCardViewModel==null){
            movieCardViewModel=new MutableLiveData<Resource<Movie>>();
            TmdbRepository.getInstance().getMovieDetails(movieCardViewModel, movie_id, language);
        }
        return movieCardViewModel;
    }
    public LiveData<Resource<MovieCreditsApiTmdbResponse>> getMovieCredits(int movie_id){
        if(movieCreditsViewModel==null){
            movieCreditsViewModel=new MutableLiveData<Resource<MovieCreditsApiTmdbResponse>>();
            TmdbRepository.getInstance().getMovieCredits(movieCreditsViewModel, movie_id);
        }
        return movieCreditsViewModel;
    }
    public LiveData<Resource<GetVideosApiTmdbResponse>> getVideos(int movie_id, String language){
        if(getVideosMutableLiveData==null){
            getVideosMutableLiveData=new MutableLiveData<Resource<GetVideosApiTmdbResponse>>();
            TmdbRepository.getInstance().getVideos(getVideosMutableLiveData,movie_id,language);
        }
        return getVideosMutableLiveData;
    }


}
