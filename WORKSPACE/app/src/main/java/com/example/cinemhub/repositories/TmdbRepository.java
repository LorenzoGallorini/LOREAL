package com.example.cinemhub.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cinemhub.models.ComingSoonApiTmdbResponse;
import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieApiTmdbResponse;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.models.NowPlayingApiTmdbResponse;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.PeopleApiTmdbResponse;
import com.example.cinemhub.models.PeopleCreditsApiTmdbResponse;
import com.example.cinemhub.models.RecommendationsApiTmdbResponse;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.models.TopRatedApiTmdbResponse;
import com.example.cinemhub.service.TmdbService;
import com.example.cinemhub.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbRepository {
    private static TmdbRepository instance;
    private TmdbService tmdbService;


    private final String TAG="TmdbRepository";

    private TmdbRepository(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_TMDB_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        tmdbService = retrofit.create(TmdbService.class);
    }

    public static TmdbRepository getInstance(){
        if(instance==null){
            instance=new TmdbRepository();
        }
        return instance;
    }

    public void getNowPlaying(MutableLiveData<Resource<List<Movie>>> movieNowPlaying, String language, int page, boolean checkAdult){

        Call<NowPlayingApiTmdbResponse> call = tmdbService.getNowPlaying(language , page, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<NowPlayingApiTmdbResponse>() {
            @Override
            public void onResponse(Call<NowPlayingApiTmdbResponse> call, Response<NowPlayingApiTmdbResponse> response) {
                Resource<List<Movie>> resource=new Resource<>();

                if(response.isSuccessful() && response.body()!=null){

                    List<MovieApiTmdbResponse> movies =response.body().getResults();
                    Log.d(TAG, "callback nowplaying ok");
                    List<Movie> res=new ArrayList<Movie>();
                    for (int i=0;i<movies.size();i++) {
                        if(!checkAdult||!movies.get(i).isAdult()){
                            res.add(new Movie(movies.get(i)));
                        }
                    }
                    resource.setData(res);
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                }else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getNowPlaying=null");
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                movieNowPlaying.postValue(resource);

            }
            @Override
            public void onFailure(Call<NowPlayingApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());

            }
        });
    }

    public void getTopRated(MutableLiveData<Resource<List<Movie>>> movieTopRated, String language, int page, boolean checkAdult){
        Call<TopRatedApiTmdbResponse> call=tmdbService.getTopRated(language, page, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<TopRatedApiTmdbResponse>() {
            @Override
            public void onResponse(Call<TopRatedApiTmdbResponse> call, Response<TopRatedApiTmdbResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Resource<List<Movie>> resource=new Resource();
                    List<MovieApiTmdbResponse> movies = response.body().getResults();
                    Log.d(TAG, "callback toprated ok");
                    List<Movie> res = new ArrayList<Movie>();
                    for (int i = 0; i < movies.size(); i++) {
                        if(!checkAdult||!movies.get(i).isAdult()){
                            res.add(new Movie(movies.get(i)));
                        }
                    }
                    resource.setData(res);
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());

                    movieTopRated.postValue(resource);
                } else {
                    Log.d(TAG, "ERROR: getTopRated=null");
                }
            }

            @Override
            public void onFailure(Call<TopRatedApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getComingSoon(MutableLiveData<Resource<List<Movie>>> movieComingSoon, String language, int page, boolean checkAdult){
        Call<ComingSoonApiTmdbResponse> call=tmdbService.getComingSoon(language, page, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<ComingSoonApiTmdbResponse>() {
            @Override
            public void onResponse(Call<ComingSoonApiTmdbResponse> call, Response<ComingSoonApiTmdbResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Resource<List<Movie>> resource=new Resource();
                    List<MovieApiTmdbResponse> movies = response.body().getResults();
                    Log.d(TAG, "callback comingsoon ok");
                    List<Movie> res = new ArrayList<Movie>();
                    for (int i = 0; i < movies.size(); i++) {
                        if(!checkAdult||!movies.get(i).isAdult()){
                            res.add(new Movie(movies.get(i)));
                        }
                    }
                    resource.setData(res);
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    movieComingSoon.postValue(resource);
                }
            else{
                Log.d(TAG, "ERROR: getComingSoon=null");
            }
            }

            @Override
            public void onFailure(Call<ComingSoonApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getRecommendations(MutableLiveData<Resource<List<Movie>>> movieRecommendations, int movie_id,String language, int page, boolean checkAdult){
        Call<RecommendationsApiTmdbResponse> call=tmdbService.getRecommendations(movie_id, language, page, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<RecommendationsApiTmdbResponse>() {
            @Override
            public void onResponse(Call<RecommendationsApiTmdbResponse> call, Response<RecommendationsApiTmdbResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Resource<List<Movie>> resource=new Resource();
                    List<MovieApiTmdbResponse> movies = response.body().getResults();
                    Log.d(TAG, "callback recommendations ok");
                    List<Movie> res = new ArrayList<Movie>();
                    for (int i = 0; i < movies.size(); i++) {
                        if(!checkAdult||!movies.get(i).isAdult()){
                            res.add(new Movie(movies.get(i)));
                        }
                    }
                    resource.setData(res);
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    movieRecommendations.postValue(resource);
                }
                else{
                    Log.d(TAG, "ERROR: getRecommendations=null");
                }
            }

            @Override
            public void onFailure(Call<RecommendationsApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getMovieDetails(MutableLiveData<Resource<Movie>> movieDetails, int movie_id, String language){
        Call<MovieApiTmdbResponse> call=tmdbService.getMovieDetails(movie_id, language, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<MovieApiTmdbResponse>() {
            @Override
            public void onResponse(Call<MovieApiTmdbResponse> call, Response<MovieApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Resource<Movie> resource=new Resource();
                    MovieApiTmdbResponse movie = response.body();
                    Log.d(TAG, "callback details ok");
                    resource.setData(new Movie(movie));
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    movieDetails.postValue(resource);
                }
                else{
                    Log.d(TAG, "ERROR: getDatails=null");
                }
            }

            @Override
            public void onFailure(Call<MovieApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getMovieCredits(MutableLiveData<Resource<MovieCreditsApiTmdbResponse>> credits, int movie_id){
        Call<MovieCreditsApiTmdbResponse> call=tmdbService.getMovieCredits(movie_id, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<MovieCreditsApiTmdbResponse>() {
            @Override
            public void onResponse(Call<MovieCreditsApiTmdbResponse> call, Response<MovieCreditsApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Resource<MovieCreditsApiTmdbResponse> resource=new Resource();
                    resource.setData(response.body());
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    credits.postValue(resource);
                    Log.d(TAG, "callback credits ok");

                }
                else{
                    Log.d(TAG, "ERROR: getCredits=null");
                }
            }

            @Override
            public void onFailure(Call<MovieCreditsApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getPeopleDetails (MutableLiveData<Resource<People>> peopleDetails, int person_id, String language){
        Call<PeopleApiTmdbResponse> call=tmdbService.getPeopleDetails(person_id, language, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<PeopleApiTmdbResponse>() {
            @Override
            public void onResponse(Call<PeopleApiTmdbResponse> call, Response<PeopleApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Resource<People> resource=new Resource();
                    PeopleApiTmdbResponse people=response.body();
                    Log.d(TAG, "callback peopledetails ok");
                    resource.setData(new People(people));
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    peopleDetails.postValue(resource);
                }
            }

            @Override
            public void onFailure(Call<PeopleApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getPeopleCredits(MutableLiveData<Resource<PeopleCreditsApiTmdbResponse>> credits, int person_id, String language){
        Call<PeopleCreditsApiTmdbResponse> call=tmdbService.getPeopleCredits(person_id, language,  Constants.API_TMDB_KEY);
        call.enqueue(new Callback<PeopleCreditsApiTmdbResponse>() {
            @Override
            public void onResponse(Call<PeopleCreditsApiTmdbResponse> call, Response<PeopleCreditsApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Resource<PeopleCreditsApiTmdbResponse> resource=new Resource();
                    resource.setData(response.body());
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    credits.postValue(resource);
                    Log.d(TAG, "callback peoplecredits ok");

                }
                else{
                    Log.d(TAG, "ERROR: getCredits=null");
                }
            }

            @Override
            public void onFailure(Call<PeopleCreditsApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    public void getVideos(MutableLiveData<Resource<GetVideosApiTmdbResponse>> videos, int movie_id, String language)
    {
        Call<GetVideosApiTmdbResponse> call=tmdbService.getVideos(movie_id,language, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<GetVideosApiTmdbResponse>() {
            @Override
            public void onResponse(Call<GetVideosApiTmdbResponse> call, Response<GetVideosApiTmdbResponse> response) {

                if(response.isSuccessful() && response.body()!=null) {
                    Resource<GetVideosApiTmdbResponse> resource=new Resource();
                    resource.setData(response.body());
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    videos.postValue(resource);
                    Log.d(TAG, "callback getVideos ok");

                }
                else{
                    Log.d(TAG, "ERROR: getVideos=null");
                }
            }

            @Override
            public void onFailure(Call<GetVideosApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

}
