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
import com.example.cinemhub.models.People;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CreditsListHorizontalAdapter extends RecyclerView.Adapter<CreditsListHorizontalAdapter.CreditsListViewHolder> {

    public interface OnItemClickListener{
        void OnItemClick(People people);
    }

    private List<People> people;
    private LayoutInflater layoutInflater;
    private CreditsListHorizontalAdapter.OnItemClickListener onItemClickListener;


    public static class CreditsListViewHolder extends RecyclerView.ViewHolder{
        ImageButton photo;
        TextView title;
        TextView subTitle;
        public CreditsListViewHolder(View view){
            super(view);
            photo = view.findViewById(R.id.imageCredits);
            title = view.findViewById(R.id.itemTitleCredits);
            subTitle = view.findViewById(R.id.itemSubtitleCredits);
        }

        public void bind(People people, CreditsListHorizontalAdapter.OnItemClickListener onItemClickListener){
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

    public CreditsListHorizontalAdapter(Context context, List<People> peopleList, CreditsListHorizontalAdapter.OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.people = peopleList;
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public CreditsListHorizontalAdapter.CreditsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.credits_item,parent,false);
        return new CreditsListHorizontalAdapter.CreditsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsListHorizontalAdapter.CreditsListViewHolder holder, int position) {
        holder.bind(people.get(position), this.onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
