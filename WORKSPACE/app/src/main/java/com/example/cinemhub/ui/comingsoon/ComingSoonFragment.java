package com.example.cinemhub.ui.comingsoon;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentComingSoonBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class ComingSoonFragment extends Fragment {

    private ComingSoonViewModel mViewModel;
    private final String TAG = "ComingSoonFragment";
    private FragmentComingSoonBinding binding;
    private MovieListVerticalAdapter movieListVerticalAdapter;

    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleItemCount;
    private int threshold=1;

    int comingSoonRVSpanCount=3;

    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(ComingSoonViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComingSoonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_coming_soon));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_coming_soon);

        return view;
    }


    private List<Movie> getMovieList(String language, boolean checkAdult, String region){
        Resource<List<Movie>> movieListResult=mViewModel.getMovieComingSoon(language, checkAdult, region).getValue();
        if(movieListResult != null){
            return movieListResult.getData();
        }
        return null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),comingSoonRVSpanCount);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);



        movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), getMovieList(getString(R.string.API_LANGUAGE), checkAdult, region), new MovieListVerticalAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Movie movie) {
                Navigation.findNavController(getView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationMovieCard(movie.getId()));
            }
        });
        binding.ComingSoonRecyclerView.setAdapter(movieListVerticalAdapter);



        binding.ComingSoonRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                visibleItemCount = layoutManager.getChildCount();

                if((totalItemCount <= (lastVisibleItem + threshold) && dy>0 && !mViewModel.isLoading())
                                &&
                                mViewModel.getMovieLiveData().getValue() != null
                                &&
                                mViewModel.getCurrentResults()!=mViewModel.getMovieLiveData().getValue().getTotalResult()
                ){
                    Resource<List<Movie>> movieListResource=new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = mViewModel.getMovieLiveData();

                    if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){
                        mViewModel.setLoading(true);

                        List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                        currentMovieList.add(null);
                        movieListResource.setData(currentMovieList);
                        movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                        movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                        movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());

                        movieListResource.setLoading(true);
                        movieListMutableLiveData.postValue(movieListResource);

                        int page=mViewModel.getPage() + 1;
                        mViewModel.setPage(page);

                        mViewModel.getMoreMovieComingSoon(getString(R.string.API_LANGUAGE), checkAdult, region);
                    }
                }



            }
        });

        final Observer<Resource<List<Movie>>> observer_coming_soon=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);

                movieListVerticalAdapter.setData(movies.getData());

                if(!movies.isLoading()){
                    mViewModel.setLoading(false);
                    mViewModel.setCurrentResults(movies.getData().size());
                }
            }
        };

        mViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), checkAdult, region).observe(getViewLifecycleOwner(), observer_coming_soon);
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
                Navigation.findNavController(getView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }





}
