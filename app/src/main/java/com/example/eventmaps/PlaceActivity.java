package com.example.eventmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


public class PlaceActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eventName, eventPlace;
    ImageButton backToDate, addEvent;
    Intent intent;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        eventName = findViewById(R.id.eventNameET);
        eventPlace = findViewById(R.id.eventPlaceET);

        backToDate = findViewById(R.id.backToDateButton);
        addEvent = findViewById(R.id.addEventButton);

        backToDate.setOnClickListener(this);
        addEvent.setOnClickListener(this);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");

    }

    @Override
    public void onClick(View v) {

        if (v == backToDate) {
            intent = new Intent(this, DateActivity.class);
        }
        if (v == addEvent) {

            intent = new Intent(this, MapsActivity.class);
            intent.putExtra("date", date);
            intent.putExtra("time", time);
            intent.putExtra("event", eventName.getText().toString());
            intent.putExtra("site", eventPlace.getText().toString());

        }
        startActivity(intent);
        finish();
    }
}