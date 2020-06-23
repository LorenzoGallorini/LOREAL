package com.example.cinemhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.models.People;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * public class CreditsListAdapter
 * Questa classe appartiene al package adapters
 * la classe estende RecyclerView.Adapter<CreditsListAdapter.CreditsListViewHolder>
 * questa classe serve per visualizzare nel moviecard la recycler view relativa al cast
 */
public class CreditsListAdapter extends RecyclerView.Adapter<CreditsListAdapter.CreditsListViewHolder> {

    /**
     * public interface OnItemClickListener
     * questa interfaccia serve per intercettare il click dell'utente sull'immagine dell'attore
     */
    public interface OnItemClickListener{
        /**
         * OnItemClick
         * @param people
         */
        void OnItemClick(People people);
    }


    private List<People> people; /**< people list memorizza la list del cast*/
    private LayoutInflater layoutInflater; /**< Variabile per memorizzare il layout inflater*/
    private CreditsListAdapter.OnItemClickListener onItemClickListener; /**< variabile per salvare l'item click listener*/

    /**
     * public static class CreditsListViewHolder extends RecyclerView.ViewHolder
     * questa classe statica serve per creare gli elementi della RecyclerView
     */
    public static class CreditsListViewHolder extends RecyclerView.ViewHolder{
        ImageButton photo; /**< variabile per inserire la photo*/
        TextView title; /**< variabile per inserire il nome della persona*/
        TextView subTitle; /**< variabile per inserire il ruole della persona nel film*/
        /**
         * public CreditsListViewHolder
         * costruttore della classe CreditsListViewHolder
         * @param view
         */
        public CreditsListViewHolder(View view){
            super(view);
            photo = view.findViewById(R.id.imageCredits);
            title = view.findViewById(R.id.itemTitleCredits);
            subTitle = view.findViewById(R.id.itemSubtitleCredits);
        }

        /**
         * public void bind
         * classe bind per settare le variabili dell'oggetto contenuto nel viewholder
         * @param people
         * @param onItemClickListener
         */
        public void bind(People people, CreditsListAdapter.OnItemClickListener onItemClickListener){
            if(people.getName().length() > Constants.MAX_LENGHT)
                title.setText(people.getName().substring(0,Constants.MAX_LENGHT-1)+" ...");
            else
                title.setText(people.getName());

            if(people.getRole().length() > Constants.MAX_LENGHT)
                subTitle.setText(people.getRole().substring(0,Constants.MAX_LENGHT-1)+" ...");
            else
                subTitle.setText(people.getRole());

            if(people.getProfile_path()!=null && !people.getProfile_path().equals("")){
                Picasso.get().load(Constants.IMAGE_BASE_URL + people.getProfile_path()).into(photo);
            }else {
                photo.setImageResource(R.drawable.no_phot_available_grey);
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
     * public CreditsListAdapter
     * costruttore della classe CreditsListAdapter
     * @param context
     * @param peopleList
     * @param onItemClickListener
     */
    public CreditsListAdapter(Context context, List<People> peopleList, CreditsListAdapter.OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.people = peopleList;
        this.onItemClickListener=onItemClickListener;
    }

    /**
     * onCreateViewHolder
     * override del metodo onCreateViewHolder per settare la recyclerView
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CreditsListAdapter.CreditsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.credits_item,parent,false);
        return new CreditsListAdapter.CreditsListViewHolder(view);
    }

    /**
     * onBindViewHolder
     * override del metodo onBindViewHolder oer settare l'oggetto della recycler view in posizione postion
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CreditsListAdapter.CreditsListViewHolder holder, int position) {
        holder.bind(people.get(position), this.onItemClickListener);
    }

    /**
     * getItemCount
     * override del metodo getItemCount che restituisce in numero di persone contenute nella recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        return people.size();
    }
}
