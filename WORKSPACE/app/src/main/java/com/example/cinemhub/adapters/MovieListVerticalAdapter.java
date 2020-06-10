package com.example.cinemhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * public class MovieListVerticalAdapter
 * Questa classe appartiene al package adapter
 * la classe estende RecyclerView.Adapter<RecyclerView.ViewHolder>, la classe che estende è più generica possibile per poter permettere il lazy loading
 * questa classe serve per visualizzare nel NowPlaying, nel TopRated, nel ComingSoon e nella sezione Movie del SearchResult la recycler view popolata dai vari film
 */
public class MovieListVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MOVIE_VIEW_TYPE=0; /**< il valore di MOVIE_VIEW_TYPE è un codice univoco che serve per riconoscerlo*/
    private static final int LOADING_VIEW_TYPE=1;/**< il valore di LOADING_VIEW_TYPE è un codice univoco che serve per riconoscerlo*/

    /**
     * public interface OnItemClickListener
     * questa interfaccia serve per intercettare il click dell'utente sulla locandina del film
     */
    public interface OnItemClickListener{
        /**
         * OnItemClick
         * @param movie
         */
        void OnItemClick(Movie movie);
    }

    private List<Movie> movies; /**< movies memorizza la lista dei film*/
    private LayoutInflater layoutInflater; /**< Variabile per memorizzare il layout inflater*/
    private OnItemClickListener onItemClickListener; /**< variabile per salvare l'item click listener*/

    /**
     * costruttore della classe MovieListVerticalAdapter
     * @param context
     * @param movieList
     * @param onItemClickListener
     */
    public MovieListVerticalAdapter(Context context, List<Movie> movieList, OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.movies = movieList;
        this.onItemClickListener=onItemClickListener;
    }
    /**
     * onCreateViewHolder
     * @param parent
     * @param viewType
     * @return //TODO dire cosa return?
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MOVIE_VIEW_TYPE){
            View view = this.layoutInflater.inflate(R.layout.movie_item,parent,false);
            return new MovieListViewHolder(view);
        }
        else {
            View view = this.layoutInflater.inflate(R.layout.loading_item,parent,false);
            return new LoadingListViewHolder(view);
        }
    }
    /***
     * onBindViewHolder
     * classe bind per settare le variabili dell'oggetto contenuto nel viewholder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MovieListViewHolder){
            ((MovieListViewHolder) holder).bind(movies.get(position), this.onItemClickListener);
        }
        else if(holder instanceof LoadingListViewHolder){
            ((LoadingListViewHolder) holder).progressBarLoadingMovies.setIndeterminate(true);
        }
    }
    /**
     * override del metodo getItemCount che restituisce il numero di oggetti di tipo Movie contenuti nella recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        if(movies!=null){
            return movies.size();
        }
        return 0;
    }

    /**
     * metodo per riconoscere se l'oggetto è un film o un loading //TODO trovare nome più adatto
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(movies.get(position) == null){
            return LOADING_VIEW_TYPE;
        } else {
            return MOVIE_VIEW_TYPE;
        }
    }

    /**
     * metodo che prende in input una lista di Movie e la setta nella variabile movies
     * @param movies
     */
    public void setData(List<Movie> movies){
        if(movies!=null){
            this.movies=movies;
            notifyDataSetChanged();
        }
    }

    /**
     * public static class MovieListViewHolder extends RecyclerView.ViewHolder
     * questa classe statica serve per creare gli elementi della RecyclerView
     */
    public static class MovieListViewHolder extends RecyclerView.ViewHolder{
        TextView title;/**< variabile per salvare il nome del film */
        ImageButton photo; /**< variabile per salvare la locandina del film */
        /**
         * costruttorre del MovieListViewHolder
         * @param view
         */
        public MovieListViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.TitleTextViewItem);
            photo = view.findViewById(R.id.ImageButtonItem);
        }

        /**
         * public void bind
         * classe bind per settare le variabili dell'oggetto contenuto nel viewholder
         * @param movie
         * @param onItemClickListener
         */
        public void bind(Movie movie, OnItemClickListener onItemClickListener){
            if(movie.getTitle().length() > Constants.MAX_LENGHT)
                title.setText(movie.getTitle().substring(0,Constants.MAX_LENGHT-1)+" ...");
            else
                title.setText(movie.getTitle());
            if(movie.getPoster_path()!=null && !movie.getPoster_path().equals("")){
                Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getPoster_path()).into(photo);
            }else {
                photo.setImageResource(R.drawable.no_image_avaiable);
            }

            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(movie);
                }
            });
        }
    }

    /**
     * public static class LoadingListViewHolder extends RecyclerView.ViewHolder
     * questa classe statica serve per creare gli elementi della RecyclerView
     */
    public static class LoadingListViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBarLoadingMovies;/**< variabile per mostrare il loading */

        public LoadingListViewHolder(View view){
            super(view);
            progressBarLoadingMovies = view.findViewById(R.id.progressBarLoadingMovie);
        }
    }

}
