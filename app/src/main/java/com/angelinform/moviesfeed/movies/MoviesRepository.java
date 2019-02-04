package com.angelinform.moviesfeed.movies;

import com.angelinform.moviesfeed.http.MoviesApiService;
import com.angelinform.moviesfeed.http.MoviesExtraInfoApiService;
import com.angelinform.moviesfeed.http.apimodel.OmdbApi;
import com.angelinform.moviesfeed.http.apimodel.Result;
import com.angelinform.moviesfeed.http.apimodel.TopMovieRated;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MoviesRepository implements Repository {
    private static final long CACHE_LIFETIME = 20 * 1000;

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApiService moviesExtraInfoApiService;

    private List<String> countries;
    private List<Result> results;
    private long lasttimestamp;

    public MoviesRepository(MoviesApiService moviesApiService, MoviesExtraInfoApiService moviesExtraInfoApiService) {
        this.moviesApiService = moviesApiService;
        this.moviesExtraInfoApiService = moviesExtraInfoApiService;

        this.lasttimestamp = System.currentTimeMillis();

        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public boolean isUpdated() {
        return (System.currentTimeMillis() - lasttimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork());
    }

    @Override
    public Observable<Result> getResultFromNetwork() {
        Observable<TopMovieRated> topMovieRatedObservable = moviesApiService.getTopMoviesRated(1)
                .concatWith(moviesApiService.getTopMoviesRated(2))
                .concatWith(moviesApiService.getTopMoviesRated(3));

        return topMovieRatedObservable
                .concatMap(new Function<TopMovieRated, Observable<Result>>() {
                    @Override
                    public Observable<Result> apply(TopMovieRated topMovieRated) {
                        return Observable.fromIterable(topMovieRated.getResults());
                    }
                }).doOnNext(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        results.add(result);
                    }
                });
    }

    @Override
    public Observable<Result> getResultFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(results);
        } else {
            lasttimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork());
    }

    @Override
    public Observable<String> getCountryFromNetwork() {
        return getResultFromNetwork().concatMap(new Function<Result, Observable<OmdbApi>>() {
            @Override
            public Observable<OmdbApi> apply(Result result) {
                return moviesExtraInfoApiService.getExtraInfoMovie(result.getTitle());
            }
        }).concatMap(new Function<OmdbApi, Observable<String>>() {
            @Override
            public Observable<String> apply(OmdbApi omdbApi) throws Exception {
                if (omdbApi == null || omdbApi.getCountry() == null) {
                    return Observable.just("País desconocido");
                } else {
                    return Observable.just(omdbApi.getCountry());
                }
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String country) throws Exception {
                countries.add(country);
            }
        });
    }

    @Override
    public Observable<String> getCountryFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(countries);
        } else {
            lasttimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }
}
