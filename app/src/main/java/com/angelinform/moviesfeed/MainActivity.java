package com.angelinform.moviesfeed;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.angelinform.moviesfeed.movies.ListAdapter;
import com.angelinform.moviesfeed.movies.MoviesMPV;
import com.angelinform.moviesfeed.movies.ViewModel;
import com.angelinform.moviesfeed.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesMPV.View {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.activity_root_view)
    ViewGroup rootView;

    @BindView(R.id.recycler_views_movies)
    RecyclerView recyclerView;

    @Inject
    MoviesMPV.Presenter presenter;

    private ListAdapter listAdapter;
    private List<ViewModel> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((App) getApplication()).getComponent().inject(this);

        resultList = new ArrayList<>();
        listAdapter = new ListAdapter(resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxJavaUnsuscribe();
        resultList.clear();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        listAdapter.notifyItemInserted(resultList.size() - 1);
        Log.d(TAG, "Información nueva: " + viewModel.getName());
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }
}
