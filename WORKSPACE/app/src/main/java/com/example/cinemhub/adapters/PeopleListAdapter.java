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
import com.example.cinemhub.models.People;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * public class PeopleListAdapter
 * Questa classe appartiene al package adapter
 * la classe estende RecyclerView.Adapter<RecyclerView.ViewHolder>, la classe che estende è più generica possibile per poter permettere il lazy loading
 * questa classe serve per visualizzare nella sezione People del SearchResult la recycler view popolata da oggetti di tipo People
 */
public class PeopleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int PEOPLE_VIEW_TYPE =0;/**< il valore di PEOPLE_VIEW_TYPE è un codice univoco che serve per riconoscerlo*/
    private static final int LOADING_VIEW_TYPE=1;/**< il valore di LOADING_VIEW_TYPE è un codice univoco che serve per riconoscerlo*/
    /**
     * public interface OnItemClickListener
     * questa interfaccia serve per intercettare il click dell'utente sulla locandina della persona
     */
    public interface OnItemClickListener {
        /**
         *
         * @param people
         */
        void OnItemClick(People people);
    }

    private List<People> people; /**< people memorizza la lista delle persone*/
    private LayoutInflater layoutInflater; /**< Variabile per memorizzare il layout inflater*/
    private OnItemClickListener onItemClickListener;/**< variabile per salvare l'item click listener*/
    /**
     * costruttore della classe MovieListAdapter
     * @param context
     * @param peopleList
     * @param onItemClickListener
     */
    public PeopleListAdapter(Context context, List<People> peopleList, OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.people = peopleList;
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
        if(viewType== PEOPLE_VIEW_TYPE){
            View view = this.layoutInflater.inflate(R.layout.movie_item,parent,false);
            return new PeopleListViewHolder(view);
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
        if(holder instanceof PeopleListAdapter.PeopleListViewHolder){
            ((PeopleListViewHolder) holder).bind(people.get(position), this.onItemClickListener);
        }
        else if(holder instanceof MovieListAdapter.LoadingListViewHolder){
            ((LoadingListViewHolder) holder).progressBarLoadingMovies.setIndeterminate(true);
        }
    }
    /**
     * override del metodo getItemCount che restituisce il numero di oggetti di tipo People contenuti nella recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        if(people !=null){
            return people.size();
        }
        return 0;
    }
    /**
     * metodo per riconoscere se l'oggetto è di tipo People o un loading //TODO trovare nome più adatto
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(people.get(position) == null){
            return LOADING_VIEW_TYPE;
        } else {
            return PEOPLE_VIEW_TYPE;
        }
    }
    /**
     * metodo che prende in input una lista di Movie e la setta nella variabile movies
     * @param people
     */
    public void setData(List<People> people){
        if(people!=null){
            this.people =people;
            notifyDataSetChanged();
        }
    }
    /**
     * public static class PeopleListViewHolder extends RecyclerView.ViewHolder
     * questa classe statica serve per creare gli elementi della RecyclerView
     */
    public static class PeopleListViewHolder extends RecyclerView.ViewHolder{
        TextView title;/**< variabile per salvare il nome della persona */
        ImageButton photo;/**< variabile per salvare la locandina della persona */
        /**
         * costruttorre del PeopleListViewHolder
         * @param view
         */
        public PeopleListViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.TitleTextViewItem);
            photo = view.findViewById(R.id.ImageButtonItem);
        }
        /**
         * public void bind
         * classe bind per settare le variabili dell'oggetto contenuto nel viewholder
         * @param people
         * @param onItemClickListener
         */
        public void bind(People people, PeopleListAdapter.OnItemClickListener onItemClickListener){
            if(people.getName().length() > Constants.MAX_LENGHT)
                title.setText(people.getName().substring(0,Constants.MAX_LENGHT-1)+" ...");
            else
                title.setText(people.getName());
            if(people.getProfile_path()!=null && !people.getProfile_path().equals("")){
                Picasso.get().load(Constants.IMAGE_BASE_URL + people.getProfile_path()).into(photo);
            }else {
                photo.setImageResource(R.drawable.no_image_avaiable);
            }

            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(people);
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
