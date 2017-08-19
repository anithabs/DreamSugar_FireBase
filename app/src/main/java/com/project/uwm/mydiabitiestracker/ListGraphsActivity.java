package com.project.uwm.mydiabitiestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.project.uwm.mydiabitiestracker.RecordFragment.ExerciseGraphFragment;
import com.project.uwm.mydiabitiestracker.RecordFragment.FoodGraphFragment;
import com.project.uwm.mydiabitiestracker.RecordFragment.GlucoseGraphFragment;
import com.project.uwm.mydiabitiestracker.RecordFragment.PrescriptionGraphFragment;

public class ListGraphsActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Spinner menuSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_graphs);


        setTitle("View Graphs");
        this.menuSpinner = (Spinner) findViewById(R.id.selectRecordsGraphs);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_array_graph, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        menuSpinner.setAdapter(adapter);
        menuSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String itemAtPosition =(String) parent.getItemAtPosition(position);

       if(itemAtPosition.equals("Glucose")){
            GlucoseGraphFragment newFragment = new GlucoseGraphFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recordGraphFragmentContainer, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(itemAtPosition.equals("Exercise")){
            ExerciseGraphFragment newFragment = new ExerciseGraphFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recordGraphFragmentContainer, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(itemAtPosition.equals("Prescription")){
            PrescriptionGraphFragment newFragment = new PrescriptionGraphFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recordGraphFragmentContainer, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void GoToViewRecords(View v){
        Intent intent = new Intent(this, ListMainRecords.class);
        startActivity(intent);
    }

    public void onGraphBackPressed(View v){
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}

