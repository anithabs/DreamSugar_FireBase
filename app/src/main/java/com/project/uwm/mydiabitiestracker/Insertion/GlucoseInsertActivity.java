package com.project.uwm.mydiabitiestracker.Insertion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.GlucoseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.Calendar;
import java.util.TimeZone;

public class GlucoseInsertActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseManager dbManager;

    public static final String GI = "GlucoseInsertActivity";
    String userName;
    int min = 60;
    int max = 450;
    int step = 1;
    UserPreference pref;
    Spinner menuSpinner;
    private int day;
    private int month;
    private int year, hour,minute;
    EditText dateglu, timeglu;

        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setTitle("Log Glucose");
            pref = new UserPreference(this);
            setContentView(R.layout.activity_glucose);
            dbManager = new DatabaseManager(this);
            SeekBar simpleSeekBar = (SeekBar) findViewById(R.id.glucoseStartSeek); // initiate the Seek bar
            simpleSeekBar.setMax(max );
            int maxValue=simpleSeekBar.getMax();
            int seekBarValue= simpleSeekBar.getProgress();
            final EditText glucoseVlue =(EditText) findViewById(R.id.glucose_value) ;

            this.menuSpinner = (Spinner) findViewById(R.id.reading_taken_value);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.menu_reading, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            menuSpinner.setAdapter(adapter);
            menuSpinner.setOnItemSelectedListener(this);

            simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressChangedValue = 60;

                public void onProgressChanged(SeekBar seekBar,int progress, boolean fromUser) {
                    if (progress > min){
                        progressChangedValue = progress;
                        glucoseVlue.setText(Integer.toString(progressChangedValue));
                    }
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                    /*Toast.makeText(GlucoseInsertActivity.this, "Seek bar progress is :" + progressChangedValue,
                            Toast.LENGTH_SHORT).show();*/
                }
            });

            userName = pref.getUserName();
            dateglu = (EditText) findViewById(R.id.date_value);
            timeglu = (EditText) findViewById(R.id.time_value);
         /*   Date date = new Date();

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            dateglu.setText(df.format("yyyy-MM-dd",date));

            timeglu.setText(df.format("hh:mm",date));*/

            final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                    year =yr;
                    month = mnth+1;
                    day = monthday;
                    updateToDisplayToDay();
                }
            };
            dateglu.setOnClickListener(new View.OnClickListener() {
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
            timeglu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                    TimePickerDialog dialog = new TimePickerDialog(view.getContext(),timeListener,calender.get(calender.HOUR),calender.get(Calendar.MINUTE),true);
                    dialog.show();
                }
            });
        }
    protected void onStart() {
        super.onStart();
        Log.w(GI, "inside GlucoseInsertActivity:onStart()\n");
    }
    protected void onRestart() {
        super.onRestart();
        Log.v(GI, "inside GlucoseInsertActivity:onRestart()\n");
    }
    protected void onResume() {
        super.onResume();
        Log.v(GI, "inside GlucoseInsertActivity:onResume()\n");
    }
    protected void onPause() {
        dbManager.close();
        super.onPause();
        Log.v(GI, "inside GlucoseInsertActivity:onPause()\n");
    }
    protected void onStop() {
        dbManager.close();
        super.onStop();
        Log.v(GI, "inside GlucoseInsertActivity:onStop()\n");
    }
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
        Log.v(GI, "inside GlucoseInsertActivity:onDestroy()\n");
    }
    public void glucoseInsert(View v) {
        EditText glucoseValue = (EditText) findViewById(R.id.glucose_value);
        this.menuSpinner = (Spinner) findViewById(R.id.reading_taken_value);
        String mySpinnervalue =menuSpinner.getSelectedItem().toString();
        EditText dateglu = (EditText) findViewById(R.id.date_value);
        EditText timeglu = (EditText) findViewById(R.id.time_value);
        dbManager = new DatabaseManager(this);

        String timeString = timeglu.getText().toString();
        String dateString = dateglu.getText().toString();
        String glucoseValues = glucoseValue.getText().toString();
        String sReadingTaken =mySpinnervalue;
        int iGlucoseValue = Integer.parseInt(glucoseValues);
        pref.setGlucoseLevelField(glucoseValues);
        pref.setReadingTakenField(sReadingTaken);
        pref.setPreference(this);

        try{
            GlucoseReadingObject gco = new GlucoseReadingObject( 0,userName,iGlucoseValue,sReadingTaken,dateString,timeString );
            dbManager.insertGlucose(gco);
            Toast.makeText( this, "Details added", Toast.LENGTH_SHORT ).show( );
        } catch ( NumberFormatException nfe ) {
            Toast.makeText( this, "Food Insert error", Toast.LENGTH_LONG ).show( );
        }
        glucoseValue.setText("");
        //readingTaken.setText("");
        dbManager.close();
    }
    public void goBack(View view){
        this.finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void updateToDisplayToDay(){
        if(month <10 && day <10 ) {
            dateglu.setText(new StringBuilder().append(year).append("-0").append(month).append("-0").append(day));
        }else if(month <10){
            dateglu.setText(new StringBuilder().append(year).append("-0").append(month).append("-").append(day));
        }else if(day <10){
            dateglu.setText(new StringBuilder().append(year).append("-").append(month).append("-0").append(day));
        }else
            dateglu.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    private void updateDisplayToTime() {
        if(hour <10 && minute <10){
            timeglu.setText(new StringBuilder().append("0").append(hour).append(":0").append(minute));
        }else if(hour <10){
            timeglu.setText(new StringBuilder().append("0").append(hour).append(":").append(minute));
        }else if(minute <10) {
            timeglu.setText(new StringBuilder().append(hour).append(":0").append(minute));
        }else{
            timeglu.setText(new StringBuilder().append(hour).append(":").append(minute));
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

