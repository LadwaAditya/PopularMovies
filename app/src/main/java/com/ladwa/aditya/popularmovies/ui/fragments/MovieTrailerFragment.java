package com.ladwa.aditya.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ladwa.aditya.popularmovies.R;
import com.ladwa.aditya.popularmovies.data.api.MovieApi;
import com.ladwa.aditya.popularmovies.data.api.ServiceGenerator;
import com.ladwa.aditya.popularmovies.data.model.MovieVideoListModel;
import com.ladwa.aditya.popularmovies.ui.adapter.RecyclerViewMoviesAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aditya on 08-Feb-16.
 */
public class MovieTrailerFragment extends Fragment {

    private static final String LOG_TAG = MovieTrailerFragment.class.getSimpleName();
    private MovieApi movieApi;
    private Subscription videoSubscription;

    private ArrayList<MovieVideoListModel> mVideoList;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewMoviesAdapter moviesAdapter;


    @Bind(R.id.recycler_view_movie_trailer)
    RecyclerView mRecyclerView;


    public MovieTrailerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_trailer, container, false);
        ButterKnife.bind(this, view);

        movieApi = ServiceGenerator.createService(MovieApi.class);
        videoSubscription = movieApi.getMovieTrailerRx("281957", getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<MovieVideoListModel>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG_TAG, "Completed loading movie videos");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieVideoListModel movieVideoListModel) {
                        for (int i = 0; i < movieVideoListModel.getResults().size(); i++) {

                        }
                    }
                });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        videoSubscription.unsubscribe();
    }
}