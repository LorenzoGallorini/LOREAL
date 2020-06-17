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
import com.example.cinemhub.adapters.MovieListAdapter;
import com.example.cinemhub.databinding.FragmentComingSoonBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;

import java.util.List;
/**
 * classe ComingSoonFragment
 * classe utilizzata per visualizzare i Film che usciranno Prossimamente ed è raggiungibile cliccando
 * nel relativo pulsante "vedi tutti" presente nell'HomeFragment
 */
public class ComingSoonFragment extends Fragment {

    private ComingSoonViewModel comingSoonViewModel;/**< attributo per la gestione del ComingSoonViewModel*/
    private final String TAG = "ComingSoonFragment";
    private FragmentComingSoonBinding binding;/**< attributo per poter gestire gli oggetti all'interno del Fragment*/
    private MovieListAdapter movieListAdapter;/**< attributo per contenere l'Adapter per la RecyclerView*/
    private final int comingSoonRVSpanCount = 3;/**< attributo per il numero massimo delle colonne della RecyclerView*/


    private int totalItemCount;/**< attributo per il numero totale di oggetti nella RecyclerView*/
    private int lastVisibleItem;/**< attributo per l'ultimo oggetto visibile nella RecyclerView*/
    private int threshold=1;/**< attributo per tenere conto dell'elemento aggiuntivo grafico della RecyclerView*/

    /**
     * costruttore di ComingSoonFragment
     * @return Fragment di tipo ComingSoonFragment
     */
    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //istanziamo l'oggetto ComingSoonViewModel
        comingSoonViewModel =new ViewModelProvider(requireActivity()).get(ComingSoonViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //andiamo a prendere il FragmentComingSoonBinding mettendolo in binding per poter utilizzare gli oggetti presenti nel Fragment
        binding = FragmentComingSoonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //settiamo il titolo del ComingSoonFragment nell'ActionBar della MainActivity ed il colore
        setHasOptionsMenu(true);
        ((MainActivity) requireActivity()).setActionBarTitle(getString(R.string.title_coming_soon));
        ((MainActivity) requireActivity()).menuColorSettings(R.id.navigation_coming_soon);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //creiamo il LayoutManager per gestire la RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), comingSoonRVSpanCount);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManager);

        //recuperiamo i valori checkAdult e region dalla SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);

        //creiamo l'Adapter per gestire la RecyclerView
        movieListAdapter = new MovieListAdapter(getActivity(), getMovieList(getString(R.string.API_LANGUAGE), checkAdult, region), movie -> Navigation.findNavController(requireView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationMovieCard(movie.getId())));
        binding.ComingSoonRecyclerView.setAdapter(movieListAdapter);

        //andiamo ad intercettare l'evento di scroll della RecyclerView
        binding.ComingSoonRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //aggiorniamo i valori del totalItemCount e del lastVisibleItem
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                //controlliamo se dobbiamo aggiungere elementi alla RecyclerView
                if((totalItemCount <= (lastVisibleItem + threshold) && dy>0 && !comingSoonViewModel.isLoading())
                                &&
                                comingSoonViewModel.getMovieLiveData().getValue() != null
                                &&
                                comingSoonViewModel.getCurrentResults()!= comingSoonViewModel.getMovieLiveData().getValue().getTotalResult()
                ){
                    //creiamo un oggetto di tipo Resource per aggiungere nuovi elementi alla RecyclerView
                    Resource<List<Movie>> movieListResource=new Resource<>();
                    MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = comingSoonViewModel.getMovieLiveData();

                    //controlliamo che la chiamata sia andata a buon fine e che ci siano altri dati da visualizzare
                    if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){

                        //aggiorniamo i valori della Resource
                        comingSoonViewModel.setLoading(true);
                        List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                        currentMovieList.add(null);
                        movieListResource.setData(currentMovieList);
                        movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                        movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                        movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());
                        movieListResource.setLoading(true);
                        movieListMutableLiveData.postValue(movieListResource);

                        //incrementiamo il valore di page per visualizzare nuove pagine nei risultati
                        int page= comingSoonViewModel.getPage() +1;
                        comingSoonViewModel.setPage(page);

                        //chiediamo altri risultati tramite il ViewModel
                        comingSoonViewModel.getMoreMovieComingSoon(getString(R.string.API_LANGUAGE), checkAdult, region);
                    }
                }
            }
        });

        //creiamo l'observer per cattura la modifica della lista dei film
        final Observer<Resource<List<Movie>>> observer_coming_soon= movies -> {
            Log.d(TAG, "lista tmdb comingsoon"+movies);

            //inseriamo nell'Adapter i dati
            movieListAdapter.setData(movies.getData());

            //controlliamo che movies non stia ancora caricando i risultati
            if(!movies.isLoading()){
                comingSoonViewModel.setLoading(false);
                comingSoonViewModel.setCurrentResults(movies.getData().size());
            }
        };
        //se la region è diversa da null effuttiamo una chiamata al DB
        if(region!=null){
            comingSoonViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), checkAdult, region).observe(getViewLifecycleOwner(), observer_coming_soon);
        }
        else {
            Log.d(TAG, "ERROR:region cannot be null");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //controlliamo tramite l'ID quale icona è stata selezionata e indirizziamo l'utente nel Fragment corretto
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(requireView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(requireView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSettings());
                return true;
            case android.R.id.home:
                requireActivity().onBackPressed();
                return true;
            default:
                return false;
        }
    }

    /**
     * metodo per la richiesta della List di film
     * @param language stringa che contiene la lingua del dispositivo
     * @param checkAdult booleano per il Parental Control
     * @param region stringa che contiene il paese selezionato
     * @return List di Movie contenente i risultati della chiamata
     */
    private List<Movie> getMovieList(String language, boolean checkAdult, String region){
        Resource<List<Movie>> movieListResult= comingSoonViewModel.getMovieComingSoon(language, checkAdult, region).getValue();
        if(movieListResult != null){
            return movieListResult.getData();
        }
        return null;
    }
}
