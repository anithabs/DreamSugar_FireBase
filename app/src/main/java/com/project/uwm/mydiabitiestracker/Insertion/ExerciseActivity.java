package com.project.uwm.mydiabitiestracker.Insertion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.ExerciseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.Objects.WordObject;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ExerciseActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private AutoCompleteTextView text;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> ArrayWords = new ArrayList<>();
    private int day;
    private int month;
    private int year, hour,minute;
    public static final String EA = "Activity Exercise";
    UserPreference pref;
    EditText exTime, exDate, exType, exDuration;
    String userName;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Log Exercise");
        pref = new UserPreference(this);
        setContentView(R.layout.activity_exercise);

        AddWords();

        exType = (AutoCompleteTextView) findViewById(R.id.exercise_type_value);
        exDuration= (EditText) findViewById(R.id.exercise_duration_value);

        userName = pref.getUserName();
        exType.setText(pref.getExerciseField());
        exDuration.setText(pref.getDurationField());

        Date date = new Date();
        exDate = (EditText) findViewById(R.id.exercise_date_value);
        exTime = (EditText) findViewById(R.id.exercise_start_time_value);
        /*android.text.format.DateFormat df = new android.text.format.DateFormat();*/
        /*exDate.setText(df.format("yyyy-MM-dd",date));
        exTime = (EditText) findViewById(R.id.exercise_start_time_value);
        exTime.setText(df.format("hh:mm",date));*/

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth +1;
                day = monthday;
                updateToDisplayToDay();
            }
        };
        exDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(),dateListener,calender.get(calender.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH));
                dialog.show();

            }
        });
        final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min ;
                updateDisplayToTime();
            }
        };
        exTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                TimePickerDialog dialog = new TimePickerDialog(view.getContext(),timeListener,calender.get(calender.HOUR),calender.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });
    }
    protected void onStart() {
        this.AddWords();
        super.onStart();
        Log.w(EA, "onStart()\n");
    }
    protected void onRestart() {
        this.AddWords();
        super.onRestart();
        Log.v(EA, ":onRestart()\n");
    }
    protected void onResume() {
        this.AddWords();
        super.onResume();
        Log.v(EA, ":onResume()\n");
    }
    protected void onPause() {
        super.onPause();
        dbManager.close();
        Log.v(EA, ":onPause()\n");
    }
    protected void onStop() {
        super.onStop();
        dbManager.close();
        Log.v(EA, ":onStop()\n");
    }
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
        Log.v(EA, ":onDestroy()\n");
    }
    public void AddWords() {
        dbManager = new DatabaseManager(this);
        try{
            if (dbManager.ifSourceColumnHasId("R.id.exercise_type_value")) {
                ArrayWords = dbManager.selectWordsBySource("R.id.exercise_type_value");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ArrayWords);
                text = (AutoCompleteTextView) findViewById(R.id.exercise_type_value);
                // set adapter for the auto complete field
                text.setAdapter(adapter);
                // specify the minimum type of characters before drop-down list is shown
                text.setThreshold(1);
            }
        }
        catch (NullPointerException npe){
            Toast.makeText( this, "No current words in source 'R.id.exercise_type_value'", Toast.LENGTH_LONG ).show( );
        }
        dbManager.close();
    }
    public void exerciseInsert(View v) {
        dbManager = new DatabaseManager(this);
        exType = (EditText) findViewById(R.id.exercise_type_value);
        exDuration= (EditText) findViewById(R.id.exercise_duration_value);
        exDate = (EditText) findViewById(R.id.exercise_date_value);
        exTime = (EditText) findViewById(R.id.exercise_start_time_value);
        String timeString = exTime.getText().toString();
        String dateString = exDate.getText().toString();
        String typeOfExerciseString = exType.getText().toString();
        String durationExercise = exDuration.getText().toString();
        int minutesOfExercise = 0;
        try {
            minutesOfExercise = Integer.parseInt( durationExercise );
        }
        catch( Exception e ) {
            Toast.makeText( this, "Minutes must be an integer. Duration changed to '0'.", Toast.LENGTH_LONG ).show( );
            minutesOfExercise = 0;
        }
        try{
            ExerciseReadingObject eco = new ExerciseReadingObject(
                    0,userName,
                    typeOfExerciseString,
                    minutesOfExercise,
                    dateString,
                    timeString );
            dbManager.insertExercise(eco);

            WordObject wo = new WordObject(
                    0,
                    typeOfExerciseString,
                    "R.id.exercise_type_value");
            int occurrences = dbManager.numOccurrencesByObject(wo);
            if (occurrences == 0) {
                dbManager.insertWord(wo);
            }
            Toast.makeText( this, "Details added", Toast.LENGTH_SHORT ).show( );
        } catch ( NumberFormatException nfe ) {
            Toast.makeText( this, "Food Insert error", Toast.LENGTH_LONG ).show( );
        }
        pref.setExerciseField(typeOfExerciseString);
        pref.setDurationField(exDuration.getText().toString());
        pref.setPreference(this);
        exType.setText("");
        exDuration.setText("");
        exType.requestFocus();
        AddWords();
        dbManager.close();
    }


    private void updateToDisplayToDay(){
        if(month <10 && day <10 ) {
            exDate.setText(new StringBuilder().append(year).append("-0").append(month).append("-0").append(day));
        }else if(month <10){
            exDate.setText(new StringBuilder().append(year).append("-0").append(month).append("-").append(day));
        }else if(day <10){
            exDate.setText(new StringBuilder().append(year).append("-").append(month).append("-0").append(day));
        }else
            exDate.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    private void updateDisplayToTime() {
        if(hour <10 && minute <10){
            exTime.setText(new StringBuilder().append("0").append(hour).append(":0").append(minute));
        }else if(hour <10){
            exTime.setText(new StringBuilder().append("0").append(hour).append(":").append(minute));
        }else if(minute <10) {
            exTime.setText(new StringBuilder().append(hour).append(":0").append(minute));
        }else{
            exTime.setText(new StringBuilder().append(hour).append(":").append(minute));
        }
    }

    public void goBack(View view){
        this.finish();
    }

}