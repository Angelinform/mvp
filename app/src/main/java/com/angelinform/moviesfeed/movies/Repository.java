package com.angelinform.moviesfeed.movies;

import com.angelinform.moviesfeed.http.apimodel.Result;

import io.reactivex.Observable;

public interface Repository {
    Observable<Result> getResultData();
    Observable<Result> getResultFromNetwork();
    Observable<Result> getResultFromCache();

    Observable<String> getCountryData();
    Observable<String> getCountryFromNetwork();
    Observable<String> getCountryFromCache();
}
