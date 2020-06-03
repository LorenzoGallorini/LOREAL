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

public class PeopleListVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int MOVIE_VIEW_TYPE=0;
    private static final int LOADING_VIEW_TYPE=1;

    public interface OnItemClickListener {
        void OnItemClick(People people);
    }

    private List<People> people;
    private LayoutInflater layoutInflater;
    private PeopleListVerticalAdapter.OnItemClickListener onItemClickListener;

    public PeopleListVerticalAdapter(Context context, List<People> peopleList, PeopleListVerticalAdapter.OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.people = peopleList;
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MOVIE_VIEW_TYPE){
            View view = this.layoutInflater.inflate(R.layout.movie_item,parent,false);
            return new PeopleListVerticalAdapter.PeopleListViewHolder(view);
        }
        else {
            View view = this.layoutInflater.inflate(R.layout.loading_item,parent,false);
            return new PeopleListVerticalAdapter.LoadingListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PeopleListVerticalAdapter.PeopleListViewHolder){
            ((PeopleListVerticalAdapter.PeopleListViewHolder) holder).bind(people.get(position), this.onItemClickListener);
        }
        else if(holder instanceof MovieListVerticalAdapter.LoadingListViewHolder){
            ((PeopleListVerticalAdapter.LoadingListViewHolder) holder).progressBarLoadingMovies.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if(people !=null){
            return people.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(people.get(position) == null){
            return LOADING_VIEW_TYPE;
        } else {
            return MOVIE_VIEW_TYPE;
        }
    }

    public void setData(List<People> people){
        if(people!=null){
            this.people =people;
            notifyDataSetChanged();
        }
    }

    public static class PeopleListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageButton photo;

        public PeopleListViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.TitleTextViewItem);
            photo = view.findViewById(R.id.ImageButtonItem);
        }

        public void bind(People people, PeopleListVerticalAdapter.OnItemClickListener onItemClickListener){
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

    public static class LoadingListViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBarLoadingMovies;

        public LoadingListViewHolder(View view){
            super(view);
            progressBarLoadingMovies = view.findViewById(R.id.progressBarLoadingMovie);
        }
    }
}
