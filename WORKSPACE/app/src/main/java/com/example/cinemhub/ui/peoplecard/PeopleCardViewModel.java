package com.example.cinemhub.ui.peoplecard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.models.People;
import com.example.cinemhub.models.PeopleCreditsApiTmdbResponse;
import com.example.cinemhub.repositories.TmdbRepository;

public class PeopleCardViewModel extends ViewModel {
    private MutableLiveData<People> peopleMutableLiveData;
    private MutableLiveData<PeopleCreditsApiTmdbResponse> peopleCreditsMutableLiveData;

    public LiveData<People> getPeopleDetails(int people_id, String language){
        if(peopleMutableLiveData==null){
            peopleMutableLiveData=new MutableLiveData<People>();
            TmdbRepository.getInstance().getPeopleDetails(peopleMutableLiveData, people_id, language);
        }
        return peopleMutableLiveData;
    }
    public LiveData<PeopleCreditsApiTmdbResponse> getPeopleCredits(int people_id, String language){
        if(peopleCreditsMutableLiveData==null){
            peopleCreditsMutableLiveData=new MutableLiveData<PeopleCreditsApiTmdbResponse>();
            TmdbRepository.getInstance().getPeopleCredits(peopleCreditsMutableLiveData, people_id, language);
        }
        return peopleCreditsMutableLiveData;
    }
}
