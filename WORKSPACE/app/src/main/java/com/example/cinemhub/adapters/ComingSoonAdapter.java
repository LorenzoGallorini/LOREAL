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

public class ComingSoonAdapter extends RecyclerView.Adapter<ComingSoonAdapter.ComingSoonViewHolder> {
    private List<Movie> movies;
    private LayoutInflater layoutInflater;
    public static class ComingSoonViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageButton photo;
        public ComingSoonViewHolder(View view){
            super(view);

            title = view.findViewById(R.id.TitleTextViewItem);
            photo = view.findViewById(R.id.ImageButtonItem);
        }
    }
    public ComingSoonAdapter(Context context,List<Movie> movieList)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.movies = movieList;
    }

    @NonNull
    @Override
    public ComingSoonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.movie_item,parent,false);
        return new ComingSoonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComingSoonViewHolder holder, int position) {
        if(movies.get(position).getTitle().length() > Constants.MAX_LENGHT)
            holder.title.setText(movies.get(position).getTitle().substring(0,Constants.MAX_LENGHT-1)+" ...");
        else
            holder.title.setText(movies.get(position).getTitle());
        if(movies.get(position).getPoster_path()!=null && !movies.get(position).getPoster_path().equals("")){
            Picasso.get().load(Constants.IMAGE_BASE_URL + movies.get(position).getPoster_path()).into(holder.photo);
        }else {
            holder.photo.setImageResource(R.drawable.no_image_avaiable);
        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
