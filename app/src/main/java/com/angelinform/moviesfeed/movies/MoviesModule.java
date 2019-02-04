package com.angelinform.moviesfeed.movies;

import com.angelinform.moviesfeed.http.MoviesApiService;
import com.angelinform.moviesfeed.http.MoviesExtraInfoApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    @Provides
    public MoviesMPV.Presenter provideMoviesPresenter(MoviesMPV.Model model){
        return new MoviesPresenter(model);
    }

    @Provides
    public MoviesMPV.Model provideMoviesModel(Repository repository){
        return new MoviesModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideMovieRepository(MoviesApiService moviesApiService, MoviesExtraInfoApiService moviesExtraInfoApiService){
        return new MoviesRepository(moviesApiService, moviesExtraInfoApiService);
    }
}
