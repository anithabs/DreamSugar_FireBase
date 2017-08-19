package com.project.uwm.mydiabitiestracker.EditRecords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.GlucoseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

public class UpdateGlucoseRecords extends AppCompatActivity {
    DatabaseManager DBManager;
    GlucoseReadingObject gro;
    String glucoseTime, glucoseDate,userName;
    // change to glucose
    EditText glucoseLevel;
    EditText glucoseReadingTaken;
    EditText gtGlucoseDate ;
    EditText gtGlucoseTime;
    UserPreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_glucose_records);
        Bundle extras = getIntent().getExtras();
        glucoseTime = extras.getString("GLUCOSE_TIME");
        glucoseDate = extras.getString("GLUCOSE_DATE");
        userName = pref.getUserName();
        DBManager = new DatabaseManager(this);
        gro = DBManager.selectGlucoseByTime(glucoseTime,glucoseDate,userName);
        glucoseLevel = (EditText)findViewById(R.id.update_glucose_value);
        glucoseReadingTaken =(EditText)findViewById(R.id.update_reading_taken_value);
        gtGlucoseDate =(EditText)findViewById(R.id.update_gdate_value) ;
        gtGlucoseTime =(EditText)findViewById(R.id.update_gtime_value);
        glucoseLevel.setText(Integer.toString(gro.getGlucose_level()));
        glucoseReadingTaken.setText(gro.getReading_taken());
        gtGlucoseDate.setText(gro.getGdate());
        gtGlucoseTime.setText(gro.getGtime());
        gtGlucoseDate.setFocusable(false);
        gtGlucoseTime.setFocusable(false);
    }
    public void backFromGlucoseUpdate(View view){
        this.finish();
    }
    public void glucoseDelete(View view){
        userName = pref.getUserName();
        DBManager.deleteExerciseByDateTime(glucoseTime,glucoseDate,userName);
        glucoseLevel.setText("");
        glucoseReadingTaken.setText("");
        gtGlucoseDate.setText("");
        gtGlucoseTime.setText("");
        Toast.makeText(this,"Data Deleted!",Toast.LENGTH_LONG).show();
    }

    protected void onPause() {
        DBManager.close();
        super.onPause();
    }
    protected void onStop() {
        DBManager.close();
        super.onStop();
    }
    protected void onDestroy() {
        DBManager.close();
        super.onDestroy();
    }

    public void glucoseUpdate(View v) {
        DBManager = new DatabaseManager(this);
        glucoseLevel = (EditText)findViewById(R.id.update_glucose_value);
        glucoseReadingTaken =(EditText)findViewById(R.id.update_reading_taken_value);
        gtGlucoseDate =(EditText)findViewById(R.id.update_gdate_value) ;
        gtGlucoseTime =(EditText)findViewById(R.id.update_gtime_value);

        String sReadingTaken = glucoseReadingTaken.getText().toString();
        int iGlucoseValue = Integer.parseInt(glucoseLevel.getText().toString());

        try{
            GlucoseReadingObject gco = new GlucoseReadingObject( 0,userName,iGlucoseValue,sReadingTaken,glucoseDate,glucoseTime );
            DBManager.updateGlucoseByObject(gco);
            Toast.makeText( this, "Details added", Toast.LENGTH_SHORT ).show( );
        } catch ( NumberFormatException nfe ) {
            Toast.makeText( this, "Food Insert error", Toast.LENGTH_LONG ).show( );
        }
    }

}
