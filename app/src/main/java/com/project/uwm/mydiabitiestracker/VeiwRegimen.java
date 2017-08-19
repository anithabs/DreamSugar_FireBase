package com.project.uwm.mydiabitiestracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.project.uwm.mydiabitiestracker.Objects.RegimenReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;

public class VeiwRegimen extends AppCompatActivity {
    private DatabaseManager dbManager;
    private RegimenReadingObject ra;
    TextView etTestedBGValue, etExercise,etPresValue,etDietValue ,etDateValue,etTimeValue;
    String userName;
    UserPreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_regimen);
        dbManager = new DatabaseManager(this);
        userName = pref.getUserName();
        etTestedBGValue = (TextView) findViewById(R.id.tested_bgl_value_view);
        etExercise = (TextView) findViewById(R.id.exercise_regimen_value_view);
        etPresValue = (TextView) findViewById(R.id.prescription_regimen_value_view);
        etDietValue = (TextView) findViewById(R.id.diet_regimen_value_view);
        etDateValue = (TextView) findViewById(R.id.date_regimen_value_view);
        etTimeValue = (TextView) findViewById(R.id.regimen_value_time_view);

        ra = dbManager.selectRegimen(userName);

        etTestedBGValue.setText(ra.getTested());
        etExercise.setText(ra.getExercise());
        etPresValue.setText(ra.getMeds());
        etDietValue.setText(ra.getDiet());


        etTestedBGValue.setFocusable(false);
        etExercise.setFocusable(false);
        etPresValue.setFocusable(false);
        etDietValue.setFocusable(false);
        etDateValue.setFocusable(false);
        etTimeValue.setFocusable(false);
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
   public void goBack(View view){
       this.finish();
   }
}
