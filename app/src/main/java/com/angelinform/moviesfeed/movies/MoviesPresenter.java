package com.angelinform.moviesfeed.movies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenter implements MoviesMPV.Presenter {

    private MoviesMPV.View view;
    private MoviesMPV.Model model;

    private Disposable subscription = null;

    public MoviesPresenter(MoviesMPV.Model model) {
        this.model = model;
    }

    @Override
    public void loadData() {
        subscription = model.result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ViewModel>() {
                    @Override
                    public void onNext(ViewModel viewModel) {
                        if (view != null){
                            view.updateData(viewModel);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (view != null){
                            view.showSnackBar("Error al descargar las películas");
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (view != null){
                            view.showSnackBar("Información descargada con éxito");
                        }
                    }
                });
    }

    @Override
    public void rxJavaUnsuscribe() {
        if (subscription != null && !subscription.isDisposed()){
            subscription.dispose();
        }
    }

    @Override
    public void setView(MoviesMPV.View view) {
        this.view = view;
    }
}
