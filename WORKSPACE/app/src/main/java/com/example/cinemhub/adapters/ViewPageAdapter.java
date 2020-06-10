package com.example.cinemhub.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * public class ViewPageAdapter
 * Questa classe appartiene al package adapter
 * la classe estende FragmentPagerAdapter
 * questa classe serve per visualizzare i due Fragment(SearchMovieResult e SearchPeopleResult) all'interno della TabView presente nel SearchResultFragment
 */
public class ViewPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList=new ArrayList<>();/**< variabile per contenere la List di Fragment da visualizzare */
    private final List<String> stringList=new ArrayList<>();/**< variabile per contenere la List di String da visualizzare */


    /**
     * costruttore di ViewPageAdapter
     * @param fm
     */
    public ViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    /**
     * Override del metodo getItemPosition
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
        super.getItemPosition(object);
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * Override del metodo getItem
     * @param position
     * @return l'oggetto in posizione position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
        }

    /**
     * Overrride del metodo getCount
     * @return la dimensione della stringList
     */
    @Override
    public int getCount() {
        return stringList.size();
    }

    /**
     * metodo che ritorna la stringa presente alla posizione position della stringList
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

    /**
     * metodo che aggiunge un Fragment alla fragmentList ed una stringa alla stringList
     * @param fragment
     * @param title
     */
    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        stringList.add(title);
    }

    /**
     * distruttore della classe ViewPageAdapter
     */
    public void clear()
    {
        fragmentList.clear();
        stringList.clear();
    }



}
