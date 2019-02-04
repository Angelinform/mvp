package com.angelinform.moviesfeed.root;

import android.app.Application;

import com.angelinform.moviesfeed.http.MovieTitleApiModule;
import com.angelinform.moviesfeed.http.MoviesExtraInfoModule;
import com.angelinform.moviesfeed.movies.MoviesModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .moviesModule(new MoviesModule())
                .movieTitleApiModule(new MovieTitleApiModule())
                .moviesExtraInfoModule(new MoviesExtraInfoModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
