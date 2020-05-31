package com.example.cinemhub.service;

import com.example.cinemhub.models.ComingSoonApiTmdbResponse;
import com.example.cinemhub.models.GenreApiTmdbResponse;
import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.MovieApiTmdbResponse;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.models.NowPlayingApiTmdbResponse;
import com.example.cinemhub.models.PeopleApiTmdbResponse;
import com.example.cinemhub.models.PeopleCreditsApiTmdbResponse;
import com.example.cinemhub.models.RecommendationsApiTmdbResponse;
import com.example.cinemhub.models.SearchMovieApiTmdbResponse;
import com.example.cinemhub.models.SearchPeopleApiTmdbResponse;
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

    @GET("movie/{movie_id}")
    Call<MovieApiTmdbResponse> getMovieDetails (@Path("movie_id") int movie_id,
                                                @Query("language") String language,
                                                @Query("api_key") String api_key);

    @GET("movie/{movie_id}/credits")
    Call<MovieCreditsApiTmdbResponse> getMovieCredits (@Path("movie_id") int movie_id,
                                                  @Query("api_key") String api_key);

    @GET("person/{person_id}")
    Call<PeopleApiTmdbResponse> getPeopleDetails (@Path("person_id") int person_id,
                                                  @Query("language") String language,
                                                  @Query("api_key") String api_key);

    @GET("person/{person_id}/movie_credits")
    Call<PeopleCreditsApiTmdbResponse> getPeopleCredits(@Path("person_id") int person_id,
                                                        @Query("language") String language,
                                                        @Query("api_key") String api_key);

    @GET("movie/{movie_id}/videos")
    Call<GetVideosApiTmdbResponse> getVideos(@Path("movie_id") int movie_id,
                                             @Query("language") String language,
                                             @Query("api_key") String api_key);

    @GET("genre/movie/list")
    Call<GenreApiTmdbResponse> getGenres(@Query("language") String language,
                                         @Query("api_key") String api_key);

    @GET("search/person")
    Call<SearchPeopleApiTmdbResponse> getSearchPeople(@Query("language") String language,
                                                      @Query("api_key") String api_key,
                                                      @Query("query") String query,
                                                      @Query("page") int page,
                                                      @Query("include_adult") boolean include_adult,
                                                      @Query("region") String region);

    @GET("search/movie")
    Call<SearchMovieApiTmdbResponse> getSearchMovie(@Query("language") String language,
                                                    @Query("api_key") String api_key,
                                                    @Query("query") String query,
                                                    @Query("page") int page,
                                                    @Query("include_adult") boolean include_adult,
                                                    @Query("region") String region,
                                                    @Query("primary_release_year") int primary_release_year);

}
