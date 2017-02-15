package com.example.android.mymovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ana on 06.02.2017.
 */

public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/now_playing")
    void getNowPlayingMovies(Callback<Movie.MovieResult> cb);
}
