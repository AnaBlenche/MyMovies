package com.example.android.mymovies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ana on 24.01.2017.
 */

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {

    private Context context;
    private ArrayList<Movie> movieList;

    public MasonryAdapter(Context c){
        this.context = c;
    }

    public MasonryAdapter(Context c, ArrayList<Movie> movies){
        movieList = movies;
        this.context = c;
    }


    @Override
    public MasonryAdapter.MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list,parent,false);

        final MasonryView masonryView = new MasonryView(layoutView);

        layoutView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                int position = masonryView.getAdapterPosition();
                Intent detail = new Intent(context,MovieDetailActivity.class);
                detail.putExtra(MovieDetailActivity.EXTRA_MOVIE,movieList.get(position));
                context.startActivity(detail);
            }
        });
        return masonryView;
    }

    @Override
    public void onBindViewHolder(MasonryAdapter.MasonryView holder, int position) {
        Movie m = movieList.get(position);
        Picasso.with(context).load(m.getPoster()).noPlaceholder().into(holder.imgView);

    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = new ArrayList<>();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MasonryView extends RecyclerView.ViewHolder {
        ImageView imgView;

        public MasonryView(View itemView){
            super(itemView);
            imgView = (ImageView)itemView.findViewById(R.id.movie_image);
        }
    }
}
