package com.example.ghada.cinematicketbooking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.ghada.cinematicketbooking.MoviesList.mEmptyStateTextView;

/**
 * Created by ghada on 20/12/2017.
 */

public final class FetchGenreXMovies {
    final static int READ_TIME_OUT = 10000;
    final static int CONNECT_TIME_OUT = 15000;

    public static ArrayList<Movie> fetchMovieData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpResquest(url);
        } catch (IOException e) {
        }
        return extractFeatureFromJson(jsonResponse);

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    private static String makeHttpResquest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                System.out.print("200");
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Movie> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<Movie> arrayList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            if (baseJsonResponse.has("results")) {
                String mTitle = "N/A";
                String mRating = "N/A";
                String posterLink = "";
                Bitmap posterThump = null;
                int mId = 0;
                JSONArray itemsArray = baseJsonResponse.getJSONArray("results");
                for (int x = 0; x < itemsArray.length(); x++) {
                    JSONObject movie = itemsArray.getJSONObject(x);
                    if (movie.has("original_title")) {
                        mTitle = movie.getString("original_title");
                    }
                    if (movie.has("vote_average")) {
                        mRating = movie.getString("vote_average");
                    }
                    if (movie.has("backdrop_path")) {
                        posterLink = movie.getString("backdrop_path");
                    }
                    if (movie.has("id")) {
                        mId = movie.getInt("id");
                    }

                    try {
                        URL url = new URL("https://image.tmdb.org/t/p/w500" + posterLink);
                        posterThump = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    arrayList.add(new Movie(mTitle, mRating, mId, posterThump));
                }
            }
            else {
                mEmptyStateTextView.setText("No movies found"); // move it to string file
            }

        } catch (JSONException e) {
        }
        return arrayList;


    }
}
