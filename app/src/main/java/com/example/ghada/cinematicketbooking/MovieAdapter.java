package com.example.ghada.cinematicketbooking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, ArrayList<Movie> movie) {
        super(context, 0, movie);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Movie currentMovie = getItem(position);
        holder.movieTitle.setText(currentMovie.getmTitle());
        holder.movieRating.setText(currentMovie.getmRating()+"");
        holder.poster.setImageBitmap(currentMovie.getmPosterThumb());
        holder.poster.setVisibility(View.VISIBLE);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_rating)
        TextView movieRating;
        @BindView(R.id.poster_thump)
        ImageView poster;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}