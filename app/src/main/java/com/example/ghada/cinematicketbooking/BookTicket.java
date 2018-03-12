package com.example.ghada.cinematicketbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BookTicket extends AppCompatActivity {
    Spinner citySpinner, dateSpinner;
    RadioButton time1, time2, time3;
    Button book;
    String selectedCity, selectedDate, selectedTime, date[] = new String[3];
    SharedPreferences sp;
    Calendar c = Calendar.getInstance();
    DateFormat format = DateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        dateSpinner = (Spinner) findViewById(R.id.date_spinner);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        c.add(Calendar.DATE, 1);
        date[0] = format.format(c.getTime());
        c.add(Calendar.DATE, 1);
        date[1] = format.format(c.getTime());
        c.add(Calendar.DATE, 1);
        date[2] = format.format(c.getTime());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, date);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);
        time1 = (RadioButton) findViewById(R.id.r1);
        time2 = (RadioButton) findViewById(R.id.r2);
        time3 = (RadioButton) findViewById(R.id.r3);

        book = (Button) findViewById(R.id.book_btn2);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time1.isChecked() || time2.isChecked() || time3.isChecked()) {
                    SharedPreferences.Editor editor = sp.edit();
                    selectedCity = citySpinner.getSelectedItem().toString();
                    selectedDate = dateSpinner.getSelectedItem().toString();

                    if (time1.isChecked()) selectedTime = time1.getText().toString();
                    if (time2.isChecked()) selectedTime = time2.getText().toString();
                    if (time3.isChecked()) selectedTime = time3.getText().toString();

                    editor.putString("city",selectedCity);
                    editor.putString("date",selectedDate);
                    editor.putString("time",selectedTime);
                    editor.commit();


                    startActivity(new Intent(BookTicket.this, Info.class));

                } else
                    Toast.makeText(BookTicket.this, "Please choose Time", Toast.LENGTH_SHORT).show();
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
