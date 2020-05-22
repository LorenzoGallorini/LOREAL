package com.example.cinemhub.ui.peoplecard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentPeopleCardBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieApiTmdbResponse;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.PeopleCreditsApiTmdbResponse;
import com.example.cinemhub.ui.moviecard.MovieCardFragmentDirections;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

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

        int value= PeopleCardFragmentArgs.fromBundle(getArguments()).getPeopleId();

        final Observer<People> observer_details = new Observer<People>() {
            @Override
            public void onChanged(People people) {
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

                final Observer<PeopleCreditsApiTmdbResponse> observer_credits=new Observer<PeopleCreditsApiTmdbResponse>() {
                    @Override
                    public void onChanged(PeopleCreditsApiTmdbResponse peopleCreditsApiTmdbResponse) {
                        MovieApiTmdbResponse[] movies;
                        if(people.getKnown_for_department().equals(Constants.DEPARTMENT_ACTING)){
                            movies=peopleCreditsApiTmdbResponse.getCast();
                        }else{
                            movies=peopleCreditsApiTmdbResponse.getCrew();
                        }
                        if(movies.length>0) {

                            setMoviePreview(new Movie(movies[0]), binding.filmographyText1, binding.filmographyMovieCard1);
                            if(movies.length>1) {

                                setMoviePreview(new Movie(movies[1]), binding.filmographyText2, binding.filmographyMovieCard2);
                                if(movies.length>2){

                                    setMoviePreview(new Movie(movies[2]), binding.filmographyText3, binding.filmographyMovieCard3);
                                    if(movies.length>3){

                                        setMoviePreview(new Movie(movies[3]), binding.filmographyText4, binding.filmographyMovieCard4);
                                        if(movies.length>4){

                                            setMoviePreview(new Movie(movies[4]), binding.filmographyText5, binding.filmographyMovieCard5);
                                            if(movies.length>5){

                                                setMoviePreview(new Movie(movies[5]), binding.filmographyText6, binding.filmographyMovieCard6);
                                                if(movies.length>6){

                                                    setMoviePreview(new Movie(movies[6]), binding.filmographyText7, binding.filmographyMovieCard7);
                                                    if(movies.length>7){

                                                        setMoviePreview(new Movie(movies[7]), binding.filmographyText8, binding.filmographyMovieCard8);
                                                        if(movies.length>8){

                                                            setMoviePreview(new Movie(movies[8]), binding.filmographyText9, binding.filmographyMovieCard9);
                                                            if(movies.length>9){

                                                                setMoviePreview(new Movie(movies[9]), binding.filmographyText10, binding.filmographyMovieCard10);
                                                                if(movies.length>10){
                                                                    setMoviePreview(new Movie(movies[10]), binding.filmographyText11, binding.filmographyMovieCard11);

                                                                    if(movies.length>11){
                                                                        setMoviePreview(new Movie(movies[11]), binding.filmographyText12, binding.filmographyMovieCard12);

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                };

                mViewModel.getPeopleCredits(value,getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_credits);
            }


        };

        mViewModel.getPeopleDetails(value, getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_details);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PeopleCardViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();

                return true;
            default:return false;
        }
    }



    private void setMoviePreview (Movie movie, TextView textView, ImageButton imageButton){
        if(movie.getPoster_path()!=null){
            Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getPoster_path()).into(imageButton);
        }else {
            imageButton.setImageResource(R.drawable.no_image_avaiable);
        }
        if(movie.getTitle().length() > Constants.MAX_LENGHT)
            textView.setText(movie.getTitle().substring(0,Constants.MAX_LENGHT-1)+" ...");
        else
            textView.setText(movie.getTitle());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(PeopleCardFragmentDirections.actionNavigationPeopleCardToNavigationMovieCard(movie.getId()));
            }
        });
    }
    
    private void hideImage(int i){
        
    }
}
