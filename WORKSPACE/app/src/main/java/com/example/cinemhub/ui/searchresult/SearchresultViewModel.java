package com.example.cinemhub.ui.searchresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

import java.util.List;

public class SearchresultViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Movie>>> movieSearch;
    private int page=1;
    private int currentResults;
    private boolean isLoading;
    private MutableLiveData<Resource<List<People>>> peopleSearch;
    private int peoplePage=1;
    private int peopleCurrentResults;
    private boolean peopleIsLoading;
    public  void reset()
    {
        movieSearch =null;
        page=1;
        currentResults=0;
        isLoading=false;
        peopleSearch =null;
        peoplePage=1;
        peopleCurrentResults=0;
        peopleIsLoading=false;
    }

    public LiveData<Resource<List<Movie>>> getMovieSearch(String language, boolean checkAdult,String query,String region,int year){
        if(movieSearch ==null){
            movieSearch = new MutableLiveData<Resource<List<Movie>>>();
            TmdbRepository.getInstance().getSearchMovie(movieSearch, language, page, checkAdult,query,region,year);
        }
        return movieSearch;
    }
    public LiveData<Resource<List<People>>> getPeopleSearch(String language, boolean checkAdult,String query,String region){

        if(peopleSearch ==null){
            peopleSearch = new MutableLiveData<Resource<List<People>>>();
            TmdbRepository.getInstance().getSearchPeople(peopleSearch, language, page, checkAdult,query,region);
        }
        return peopleSearch;
    }

    public LiveData<Resource<List<Movie>>> getMoreMovieSearch(String language, boolean checkAdult,String query,String region,int year){
        TmdbRepository.getInstance().getSearchMovie(movieSearch, language, page, checkAdult,query,region,year);
        return movieSearch;
    }
    public LiveData<Resource<List<People>>> getMorePeopleSearch(String language, boolean checkAdult,String query,String region){
        TmdbRepository.getInstance().getSearchPeople(peopleSearch, language, page, checkAdult,query,region);
        return peopleSearch;
    }

    public MutableLiveData<Resource<List<Movie>>> getMovieLiveData(){
        return movieSearch;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public int getPeoplePage() {
        return peoplePage;
    }

    public void setPeoplePage(int peoplePage) {
        this.peoplePage = peoplePage;
    }

    public int getPeopleCurrentResults() {
        return peopleCurrentResults;
    }

    public void setPeopleCurrentResults(int peopleCurrentResults) {
        this.peopleCurrentResults = peopleCurrentResults;
    }

    public boolean isPeopleIsLoading() {
        return peopleIsLoading;
    }

    public void setPeopleIsLoading(boolean peopleIsLoading) {
        this.peopleIsLoading = peopleIsLoading;
    }
}
