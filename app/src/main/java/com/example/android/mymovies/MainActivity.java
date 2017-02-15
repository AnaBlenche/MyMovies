package com.example.android.mymovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    private ProgressBar progressBar;
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static final String endpoint = "http://api.themoviedb.org/3";
    private static String api_key;

    MasonryAdapter mAdapter;

    public boolean  onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        api_key = this.getString(R.string.api_key);

        myRecyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
        myRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mAdapter = new MasonryAdapter(this,movieList);
        myRecyclerView.setAdapter(mAdapter);

        if(isOnline()) getMovies();
        else Toast.makeText(this,"Please check internet connection!",Toast.LENGTH_LONG).show();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.most_popular:
                getPopularMovies();
                return true;
            case R.id.top_rated:
                getTopRatedMovies();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }


    private void getMovies(){
        progressBar.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(endpoint)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",api_key);

                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getNowPlayingMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {

                try {
                    Throwable t = error.getCause();
                    if(t instanceof UnknownHostException)
                        Toast.makeText(getApplicationContext(), "Please check internet connection!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }catch (Throwable t){
                    t.printStackTrace();
                }

            }
        });

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void getTopRatedMovies(){
        progressBar.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(endpoint)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",api_key);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    Throwable t = error.getCause();
                    if(t instanceof UnknownHostException)
                        Toast.makeText(getApplicationContext(), "Please check internet connection!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void getPopularMovies(){
        progressBar.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(endpoint)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",api_key);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Throwable t = error.getCause();
                if(t instanceof IOException){
                    Toast.makeText(getApplicationContext(),"Internet connection is off", Toast.LENGTH_LONG);
                }
            }


        });

        progressBar.setVisibility(View.INVISIBLE);
    }

    public Boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }








}
