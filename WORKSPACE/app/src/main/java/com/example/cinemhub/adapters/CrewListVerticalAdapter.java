package com.example.cinemhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.models.People;

import java.util.List;
/**
 * public class CrewListVerticalAdapter
 * Questa classe appartiene al package adapter
 * la classe estende RecyclerView.Adapter<CrewListVerticalAdapter.CrewListViewHolder>
 * questa classe serve per visualizzare nel MovieCard la recycler view relativa alla crew
 */
public class CrewListVerticalAdapter extends RecyclerView.Adapter<CrewListVerticalAdapter.CrewListViewHolder>{

    /**
     * public interface OnItemClickListener
     * questa interfaccia serve per intercettare il click dell'utente sul nome della persona
     */
    public interface OnItemClickListener{
        /**
         * OnItemClick
         * @param people
         */
        void OnItemClick(People people);
    }

    private List<People> people;  /**< people list memorizza la list delle persone appartenenti alla crew*/
    private LayoutInflater layoutInflater; /**< Variabile per memorizzare il layout inflater*/
    private CrewListVerticalAdapter.OnItemClickListener onItemClickListener; /**< variabile per salvare l'item click listener*/

    /**
     * public static class CrewListViewHolder extends RecyclerView.ViewHolder
     * questa classe statica serve per creare gli elementi della RecyclerView
     */
    public static class CrewListViewHolder extends RecyclerView.ViewHolder{
        TextView title; /**< variabile per salvare il nome della persona */
        TextView subTitle; /**< variabile per salvare il ruolo della persona nel film */
        /**
         * costruttorre del CrewListViewHolder
         * @param view
         */
        public CrewListViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.itemTitleCrew);
            subTitle = view.findViewById(R.id.itemSubtitleCrew);
        }

        /**
         * public void bind
         * classe bind per settare le variabili dell'oggetto contenuto nel viewholder
         * @param people
         * @param onItemClickListener
         */
        public void bind(People people, CrewListVerticalAdapter.OnItemClickListener onItemClickListener){
            title.setText(people.getName());

            subTitle.setText(people.getRole());

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(people);
                }
            });

        }
    }

    /**
     * costruttore della classe CrewListVerticalAdapter
     * @param context
     * @param peopleList
     * @param onItemClickListener
     */
    public CrewListVerticalAdapter(Context context, List<People> peopleList, CrewListVerticalAdapter.OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.people = peopleList;
        this.onItemClickListener=onItemClickListener;
    }

    /**
     * onCreateViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CrewListVerticalAdapter.CrewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.crew_item,parent,false);
        return new CrewListVerticalAdapter.CrewListViewHolder(view);
    }

    /***
     * onBindViewHolder
     * classe bind per settare le variabili dell'oggetto contenuto nel viewholder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CrewListVerticalAdapter.CrewListViewHolder holder, int position) {
        holder.bind(people.get(position), this.onItemClickListener);

    }

    /**
     * override del metodo getItemCount che restituisce in numero di persone contenute nella recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        return people.size();
    }
}
