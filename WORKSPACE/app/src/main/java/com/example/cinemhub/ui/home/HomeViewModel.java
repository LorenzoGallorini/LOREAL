package com.example.cinemhub.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Movie>>> movieNowPlaying;
    private MutableLiveData<Resource<List<Movie>>> movieTopRated;
    private MutableLiveData<Resource<List<Movie>>> movieComingSoon;
    private String region_calledNP;
    private String region_calledTR;
    private String region_calledCS;




    LiveData<Resource<List<Movie>>> getMovieNowPlaying(String language, int page, boolean checkAdult, String region){
        if(region!=null && !region.equals(region_calledNP)){
            movieNowPlaying=null;
        }
        if(movieNowPlaying==null){
            movieNowPlaying=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getNowPlaying(movieNowPlaying, language, page, checkAdult, region);
            region_calledNP=region;
        }
        return movieNowPlaying;
    }

    LiveData<Resource<List<Movie>>> getMovieTopRated(String language, int page, boolean checkAdult, String region){
        if(region!=null && !region.equals(region_calledTR)){
            movieTopRated=null;
        }
        if(movieTopRated==null){
            movieTopRated=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getTopRated(movieTopRated, language, page, checkAdult, region);
            region_calledTR=region;
        }
        return movieTopRated;
    }

    LiveData<Resource<List<Movie>>> getMovieComingSoon(String language, int page, boolean checkAdult, String region){
        if(region!=null && !region.equals(region_calledCS)){
            movieComingSoon=null;
        }
        if(movieComingSoon==null){
            movieComingSoon=new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getComingSoon(movieComingSoon, language, page, checkAdult, region);
            region_calledCS=region;
        }
        return movieComingSoon;
    }



}