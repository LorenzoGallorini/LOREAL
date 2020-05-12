package com.example.cinemhub.service;

import com.example.cinemhub.models.NowPlayingApiTmdbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbService {
    @GET("movie/now_playing")
    Call<NowPlayingApiTmdbResponse> getNowPlaying(@Query("language") String language,
                                                  @Query("page") int page,
                                                  @Query("api_key") String api_key);
}
