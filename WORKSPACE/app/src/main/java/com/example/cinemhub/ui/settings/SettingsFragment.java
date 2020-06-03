package com.example.cinemhub.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentSettingsBinding;
import com.example.cinemhub.ui.moviecard.MovieCardFragmentDirections;
import com.example.cinemhub.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private FragmentSettingsBinding binding;
    private String TAG="SettingsFragment";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);

        binding.darkThemeSwitch.setChecked(true);
        binding.darkThemeSwitch.setClickable(false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean isAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        binding.parentalControlSwitch.setChecked(isAdult);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> region=new ArrayList<String>();
        region.add(getString(R.string.italy));
        region.add(getString(R.string.united_states));
        region.add(getString(R.string.united_kingdom));

        ArrayAdapter<String> adapterRegion = new ArrayAdapter<String>(getContext(),R.layout.customizedspinnerelement,region);

        adapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.RegionSpinner.getBackground().setColorFilter(getResources().getColor(R.color.titleWhite), PorterDuff.Mode.SRC_ATOP);
        binding.RegionSpinner.setAdapter(adapterRegion);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        String regionSelected=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);
        if(regionSelected!=null){
            int position;
            if(regionSelected.equals(Constants.REGION_ITALY)){
                position=0;
            }else if(regionSelected.equals(Constants.REGION_USA)){
                position=1;
            }else{
                position=2;
            }
            binding.RegionSpinner.setSelection(position);
        }



        binding.RegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if ((parent.getItemAtPosition(position).toString()).equals(getString(R.string.italy))) {
                    editor.putString(Constants.REGION_SHARED_PREF_NAME, Constants.REGION_ITALY);
                }else if(parent.getItemAtPosition(position).toString().equals(getString(R.string.united_states))){
                    editor.putString(Constants.REGION_SHARED_PREF_NAME, Constants.REGION_USA);
                }else{
                    editor.putString(Constants.REGION_SHARED_PREF_NAME, Constants.REGION_GB);
                }
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_settings));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_settings);
        binding.faqText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: FaqClick");
                Navigation.findNavController(view).navigate(SettingsFragmentDirections.actionNavigationSettingsToNavigationFaq());

            }
        });

        binding.creditsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: CreditsClick");
                Navigation.findNavController(view).navigate(SettingsFragmentDirections.actionNavigationSettingsToNavigationCredits());
            }
        });

        binding.parentalControlSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Constants.ADULT_SHARED_PREF_NAME, binding.parentalControlSwitch.isChecked());
                editor.commit();

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }

}
