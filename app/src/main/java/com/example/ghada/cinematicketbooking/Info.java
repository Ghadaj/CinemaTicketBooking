package com.example.ghada.cinematicketbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ghada.cinematicketbooking.MainHelpers.GMailSender;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

public class Info extends AppCompatActivity {
    EditText fname, lname, email;
    Button confirm;
    String message;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
     //   getActionBar().setDisplayHomeAsUpEnabled(true);
        fname = (EditText) findViewById(R.id.first_name);
        lname = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        confirm = (Button) findViewById(R.id.confirm_btn2);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(fname.getText().toString()) && !TextUtils.isEmpty(lname.getText().toString())) {
                    Random rand = new Random();
                    message = "Your Booking Details\nMovie Title: " + sp.getString("movie_title", null) + "\nCity: " + sp.getString("city", null)
                            + "\n Date: " + sp.getString("date", null) + "\nTime: " + sp.getString("time", null) + "\nTicket Number: " + (rand.nextInt(99999) +10000);


                    Thread sender = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                GMailSender sender = new GMailSender("cinema.ticket.booking.project@gmail.com", "se443se416");
                                sender.sendMail("Cinema Ticket Booking App",
                                        message,
                                        "cinema.ticket.booking.project@gmail.com",
                                        email.getText().toString());
                            } catch (Exception e) {
                                Log.e("mylog", "Error: " + e.getMessage());
                            }
                        }
                    });
                    sender.start();
                    Timer timer1 = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Info.this, HomeActivity.class));
                        }
                    };
                    timer1.schedule(timerTask, 10000);
                    setContentView(R.layout.confirmation);
                } else

                {
                    Toast.makeText(Info.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
