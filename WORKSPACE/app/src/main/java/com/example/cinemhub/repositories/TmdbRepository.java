package com.example.cinemhub.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.NowPlayingApiTmdbResponse;
import com.example.cinemhub.service.TmdbService;

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
        Call<NowPlayingApiTmdbResponse> call = tmdbService.getNowPlaying("it-IT", 2, API_KEY);

        call.enqueue(new Callback<NowPlayingApiTmdbResponse>() {
            @Override
            public void onResponse(Call<NowPlayingApiTmdbResponse> call, Response<NowPlayingApiTmdbResponse> response) {
                response.body().getResults();
                Log.d(TAG, "callback nowplaying ok");
            }

            @Override
            public void onFailure(Call<NowPlayingApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }
}
