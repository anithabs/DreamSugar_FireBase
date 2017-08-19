package com.project.uwm.mydiabitiestracker.EditRecords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.ExerciseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

public class UpdateExerciseRecords extends AppCompatActivity {
    DatabaseManager DBManager;
    ExerciseReadingObject ero;
    String exerciseTime, exerciseDate;
    EditText typeOfExercise;
    EditText minutesOfExercise;
    EditText etExerciseDate ;
    EditText etExerciseTime;
    String userName;
    UserPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise_records);
        Bundle extras = getIntent().getExtras();
        exerciseTime = extras.getString("EXERCISE_TIME");
        exerciseDate = extras.getString("EXERCISE_DATE");
        userName = pref.getUserName();
        DBManager= new DatabaseManager(this);

        ero = DBManager.selectExerciseByTime(exerciseTime,exerciseDate,userName);
        typeOfExercise = (EditText) findViewById(R.id.update_exercise_type_value);
        minutesOfExercise = (EditText) findViewById(R.id.update_exercise_duration_value);
        etExerciseDate = (EditText) findViewById(R.id.update_exercise_date_value);
        etExerciseTime = (EditText) findViewById(R.id.update_exercise_start_time_value);
        typeOfExercise.setText(ero.getExerciseType());
        minutesOfExercise.setText(Integer.toString(ero.getDuration()));
        etExerciseTime.setText(ero.getTime());
        etExerciseDate.setText(ero.getDate());
        etExerciseDate.setFocusable(false);
        etExerciseTime.setFocusable(false);
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

    public void exerciseDelete(View view){
        userName = pref.getUserName();
        DBManager.deleteExerciseByDateTime(exerciseTime,exerciseDate,userName);
        typeOfExercise.setText("");
        minutesOfExercise.setText("");
        etExerciseTime.setText("");
        etExerciseDate.setText("");
        Toast.makeText(this,"Data Deleted!",Toast.LENGTH_LONG).show();
    }
    public void backFromExerciseUpdate(View view){
        this.finish();
    }
    public void exerciseUpdate(View view){
        typeOfExercise = (EditText) findViewById(R.id.update_exercise_type_value);
        minutesOfExercise = (EditText) findViewById(R.id.update_exercise_duration_value);
        etExerciseDate = (EditText) findViewById(R.id.update_exercise_date_value);
        etExerciseTime = (EditText) findViewById(R.id.update_exercise_start_time_value);
        String timeString = etExerciseTime.getText().toString();
        String dateString = etExerciseDate.getText().toString();
        String typeOfExerciseString = typeOfExercise.getText().toString();
        int minutesOfExerciseInt = Integer.parseInt(minutesOfExercise.getText().toString());

        try{
            ExerciseReadingObject eco = new ExerciseReadingObject(
                    0,userName,
                    typeOfExerciseString,
                    minutesOfExerciseInt,
                    dateString,
                    timeString );
            DBManager.updateExerciseByObject(eco);
            Toast.makeText( this, "Details added", Toast.LENGTH_SHORT ).show( );
        } catch ( NumberFormatException nfe ) {
            Toast.makeText( this, "Food Insert error", Toast.LENGTH_LONG ).show( );
        }
        typeOfExercise.requestFocus();
    }

}
