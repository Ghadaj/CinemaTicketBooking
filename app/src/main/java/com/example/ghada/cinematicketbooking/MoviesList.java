package com.example.ghada.cinematicketbooking;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MoviesList extends AppCompatActivity implements LoaderCallbacks<ArrayList<Movie>> {
    private int genreId;
    private static final int Movie_Loader_ID = 1;
    private MovieAdapter movieAdapter;
    private GridView movieGridView;
    static TextView mEmptyStateTextView;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    LoaderManager loaderManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        movieGridView = (GridView) findViewById(R.id.movie_grid_view);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        movieGridView.setEmptyView(mEmptyStateTextView);

        movieAdapter = new MovieAdapter(MoviesList.this, new ArrayList<Movie>());

        movieGridView.setAdapter(movieAdapter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int movieId = movieAdapter.getItem(i).getmId();
                editor.putInt("movieId", movieId);
                editor.commit();
                startActivity(new Intent(MoviesList.this, MovieDetails.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        genreId = sharedPreferences.getInt("genreId", 0);
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(Movie_Loader_ID, null, this);
        } else if (networkInfo == null || !networkInfo.isConnected()) {
            movieGridView.setVisibility(View.GONE);
            mEmptyStateTextView.setText("There is no internet connection");// move it to string file
            mEmptyStateTextView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {

            // this api retrieve list of movies for a selected genre
            String url_api = "https://api.themoviedb.org/3/genre/" + genreId + "/movies?api_key=6199eaa139d082849117065ecc6c4d68&language=en-US&include_adult=true&sort_by=created_at.asc";
            return new MovieLoader(this, url_api);
        } else {
            mEmptyStateTextView.setText("There is no internet connection");// move it to string file
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        movieAdapter.clear();
        if (movies != null && !movies.isEmpty()) {

            movieAdapter.addAll(movies);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        movieAdapter.clear();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
