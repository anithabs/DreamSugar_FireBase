package com.project.uwm.mydiabitiestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.project.uwm.mydiabitiestracker.RecordFragment.ExerciseRecordsFragment;
import com.project.uwm.mydiabitiestracker.RecordFragment.FoodRecordsFragment;
import com.project.uwm.mydiabitiestracker.RecordFragment.GlucoseRecordsFragment;
import com.project.uwm.mydiabitiestracker.RecordFragment.PrescriptionRecordsFragment;


public class ListMainRecords extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner menuSpinner;


 /*   private static Switch selectorSwitch;*/
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setTitle("View Records and update");
        setContentView(R.layout.activity_viewrecords);
        this.menuSpinner = (Spinner) findViewById(R.id.selectRecords);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        menuSpinner.setAdapter(adapter);
        menuSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {
        String itemAtPosition =(String) parentView.getItemAtPosition(pos);
        if(itemAtPosition.equals("Food")){
            FoodRecordsFragment newFragment = new FoodRecordsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recordFragmentContainer, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(itemAtPosition.equals("Glucose")){
            GlucoseRecordsFragment newFragment = new GlucoseRecordsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recordFragmentContainer, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(itemAtPosition.equals("Exercise")){
            ExerciseRecordsFragment newFragment = new ExerciseRecordsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recordFragmentContainer, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(itemAtPosition.equals("Prescription")){
            PrescriptionRecordsFragment newFragment = new PrescriptionRecordsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recordFragmentContainer, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    public void refreshFoodData(View v)
    {
        Intent intent = new Intent(this, ListMainRecords.class);
        startActivity(intent);
    }
    public void foodToMain(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
    }
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onBackPressed(View v) {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
