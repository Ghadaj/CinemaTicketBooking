package com.example.ghada.cinematicketbooking;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

import static com.example.ghada.cinematicketbooking.FetchMovieDetails.fetchMovieDetails;

/**
 * Created by ghada on 22/12/2017.
 */

public class MovieDetailsLoader extends AsyncTaskLoader<Movie> {

        private String mUrl;

        public MovieDetailsLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public Movie loadInBackground() {
            if (mUrl == null) {
                return null;
            }
            return fetchMovieDetails(mUrl);
        }
    }


