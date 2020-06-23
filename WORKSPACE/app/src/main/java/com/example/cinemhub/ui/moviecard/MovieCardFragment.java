package com.example.cinemhub.ui.moviecard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.CreditsListAdapter;
import com.example.cinemhub.adapters.CrewListAdapter;
import com.example.cinemhub.databinding.FragmentMovieCardBinding;
import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.models.VideoApiTmdbResponse;
import com.example.cinemhub.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieCardFragment extends Fragment {
    private YouTubePlayer mYoutubePlayer;
    private MovieCardViewModel mViewModel;
    private FragmentMovieCardBinding binding;
    private final String TAG="MovieCard";
    int crewRVSpanCount=2;

    public static MovieCardFragment newInstance() {
        return new MovieCardFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_movie_card));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_movie_card);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(),crewRVSpanCount);

        binding.CreditsRecyclerView.setLayoutManager(layoutManager);
        binding.CreditsCrewRecyclerView.setLayoutManager(layoutManager2);



        mViewModel=new ViewModelProvider(this).get(MovieCardViewModel.class);

        final Observer<Resource<Movie>> observer_details=new Observer<Resource<Movie>>() {
            @Override
            public void onChanged(Resource<Movie> movieResource) {

                if(movieResource!=null && movieResource.getData()!= null){
                Movie movie=movieResource.getData();
                Log.d(TAG, "movie tmdb details"+movie);


                if(movie.getPoster_path()!=null&&!movie.getPoster_path().equals("")){
                    Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getPoster_path()).into(binding.filmImage);
                }else{
                    binding.filmImage.setImageResource(R.drawable.no_phot_available_grey);
                }

                binding.filmTextTitle.setText(movie.getTitle());
                binding.descriptionValue.setText(movie.getDescription());
                binding.RatingValue.setText(Double.toString(movie.getVote_average()));



                if(movie.isAdult())
                    binding.AdultValue.setText((getString(R.string.Adult)));
                else
                    binding.AdultValue.setVisibility(View.INVISIBLE);

                binding.RuntimeValue.setText(Integer.toString(movie.getRuntime())+"'");
                binding.ReleaseDateValue.setText(movie.getReleaseDateFORMATTED());
                binding.RevenueValue.setText(movie.getRevenueFORMATTED()+" €");

                binding.GenresValue.setText(movie.getGenresTostring());


                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
                Set<String> preferiti;
                try {

                    preferiti=sharedPreferences.getStringSet(Constants.FAVORITE_SHARED_PREF_NAME+getString(R.string.API_LANGUAGE),null);
                    if (preferiti == null){
                        preferiti = new HashSet<String>();
                    }
                }
                catch(Exception e)
                {
                    preferiti = new HashSet<String>();
                }

                Object[] favoriteArray=preferiti.toArray();
                List<Integer> intList=new ArrayList<Integer>();
                for (int i=0;i<favoriteArray.length;i++){
                    intList.add(Integer.parseInt(favoriteArray[i].toString().split(Constants.SEPARATOR)[0]));
                }

                if (intList.contains(movie.getId())) {
                    binding.MovieCardFavouriteButton.setImageResource(R.drawable.startminum_yellow);
                }
                else
                {
                    binding.MovieCardFavouriteButton.setImageResource(R.drawable.startplus_yellow);
                }
                binding.MovieCardFavouriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Set<String> preferiti;
                        Toast toast;
                        try {
                            preferiti=sharedPreferences.getStringSet(Constants.FAVORITE_SHARED_PREF_NAME+getString(R.string.API_LANGUAGE),null);
                            if (preferiti == null)
                                preferiti = new HashSet<String>();
                        }
                        catch(Exception e)
                        {
                            preferiti = new HashSet<String>();
                        }

                        Object[] favoriteArray=preferiti.toArray();
                        List<Integer> intList=new ArrayList<Integer>();
                        for (int i=0;i<favoriteArray.length;i++){
                            intList.add(Integer.parseInt(favoriteArray[i].toString().split(Constants.SEPARATOR)[0]));
                        }


                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (intList.contains(movie.getId())) {
                            int position=intList.indexOf(movie.getId());

                            Set<String> in = new HashSet<String>(preferiti);
                            in.remove(favoriteArray[position].toString());
                            editor.remove(Constants.FAVORITE_SHARED_PREF_NAME+getString(R.string.API_LANGUAGE));
                            editor.putStringSet(Constants.FAVORITE_SHARED_PREF_NAME+getString(R.string.API_LANGUAGE), in);
                            toast = Toast.makeText(getContext(), "Rimosso " + movie.getTitle() + " dai tuoi preferiti", Toast.LENGTH_SHORT);

                            binding.MovieCardFavouriteButton.setImageResource(R.drawable.startplus_yellow);
                        }
                        else {
                            Set<String> in = new HashSet<String>(preferiti);
                            in.add(Integer.toString(movie.getId())+Constants.SEPARATOR+movie.getTitle()+Constants.SEPARATOR+movie.getPoster_path());
                            editor.putStringSet(Constants.FAVORITE_SHARED_PREF_NAME+getString(R.string.API_LANGUAGE), in);
                            toast = Toast.makeText(getContext(), "Aggiunto " + movie.getTitle() + " ai tuoi preferiti", Toast.LENGTH_SHORT);
                            binding.MovieCardFavouriteButton.setImageResource(R.drawable.startminum_yellow);
                        }
                        editor.commit();
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        binding.MovieCardFavouriteButton.setClickable(false);


                        new CountDownTimer(2000, 2000) { //Set Timer for 5 seconds
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                binding.MovieCardFavouriteButton.setClickable(true);
                            }
                        }.start();
                    }

                });
                }else{
                    if(movieResource!= null && movieResource.getStatusMessage()!=null) {
                        Log.d(TAG, "ERROR CODE: " + movieResource.getStatusCode() + " ERROR MESSAGE: " + movieResource.getStatusMessage());
                    }
                    Toast toast;
                    toast = Toast.makeText(getContext(), getString(R.string.error_message_movie) , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        };


        final Observer<Resource<MovieCreditsApiTmdbResponse>> observer_credits = new Observer<Resource<MovieCreditsApiTmdbResponse>>() {
            @Override
            public void onChanged(Resource<MovieCreditsApiTmdbResponse> movieCreditsApiTmdbResponseResource) {
                MovieCreditsApiTmdbResponse movieCreditsApiTmdbResponse=movieCreditsApiTmdbResponseResource.getData();

                CreditsListAdapter creditsListAdapter = new CreditsListAdapter(getActivity(),
                        People.toList(movieCreditsApiTmdbResponse.getCast()), new CreditsListAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(People people) {
                        Navigation.findNavController(view).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationPeopleCard(people.getId()));

                    }
                });
                CrewListAdapter crewListAdapter = new CrewListAdapter(getActivity(),
                        People.toList(movieCreditsApiTmdbResponse.getCrew()), new CrewListAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(People people) {
                        Navigation.findNavController(view).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationPeopleCard(people.getId()));
                    }
                });
                binding.CreditsCrewRecyclerView.setAdapter(crewListAdapter);
                binding.CreditsRecyclerView.setAdapter(creditsListAdapter);
            }
        };


        final Observer<Resource<GetVideosApiTmdbResponse>> observer_videos = new Observer<Resource<GetVideosApiTmdbResponse>>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(Resource<GetVideosApiTmdbResponse> getVideosApiTmdbResponseResource) {
                GetVideosApiTmdbResponse getVideosApiTmdbResponse=getVideosApiTmdbResponseResource.getData();
                VideoApiTmdbResponse[] results = getVideosApiTmdbResponse.getResults();
                int i = 0;
                boolean found= false;
                VideoApiTmdbResponse result=null;
                while (i<results.length && !found)
                {
                    if(results[i].getSite().equals(Constants.PLATFORM_VIDEO))
                    {
                        result=results[i];
                        if(results[i].getType().equals(Constants.TYPE_VIDEO))
                            found=true;
                    }
                    i++;
                }
                if(result != null)
                {
                    YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

                    setRetainInstance(true);
                    VideoApiTmdbResponse finalResult = result;
                    youTubePlayerFragment.initialize(Constants.API_KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            if (!b) {
                                mYoutubePlayer = youTubePlayer;
                                mYoutubePlayer.setShowFullscreenButton(true);
                                mYoutubePlayer.cueVideo(finalResult.getKey());
                                mYoutubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            Toast toast;
                            toast = Toast.makeText(getContext(), R.string.error_message_trailer , Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_youtube, (Fragment) youTubePlayerFragment);
                    transaction.commit();
                }
                else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), R.string.error_message_trailer , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();                }
            }
        };


        int value=MovieCardFragmentArgs.fromBundle(getArguments()).getMovieId();


        mViewModel.getMovieDetails(value,getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_details);
        mViewModel.getMovieCredits(value).observe(getViewLifecycleOwner(), observer_credits);
        mViewModel.getVideos(value,getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_videos);
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
}
