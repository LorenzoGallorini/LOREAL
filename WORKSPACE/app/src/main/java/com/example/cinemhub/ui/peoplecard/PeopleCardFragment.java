package com.example.cinemhub.ui.peoplecard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.CreditsListHorizontalAdapter;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentPeopleCardBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieApiTmdbResponse;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.PeopleCreditsApiTmdbResponse;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class PeopleCardFragment extends Fragment {

    private PeopleCardViewModel mViewModel;
    private final String TAG = "PeopleCardFragment";
    private FragmentPeopleCardBinding binding;

    public static PeopleCardFragment newInstance() {
        return new PeopleCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPeopleCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);

        mViewModel=new ViewModelProvider(this).get(PeopleCardViewModel.class);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        binding.filmographyRecyclerView.setLayoutManager(layoutManager);

        int value= PeopleCardFragmentArgs.fromBundle(getArguments()).getPeopleId();

        final Observer<Resource<People>> observer_details = new Observer<Resource<People>>() {
            @Override
            public void onChanged(Resource<People> peopleResource) {
                if(peopleResource!=null && peopleResource.getData()!= null){
                    People people=peopleResource.getData();
                    Log.d(TAG, "people tmdb details"+people);
                    if(people.getProfile_path()!=null&&!people.getProfile_path().equals("")){
                        Picasso.get().load(Constants.IMAGE_BASE_URL + people.getProfile_path()).into(binding.peopleImage);
                    }else{
                        binding.peopleImage.setImageResource(R.drawable.no_image_avaiable);
                    }

                    binding.peopleNameTitle.setText(people.getName());
                    binding.knownForValue.setText(people.getKnown_for_department());
                    if(people.getGender()==2){
                        binding.genderValue.setText(R.string.gender_male);
                    }else if(people.getGender()==1){
                        binding.genderValue.setText(R.string.gender_female);
                    }else{
                        binding.genderValue.setText(getResources().getString(android.R.string.unknownName));
                    }
                    binding.birthDateValue.setText(people.getBirth_date());
                    if(people.getDeath_date()!=null&& !people.getDeath_date().equals("")){
                        binding.dayOfDeathValue.setText(people.getDeath_date());
                    }
                    else{
                        binding.dayOfDeathValue.setVisibility(View.INVISIBLE);
                        binding.deathDate.setVisibility(View.INVISIBLE);
                    }
                    binding.placeOfBirthValue.setText(people.getPlace_of_birth());

                    binding.peopleDescription.setText(people.getBiography());

                    if(people.getKnown_for_department().equals(Constants.DEPARTMENT_ACTING)) {
                        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_actor_card));
                    }else if(people.getKnown_for_department().equals(Constants.DEPARTMENT_DIRECTING)){
                        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_director_card));
                    }else{
                        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.people_card));
                    }

                    final Observer<Resource<PeopleCreditsApiTmdbResponse>> observer_credits=new Observer<Resource<PeopleCreditsApiTmdbResponse>>() {
                        @Override
                        public void onChanged(Resource<PeopleCreditsApiTmdbResponse> peopleCreditsApiTmdbResponseResource) {
                            PeopleCreditsApiTmdbResponse peopleCreditsApiTmdbResponse=peopleCreditsApiTmdbResponseResource.getData();
                            MovieApiTmdbResponse[] movies;
                            if(people.getKnown_for_department().equals(Constants.DEPARTMENT_ACTING)){
                                movies=peopleCreditsApiTmdbResponse.getCast();
                            }else{
                                movies=peopleCreditsApiTmdbResponse.getCrew();
                            }
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
                            boolean isAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
                            if(movies!= null && movies.length>0){
                                List<Movie> m=Movie.toList(movies, isAdult);
                                Collections.sort(m, new Movie.MoviePopularityComparator());
                                if(m.size()>0) {
                                    if (m.size()>12)
                                        m=m.subList(0, 12);
                                    MovieListVerticalAdapter movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(),
                                            m  , new MovieListVerticalAdapter.OnItemClickListener() {
                                        @Override
                                        public void OnItemClick(Movie movie) {
                                            Navigation.findNavController(view).navigate(PeopleCardFragmentDirections.actionNavigationPeopleCardToNavigationMovieCard(movie.getId()));

                                        }
                                    });
                                    binding.filmographyRecyclerView.setAdapter(movieListVerticalAdapter);
                                }
                            }}

                    };
                    mViewModel.getPeopleCredits(value,getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_credits);
                }
                else{
                    if(peopleResource!= null && peopleResource.getStatusMessage()!=null) {
                        Log.d(TAG, "ERROR CODE: " + peopleResource.getStatusCode() + " ERROR MESSAGE: " + peopleResource.getStatusMessage());
                    }
                    Toast toast;
                    toast = Toast.makeText(getContext(), getString(R.string.error_message_people) , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        };
        mViewModel.getPeopleDetails(value, getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_details);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(PeopleCardFragmentDirections.actionNavigationPeopleCardToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(PeopleCardFragmentDirections.actionNavigationPeopleCardToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }

}
