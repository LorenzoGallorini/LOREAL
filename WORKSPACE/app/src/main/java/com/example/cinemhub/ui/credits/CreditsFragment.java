package com.example.cinemhub.ui.credits;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentCreditsBinding;

import org.jetbrains.annotations.NotNull;

/**
 * classe CreditsFragment
 * classe utilizzata per visualizzare i Credits dell'app ed è raggiungibile passando per le impostazioni
 */
public class CreditsFragment extends Fragment {

    private FragmentCreditsBinding binding;/**<Questa variabile verrà utilizzata nel momento in cui verrà implementata la pagina*/
    private String TAG="CreditsFragment";

    /**
     * costruttore di CreditsFragment
     * @return Fragment di tipo CreditsFragment
     */
    public static CreditsFragment newInstance() {
        return new CreditsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //andiamo a prendere il FragmentCreditsBinding mettendolo in binding per poter utilizzare gli oggetti presenti nel Fragment
        binding = FragmentCreditsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //settiamo il titolo del CreditsFragment nell'ActionBar della MainActivity ed il colore
        setHasOptionsMenu(true);
        ((MainActivity) requireActivity()).setActionBarTitle(getString(R.string.credits_text));
        ((MainActivity) requireActivity()).menuColorSettings(R.id.navigation_settings);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //disabilitiamo e rendiamo invisibili i pulsanti del search e del settings
        menu.findItem(R.id.search).setEnabled(false);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.settings).setEnabled(false);
        menu.findItem(R.id.settings).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            //se premuta la backArrow simuliamo il comportamento del tasto indietro caricando il
            // Fragment precedente
            requireActivity().onBackPressed();
            return true;
        }
        return false;
    }
}
