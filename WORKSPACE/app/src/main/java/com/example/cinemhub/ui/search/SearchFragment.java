package com.example.cinemhub.ui.search;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private String TAG="SearchFragment";
    private FragmentSearchBinding binding;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_search));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_settings);



        //setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> categories =  new ArrayList<String>();
        categories.add(getString(R.string.Categories));
        categories.add("Horror");
        categories.add("Fantasy");
        categories.add("Thriller");
        List<String> years =  new ArrayList<String>();
        years.add(getString(R.string.release_year));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = year+1;i>=1920;i--)
            years.add(Integer.toString(i));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.customizedspinnerelement,categories);
        ArrayAdapter<String> adapterYears = new ArrayAdapter<String>(getContext(),R.layout.customizedspinnerelement,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.CategoriesSpinner.setAdapter(adapter);
        binding.CategoriesSpinner.getBackground().setColorFilter(getResources().getColor(R.color.titleWhite), PorterDuff.Mode.SRC_ATOP);
        binding.YearSpinner.setAdapter(adapterYears);
        binding.YearSpinner.getBackground().setColorFilter(getResources().getColor(R.color.titleWhite), PorterDuff.Mode.SRC_ATOP);


        binding.SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;


                if(!binding.SearchBox.getText().toString().isEmpty()) {
                    //TODO goto search2
                }else if(binding.SearchBox.getText().toString().isEmpty()){

                    toast = Toast.makeText(getContext(), R.string.searchError, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }



            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.search).setEnabled(false);
        menu.findItem(R.id.search).setVisible(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(SearchFragmentDirections.actionNavigationSearchToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }

}
