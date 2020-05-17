package com.example.cinemhub.ui.peoplecard;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentPeopleCardBinding;
import com.example.cinemhub.models.People;
import com.example.cinemhub.ui.search.SearchFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;
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

        final Observer<People> observer_details = new Observer<People>() {
            @Override
            public void onChanged(People people) {
                Log.d(TAG, "people tmdb details"+people);
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + people.getProfile_path()).into(binding.peopleImage);
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
                if(people.getDeath_date()!=null){
                    binding.dayOfDeathValue.setText(people.getDeath_date());
                }
                else{
                    binding.dayOfDeathValue.setVisibility(View.INVISIBLE);
                }
                binding.placeOfBirthValue.setText(people.getPlace_of_birth());

                binding.peopleDescription.setText(people.getBiography());

                if(people.getKnown_for_department()=="Acting") {
                    ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_actor_card));
                }else{
                    ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_director_card));
                }
            }


        };





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
        if(id==R.id.search){
            Log.d(TAG, "onClick: SearchClick");
            fragmentTransactionMethod(new SearchFragment());
            return true;
        }else if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");
            fragmentTransactionMethod(new SettingsFragment());
            return true;
        }
        return false;
    }

    private void fragmentTransactionMethod (Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
