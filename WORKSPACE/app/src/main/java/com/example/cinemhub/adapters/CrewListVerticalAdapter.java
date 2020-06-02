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

public class CrewListVerticalAdapter extends RecyclerView.Adapter<CrewListVerticalAdapter.CrewListViewHolder>{

    public interface OnItemClickListener{
        void OnItemClick(People people);
    }

    private List<People> people;
    private LayoutInflater layoutInflater;
    private CrewListVerticalAdapter.OnItemClickListener onItemClickListener;


    public static class CrewListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView subTitle;
        public CrewListViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.itemTitleCrew);
            subTitle = view.findViewById(R.id.itemSubtitleCrew);
        }

        public void bind(People people, CrewListVerticalAdapter.OnItemClickListener onItemClickListener){
            title.setText(people.getName());

            subTitle.setText(people.getRole());

        }
    }

    public CrewListVerticalAdapter(Context context, List<People> peopleList, CrewListVerticalAdapter.OnItemClickListener onItemClickListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.people = peopleList;
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public CrewListVerticalAdapter.CrewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.crew_item,parent,false);
        return new CrewListVerticalAdapter.CrewListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewListVerticalAdapter.CrewListViewHolder holder, int position) {
        holder.bind(people.get(position), this.onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
