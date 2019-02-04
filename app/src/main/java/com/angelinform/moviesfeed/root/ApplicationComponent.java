package com.angelinform.moviesfeed.root;

import com.angelinform.moviesfeed.MainActivity;
import com.angelinform.moviesfeed.http.MovieTitleApiModule;
import com.angelinform.moviesfeed.http.MoviesExtraInfoModule;
import com.angelinform.moviesfeed.movies.MoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MovieTitleApiModule.class, MoviesExtraInfoModule.class, MoviesModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);
}
