package com.example.cinemhub.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cinemhub.models.ComingSoonApiTmdbResponse;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieApiTmdbResponse;
import com.example.cinemhub.models.NowPlayingApiTmdbResponse;
import com.example.cinemhub.models.RecommendationsApiTmdbResponse;
import com.example.cinemhub.models.TopRatedApiTmdbResponse;
import com.example.cinemhub.service.TmdbService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbRepository {
    private static TmdbRepository instance;
    private TmdbService tmdbService;
    private final String API_BASE_URL="https://api.themoviedb.org/3/";
    private final String API_KEY="711c6e104d15a00a5aff3a04d7c0ceee";

    private final String TAG="TmdbRepository";

    private TmdbRepository(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        tmdbService = retrofit.create(TmdbService.class);
    }

    public static TmdbRepository getInstance(){
        if(instance==null){
            instance=new TmdbRepository();
        }
        return instance;
    }

    public void getNowPlaying(MutableLiveData<List<Movie>> movieNowPlaying, String language, int page){
        Call<NowPlayingApiTmdbResponse> call = tmdbService.getNowPlaying(language , page, API_KEY);

        call.enqueue(new Callback<NowPlayingApiTmdbResponse>() {
            @Override
            public void onResponse(Call<NowPlayingApiTmdbResponse> call, Response<NowPlayingApiTmdbResponse> response) {
                List<MovieApiTmdbResponse> movies =response.body().getResults();
                Log.d(TAG, "callback nowplaying ok");
                List<Movie> res=new ArrayList<Movie>();
                for (int i=0;i<movies.size();i++) {
                    res.add(new Movie(movies.get(i)));
                }
                movieNowPlaying.postValue(res);
            }

            @Override
            public void onFailure(Call<NowPlayingApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getTopRated(MutableLiveData<List<Movie>> movieTopRated, String language, int page){
        Call<TopRatedApiTmdbResponse> call=tmdbService.getTopRated(language, page, API_KEY);
        call.enqueue(new Callback<TopRatedApiTmdbResponse>() {
            @Override
            public void onResponse(Call<TopRatedApiTmdbResponse> call, Response<TopRatedApiTmdbResponse> response) {
                List<MovieApiTmdbResponse> movies =response.body().getResults();
                Log.d(TAG, "callback toprated ok");
                List<Movie> res=new ArrayList<Movie>();
                for (int i=0;i<movies.size();i++) {
                    res.add(new Movie(movies.get(i)));
                }
                movieTopRated.postValue(res);
            }

            @Override
            public void onFailure(Call<TopRatedApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getComingSoon(MutableLiveData<List<Movie>> movieComingSoon, String language, int page){
        Call<ComingSoonApiTmdbResponse> call=tmdbService.getComingSoon(language, page, API_KEY);
        call.enqueue(new Callback<ComingSoonApiTmdbResponse>() {
            @Override
            public void onResponse(Call<ComingSoonApiTmdbResponse> call, Response<ComingSoonApiTmdbResponse> response) {
                List<MovieApiTmdbResponse> movies =response.body().getResults();
                Log.d(TAG, "callback comingsoon ok");
                List<Movie> res=new ArrayList<Movie>();
                for (int i=0;i<movies.size();i++) {
                    res.add(new Movie(movies.get(i)));
                }
                movieComingSoon.postValue(res);
            }

            @Override
            public void onFailure(Call<ComingSoonApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getRecommendations(MutableLiveData<List<Movie>> movieRecommendations, int movie_id,String language, int page){
        Call<RecommendationsApiTmdbResponse> call=tmdbService.getRecommendations(movie_id, language, page, API_KEY);
        call.enqueue(new Callback<RecommendationsApiTmdbResponse>() {
            @Override
            public void onResponse(Call<RecommendationsApiTmdbResponse> call, Response<RecommendationsApiTmdbResponse> response) {
                List<MovieApiTmdbResponse> movies =response.body().getResults();
                Log.d(TAG, "callback recommendations ok");
                List<Movie> res=new ArrayList<Movie>();
                for (int i=0;i<movies.size();i++) {
                    res.add(new Movie(movies.get(i)));
                }
                movieRecommendations.postValue(res);
            }

            @Override
            public void onFailure(Call<RecommendationsApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }
}
