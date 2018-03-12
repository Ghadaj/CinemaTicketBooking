package com.example.ghada.cinematicketbooking;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Loader;
import android.view.MenuItem;
import android.view.View;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MovieDetails extends AppCompatActivity implements LoaderCallbacks<Movie> {

    private int movieId;
    private static final int Movie_Loader_ID = 1;
    static TextView mEmptyStateTextView;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    LoaderManager loaderManager;
    SharedPreferences sharedPreferences;
    ImageView posterLarge;
    TextView mTitle, mRating, mGenre, mStory, mLang, mLength;
    private String genres = "", langs = "";

    private Button trailerBtn;
    private Button bookingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mTitle = (TextView) findViewById(R.id.mTitle);
        mRating = (TextView) findViewById(R.id.mRating);
        mGenre = (TextView) findViewById(R.id.mGenre);
        mStory = (TextView) findViewById(R.id.mStory);
        mLang = (TextView) findViewById(R.id.mLang);
        mLength = (TextView) findViewById(R.id.mLength);
        posterLarge = (ImageView) findViewById(R.id.poster_large);

        trailerBtn = (Button) findViewById(R.id.trailer_btn);
        //trailerBtn.getBackground().setAlpha(65);

        bookingBtn = (Button) findViewById(R.id.book_btn);
       // bookingBtn.getBackground().setAlpha(65);


        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        movieId = sharedPreferences.getInt("movieId", 0);

        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(Movie_Loader_ID, null, this);
        } else if (networkInfo == null || !networkInfo.isConnected()) {
            mEmptyStateTextView.setText("There is no internet connection");// move it to string file
            mEmptyStateTextView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public Loader<Movie> onCreateLoader(int i, Bundle bundle) {

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            // this api retrieve list of movies for a selected genre
            String url_api = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=6199eaa139d082849117065ecc6c4d68&append_to_response=videos";
            return new MovieDetailsLoader(this, url_api);
        } else {
            mEmptyStateTextView.setText("There is no internet connection");// move it to string file
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, final Movie movie) {

        if (movie != null) {
            mTitle.setText(movie.getmTitle());
            mRating.setText(movie.getmRating());
            mStory.setText(movie.getmStoryLine());
            mLength.setText(movie.getmLength() + " minute");
            posterLarge.setImageBitmap(movie.getmPosterLarge());


            ArrayList<String> arrayGenres = movie.getmGenre();
            for (int x = 0; x < arrayGenres.size(); x++) {
                if ((x + 1) == arrayGenres.size()) {
                    genres += arrayGenres.get(x) + ".";
                    break;
                }
                genres += arrayGenres.get(x) + ", ";
            }
            mGenre.setText(genres);
            ArrayList<String> arrayLangs = movie.getmLang();
            for (int x = 0; x < arrayLangs.size(); x++) {
                if ((x + 1) == arrayLangs.size()) {
                    langs += arrayLangs.get(x) + ".";
                    break;
                }
                langs += arrayLangs.get(x) + ", ";
            }
            mLang.setText(langs);
            trailerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(movie.getmTrailerUrl())));
                }
            });

            bookingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(MovieDetails.this,BookTicket.class));

                }


            });

        }
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                startActivity(new Intent(MovieDetails.this,MoviesList.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

