package com.example.android.mymovies;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Ana on 06.02.2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private Movie mMovie;
    ImageView poster;
    ImageView backdrop;
    TextView rating;
    TextView title;
    TextView original_title;
    TextView description;
    TextView date;
    ScrollView scrollView;

    public static final String EXTRA_MOVIE = "movie";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(getIntent().hasExtra(EXTRA_MOVIE)){
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            scrollView = (ScrollView)findViewById(R.id.detail_layout);
            scrollView.setVisibility(View.VISIBLE);

            title = (TextView)findViewById(R.id.movie_title);
            title.setText(mMovie.getTitle());

            original_title = (TextView)findViewById(R.id.movie_original_title);
            original_title.setText(mMovie.getTitle());

            rating = (TextView) findViewById(R.id.movie_rating);
            rating.setText(mMovie.getRating()+"/10");

            date = (TextView)findViewById(R.id.movie_date);
            date.setText(mMovie.getRelease_date());

            poster = (ImageView)findViewById(R.id.movie_poster);
            Picasso.with(this.getApplicationContext()).load(mMovie.getPoster()).into(poster);

            backdrop = (ImageView)findViewById(R.id.movie_backdrop);
            Picasso.with(this.getApplicationContext()).load(mMovie.getBackdrop()).into(backdrop);

            description = (TextView)findViewById(R.id.movie_description);
            description.setText(mMovie.getDescription());

        }
        else{
            throw  new IllegalArgumentException("No data passed");
        }

    }


}
