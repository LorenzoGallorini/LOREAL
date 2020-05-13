package com.example.cinemhub.service;

import com.example.cinemhub.models.ComingSoonApiTmdbResponse;
import com.example.cinemhub.models.NowPlayingApiTmdbResponse;
import com.example.cinemhub.models.RecommendationsApiTmdbResponse;
import com.example.cinemhub.models.TopRatedApiTmdbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbService {
    @GET("movie/now_playing")
    Call<NowPlayingApiTmdbResponse> getNowPlaying (@Query("language") String language,
                                                   @Query("page") int page,
                                                   @Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<TopRatedApiTmdbResponse> getTopRated (@Query("language") String language,
                                               @Query("page") int page,
                                               @Query("api_key") String api_key);

    @GET("movie/upcoming")
    Call<ComingSoonApiTmdbResponse> getComingSoon (@Query("language") String language,
                                                   @Query("page") int page,
                                                   @Query("api_key") String api_key);

    @GET("movie/{movie_id}/recommendations")
    Call<RecommendationsApiTmdbResponse> getRecommendations (@Path("movie_id") int movie_id,
                                                             @Query("language") String language,
                                                             @Query("page") int page,
                                                             @Query("api_key") String api_key);
}
