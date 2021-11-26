package com.example.eventmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateActivity extends AppCompatActivity implements View.OnClickListener {

    Button date, time;
    ImageButton backToEvents, eventNamePlace;
    int day, month, year, hour, minute;
    EditText dateET, timeET;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        date = findViewById(R.id.dateButton);
        time = findViewById(R.id.timeButton);
        dateET = findViewById(R.id.dateTV);
        timeET = findViewById(R.id.timeTV);
        backToEvents = findViewById(R.id.backToEventsButton);
        eventNamePlace = findViewById(R.id.eventNamePlaceButton);

        date.setOnClickListener(this);
        time.setOnClickListener(this);
        backToEvents.setOnClickListener(this);
        eventNamePlace.setOnClickListener(this);
    }
    // Añadimos las funciones de calendario y hora en date y time pickers que se muestran en dialogos al pulsar el botón
    @Override
    public void onClick(View v) {
        if (v == date) {

            final Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateET.setText((twoDigits(dayOfMonth) + "/" + (twoDigits(month + 1)) + "/" + twoDigits(year)));
                }

            }, day, month, year);
            datePickerDialog.show();
        }
        if (v == time) {
            final Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeET.setText(twoDigits(hourOfDay) + ":" + twoDigits(minute));
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }
        if (v == backToEvents) {
            finish();
        }
        if (v == eventNamePlace) {

            intent = null;

            intent = new Intent(this, PlaceActivity.class);

            intent.putExtra("date", dateET.getText().toString());
            intent.putExtra("time", timeET.getText().toString());
            startActivity(intent);
            finish();
        }
    }
    //Función para añadir 2 dígitos a los time y date pickers
    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

}