package com.example.ghada.cinematicketbooking;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

import static com.example.ghada.cinematicketbooking.FetchGenreXMovies.fetchMovieData;

/**
 * Created by ghada on 20/12/2017.
 */

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return fetchMovieData(mUrl);
    }
}
