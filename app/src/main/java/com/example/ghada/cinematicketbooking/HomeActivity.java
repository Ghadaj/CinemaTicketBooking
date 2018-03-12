package com.example.ghada.cinematicketbooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    private ImageView pic;
    private Integer pics[] = {R.drawable.action, R.drawable.comedy, R.drawable.romance,
            R.drawable.adventure, R.drawable.animation, R.drawable.crime, R.drawable.drama, R.drawable.fantasy, R.drawable.horror,
            R.drawable.mystery, R.drawable.sci_fi, R.drawable.thriller};
    private int genreId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /* Genres id:
                 * action: 28
                  * comedy: 35
                  * romance: 10749
                  * adventure: 12
                  * animation: 16
                  * crime: 80
                  * drama: 18
                  * fantasy: 14
                  * horror: 27
                  * mystery: 9648
                  * sci-fi: 878
                  * thriller: 53
                  */

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (i == 0) {
                    genreId = 28;
                } else if (i == 1) {
                    genreId = 35;
                } else if (i == 2) {
                    genreId = 10749;
                } else if (i == 3) {
                    genreId = 12;
                } else if (i == 4) {
                    genreId = 16;
                } else if (i == 5) {
                    genreId = 80;
                } else if (i == 6) {
                    genreId = 18;
                } else if (i == 7) {
                    genreId = 14;
                } else if (i == 8) {
                    genreId = 27;
                } else if (i == 9) {
                    genreId = 9648;
                } else if (i == 10) {
                    genreId = 878;
                } else if (i == 11) {
                    genreId = 53;
                }
                editor.putInt("genreId", genreId);
                editor.commit();
                startActivity(new Intent(HomeActivity.this, MoviesList.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();


            }
        });
    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            pic = new ImageView(context);
            pic.setImageResource(pics[position]);
            pic.setScaleType(ImageView.ScaleType.FIT_XY);
            pic.setPadding(6, 6, 6, 6);
            return pic;
        }

    }
}
