package com.example.cinemhub.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.GenreApiTmdbResponse;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.repositories.TmdbRepository;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<Resource<GenreApiTmdbResponse>> genreMutableLiveData;
    public LiveData<Resource<GenreApiTmdbResponse>> getGenre(String language) {
        if (genreMutableLiveData == null) {
            genreMutableLiveData = new MutableLiveData<Resource<GenreApiTmdbResponse>>();
            TmdbRepository.getInstance().getGenres(genreMutableLiveData, language);
        }
        return genreMutableLiveData;
    }
}
