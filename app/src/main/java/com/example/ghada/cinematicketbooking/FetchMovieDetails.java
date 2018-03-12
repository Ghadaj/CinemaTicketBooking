package com.example.ghada.cinematicketbooking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

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

public class FetchMovieDetails {

    final static int READ_TIME_OUT = 10000;
    final static int CONNECT_TIME_OUT = 15000;

    public static Movie fetchMovieDetails(String requestUrl) {

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

    private static Movie extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        Movie selectedMovie = null;
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            String mTitle = "N/A", mRating = "N/A", posterLink = "", mStoryLine = "N/A", mTrailerUrl = "";
            ArrayList<String> mGenre = new ArrayList<>();
            ArrayList<String> mLang = new ArrayList<>();
            int mLength = 0;
            Bitmap posterLarge = null;

            if (baseJsonResponse.has("original_title")) {
                mTitle = baseJsonResponse.getString("original_title");

            }

            if (baseJsonResponse.has("runtime")) {
                mLength = baseJsonResponse.getInt("runtime");
            }
            if (baseJsonResponse.has("overview")) {
                mStoryLine = baseJsonResponse.getString("overview");
            }

            if (baseJsonResponse.has("poster_path")) {
                posterLink = baseJsonResponse.getString("poster_path");
            }

            if (baseJsonResponse.has("videos")) {
                JSONObject videos = baseJsonResponse.getJSONObject("videos");
                if (videos.has("results")) {
                    JSONArray resultsArray = videos.getJSONArray("results");
                    JSONObject firstVideo = resultsArray.getJSONObject(0);
                    if (firstVideo.has("key")) {
                        mTrailerUrl = "https://www.youtube.com/watch?v=" + firstVideo.getString("key");
                    }

                }
            }

            if (baseJsonResponse.has("genres")) {
                JSONArray genreArray = baseJsonResponse.getJSONArray("genres");

                for (int x = 0; x < genreArray.length(); x++) {
                    JSONObject genre = genreArray.getJSONObject(x);
                    if (genre.has("name")) {
                        mGenre.add(genre.getString("name"));
                    }
                }
            }
            if (baseJsonResponse.has("spoken_languages")) {
                JSONArray langArray = baseJsonResponse.getJSONArray("spoken_languages");
                for (int x = 0; x < langArray.length(); x++) {
                    JSONObject lang = langArray.getJSONObject(x);
                    if (lang.has("name")) {
                        mLang.add(lang.getString("name"));
                    }

                }
            }


            try {
                URL url = new URL("https://image.tmdb.org/t/p/w500" + posterLink);
                posterLarge = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            selectedMovie = new Movie(mTitle, mRating, mGenre, mStoryLine, mLang, mTrailerUrl, posterLarge, mLength);


        } catch (JSONException e)

        {
            System.out.print("ex");
        }
        return selectedMovie;


    }
}
