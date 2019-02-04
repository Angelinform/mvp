package com.angelinform.moviesfeed.http;

import com.angelinform.moviesfeed.http.apimodel.OmdbApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesExtraInfoApiService {
    @GET("/")
    Observable<OmdbApi> getExtraInfoMovie(@Query("t") String title);
}
