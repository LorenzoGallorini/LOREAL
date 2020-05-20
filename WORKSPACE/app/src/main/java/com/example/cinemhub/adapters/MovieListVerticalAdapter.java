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
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListVerticalAdapter extends RecyclerView.Adapter<MovieListVerticalAdapter.MovieListViewHolder> {

    public interface OnItemClickListener{
        void OnItemClick(Movie movie);
    }

    private List<Movie> movies;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;


    public static class MovieListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageButton photo;
        public MovieListViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.TitleTextViewItem);
            photo = view.findViewById(R.id.ImageButtonItem);
        }

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

    public MovieListVerticalAdapter(Context context, List<Movie> movieList, OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.movies = movieList;
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.movie_item,parent,false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        holder.bind(movies.get(position), this.onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
