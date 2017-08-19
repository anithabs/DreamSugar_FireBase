package com.project.uwm.mydiabitiestracker.Insertion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.RegimenReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class RegimenActivity extends AppCompatActivity {
    UserPreference pref;
    String sTestedBGValue;
    String sExercise;
    String sPresValue;
    String sDietValue;
    String sDateValue;
    String sTimeValue;
    private DatabaseManager dbManager;
    EditText etTestedBGValue;
    EditText etExercise;
    EditText etPresValue;
    EditText etDietValue;
    EditText etDateValue;
    EditText etTimeValue;
    String userName;
    private int day;
    private int month;
    private int year, hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Log Regimen");
        pref = new UserPreference(this);
        setContentView(R.layout.activity_regimen);
        dbManager = new DatabaseManager(this);
        userName = pref.getUserName();
        Date date = new Date();
        etExercise =(EditText) findViewById(R.id.exercise_regimen_value);
        etTestedBGValue = (EditText) findViewById(R.id.tested_bgl_value);
        etPresValue =(EditText) findViewById(R.id.prescription_regimen_value);
        etDietValue = (EditText) findViewById(R.id.diet_regimen_value);
        etExercise.setText(pref.getRexerciseField());
        etDietValue.setText(pref.getRdietField());
        etTestedBGValue.setText(pref.getRtestedBGLField());
        etPresValue.setText(pref.getRprescriptionField());

        etDateValue = (EditText) findViewById(R.id.date_regimen_value);
        /*android.text.format.DateFormat df = new android.text.format.DateFormat();*/
        /*etDateValue.setText(df.format("yyyy-MM-dd",date));*/
        etTimeValue = (EditText) findViewById(R.id.time_regimen_value);
        /*etTimeValue.setText(df.format("hh:mm",date));
*/
        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth + 1;
                day = monthday;
                updateToDisplayToDay();
            }
        };
        etDateValue.setOnClickListener(new View.OnClickListener() {
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
        etTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                TimePickerDialog dialog = new TimePickerDialog(view.getContext(),timeListener,calender.get(calender.HOUR),calender.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });

    }

    protected void onPause() {
        dbManager.close();
        super.onPause();
    }
    protected void onStop() {
        dbManager.close();
        super.onStop();
    }
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
    public void regimenInsert(View view){
        etTestedBGValue = (EditText)findViewById(R.id.tested_bgl_value);
        etExercise = (EditText) findViewById(R.id.exercise_regimen_value);
        etPresValue = (EditText) findViewById(R.id.prescription_regimen_value);
        etDietValue = (EditText) findViewById(R.id.diet_regimen_value);
        etDateValue = (EditText)findViewById(R.id.date_regimen_value);
        etTimeValue = (EditText) findViewById(R.id.time_regimen_value);

        sTestedBGValue = etTestedBGValue.getText().toString();
        sExercise = etExercise.getText().toString();
        sPresValue = etPresValue.getText().toString();
        sDietValue = etDietValue.getText().toString();
        sDateValue = etDateValue.getText().toString();
        sTimeValue = etTimeValue.getText().toString();

        RegimenReadingObject rro = new RegimenReadingObject(0,userName,sTestedBGValue,sExercise,sPresValue,sDietValue,sDateValue,sTimeValue);
        try{
            dbManager.insertRegime(rro);
            Toast.makeText( this, "Details added", Toast.LENGTH_SHORT ).show( );
        } catch ( NumberFormatException nfe ) {
            Toast.makeText( this, "Regime Insert error", Toast.LENGTH_LONG ).show( );
        }

        pref.setRtestedBGLField(sTestedBGValue);
        pref.setRprescriptionField(sPresValue);
        pref.setRexerciseField(sExercise);
        pref.setRdietField(sDietValue);
        pref.setPreference(this);
    }

    private void updateToDisplayToDay(){
        if(month <10 && day <10 ) {
            etDateValue.setText(new StringBuilder().append(year).append("-0").append(month).append("-0").append(day));
        }else if(month <10){
            etDateValue.setText(new StringBuilder().append(year).append("-0").append(month).append("-").append(day));
        }else if(day <10){
            etDateValue.setText(new StringBuilder().append(year).append("-").append(month).append("-0").append(day));
        }else
            etDateValue.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    private void updateDisplayToTime() {
        if(hour <10 && minute <10){
            etTimeValue.setText(new StringBuilder().append("0").append(hour).append(":0").append(minute));
        }else if(hour <10){
            etTimeValue.setText(new StringBuilder().append("0").append(hour).append(":").append(minute));
        }else if(minute <10) {
            etTimeValue.setText(new StringBuilder().append(hour).append(":0").append(minute));
        }else{
            etTimeValue.setText(new StringBuilder().append(hour).append(":").append(minute));
        }
    }

    public void goBack(View view){
        this.finish();
    }


}