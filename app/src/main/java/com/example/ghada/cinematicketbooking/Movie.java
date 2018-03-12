package com.example.ghada.cinematicketbooking;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by ghada on 18/12/2017.
 */

public class Movie {
    private String mTitle, mRating, mStoryLine, mTrailerUrl;
    ArrayList<String>  mLang ,mGenre;
    private int mLength, mId;
    private Bitmap mPosterLarge, mPosterThumb;

    public Movie(String mTitle, String mRating, int mId, Bitmap mPosterThumb) {
        this.mTitle = mTitle;
        this.mRating = mRating;
        this.mId = mId;
        this.mPosterThumb = mPosterThumb;
    }

    public Movie(String mTitle, String mRating, ArrayList<String> mGenre, String mStoryLine, ArrayList<String> mLang, String mTrailerUrl, Bitmap mPosterLarge, int mLength) {
        this.mTitle = mTitle;
        this.mRating = mRating;
        this.mGenre = mGenre;
        this.mStoryLine = mStoryLine;
        this.mLang = mLang;
        this.mTrailerUrl = mTrailerUrl;
        this.mPosterLarge = mPosterLarge;
        this.mLength = mLength;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmRating() {
        return mRating;
    }

    public ArrayList<String> getmGenre() {
        return mGenre;
    }

    public String getmStoryLine() {
        return mStoryLine;
    }

    public ArrayList<String> getmLang() {
        return mLang;
    }

    public String getmTrailerUrl() {
        return mTrailerUrl;
    }

    public int getmLength() {
        return mLength;
    }

    public int getmId() {
        return mId;
    }

    public Bitmap getmPosterLarge() {
        return mPosterLarge;
    }

    public Bitmap getmPosterThumb() {
        return mPosterThumb;
    }
}
