package com.angelinform.moviesfeed.http;

import com.angelinform.moviesfeed.http.apimodel.TopMovieRated;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {
    @GET("/3/movie/top_rated")
    Observable<TopMovieRated> getTopMoviesRated(@Query("page")Integer page);
}
