package com.angelinform.moviesfeed.movies;

import io.reactivex.Observable;

public interface MoviesMPV {

    interface View{
        void updateData(ViewModel viewModel);
        void showSnackBar(String s);
    }

    interface Presenter{
        void loadData();
        void rxJavaUnsuscribe();
        void setView(MoviesMPV.View view);
    }

    interface Model{
        Observable<ViewModel> result();
    }

}
