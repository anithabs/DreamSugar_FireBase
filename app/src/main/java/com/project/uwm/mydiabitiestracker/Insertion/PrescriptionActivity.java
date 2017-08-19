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
import com.project.uwm.mydiabitiestracker.Objects.PrescriptionReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.Objects.WordObject;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class PrescriptionActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private AutoCompleteTextView text;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> ArrayWords = new ArrayList<>();
    private EditText dateTaken;
    private EditText timeTaken;
    private EditText etDrugName, etDosage;
    private String userName;
    UserPreference pref;
    private int day;
    private int month;
    private int year, hour,minute;

    public static final String PA = "PrescriptionActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Prescription");
        pref = new UserPreference(this);
        setContentView(R.layout.activity_prescription);

        AddWords();

        userName=pref.getUserName();
        etDrugName =(EditText) findViewById(R.id.prescription_type_value);
        etDosage = (EditText) findViewById(R.id.dosage_value);
        etDrugName.setText(pref.getDrugNameField());
        etDosage.setText(pref.getDosageField());
        timeTaken = (EditText) findViewById(R.id.time_administered_value);
        dateTaken = (EditText) findViewById(R.id.date_administered_value);
        /*Date date = new Date();

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        dateTaken.setText(df.format("yyyy-MM-dd", date));
        timeTaken = (EditText) findViewById(R.id.time_administered_value);
        timeTaken.setText(df.format("hh:mm", date));*/
        EditText p_type = (EditText) findViewById(R.id.prescription_type_value);
        p_type.requestFocus();

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth + 1;
                day = monthday;
                updateToDisplayToDay();
            }
        };
        dateTaken.setOnClickListener(new View.OnClickListener() {
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
        timeTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                TimePickerDialog dialog = new TimePickerDialog(view.getContext(),timeListener,calender.get(calender.HOUR),calender.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });

    }
    protected void onStart() {
        //this.AddWords();
        super.onStart();
        Log.w(PA, "inside PrescriptionActivity:onStart()\n");
    }
    protected void onRestart() {
        //this.AddWords();
        super.onRestart();
        Log.v(PA, "inside PrescriptionActivity:onRestart()\n");
    }
    protected void onResume() {
        //this.AddWords();
        super.onResume();
        Log.v(PA, "inside PrescriptionActivity:onResume()\n");
    }
    protected void onPause() {
        dbManager.close();
        super.onPause();
        Log.v(PA, "inside PrescriptionActivity:onPause()\n");
    }
    protected void onStop() {
        dbManager.close();
        super.onStop();
        Log.v(PA, "inside PrescriptionActivity:onStop()\n");
    }
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
        Log.v(PA, "inside PrescriptionActivity:onDestroy()\n");
    }
    public void AddWords() {
        dbManager = new DatabaseManager(this);
        try{
            if (dbManager.ifSourceColumnHasId("R.id.prescription_type_value")) {
                ArrayWords = dbManager.selectWordsBySource("R.id.prescription_type_value");
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayWords);
                text = (AutoCompleteTextView) findViewById(R.id.prescription_type_value);
                // set adapter for the auto complete field
                text.setAdapter(adapter);
                // specify the minimum type of characters before drop-down list is shown
                text.setThreshold(1);
            }
        }
        catch (NullPointerException npe){
            Toast.makeText( this, "No current words in source 'R.id.prescription_type_value'", Toast.LENGTH_LONG ).show( );
        }
        dbManager.close();
    }
    public void prescriptionInsert(View v) {

        dbManager = new DatabaseManager(this);
        userName=pref.getUserName();
        EditText drugName = (EditText) findViewById(R.id.prescription_type_value);
        EditText prescriptionDose = (EditText) findViewById(R.id.dosage_value);
        EditText dateTaken = (EditText) findViewById(R.id.date_administered_value);
        EditText timeTaken = (EditText) findViewById(R.id.time_administered_value);
        String timeString = timeTaken.getText().toString();
        String dateString = dateTaken.getText().toString();
        String drugNameString = drugName.getText().toString();


        int doseInt = Integer.parseInt(prescriptionDose.getText().toString());

        try {
            PrescriptionReadingObject pro = new PrescriptionReadingObject(
                    0,
                    userName,
                    drugNameString,
                    doseInt,
                    dateString,
                    timeString);
            dbManager.insertPrescription(pro);

            WordObject wo = new WordObject(
                    0,
                    drugNameString,
                    "R.id.prescription_type_value");
            int occurrences = dbManager.numOccurrencesByObject(wo);
            if (occurrences == 0) {
                dbManager.insertWord(wo);
            }
            Toast.makeText(this, "Details added", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Food Insert error", Toast.LENGTH_LONG).show();
        }
        pref.setDrugNameField(drugNameString);
        pref.setDosageField(prescriptionDose.getText().toString());
        pref.setPreference(this);
        drugName.setText("");
        prescriptionDose.setText("");
        AddWords();
        dbManager.close();
    }

    private void updateToDisplayToDay(){
        if(month <10 && day <10 ) {
            dateTaken.setText(new StringBuilder().append(year).append("-0").append(month).append("-0").append(day));
        }else if(month <10){
            dateTaken.setText(new StringBuilder().append(year).append("-0").append(month).append("-").append(day));
        }else if(day <10){
            dateTaken.setText(new StringBuilder().append(year).append("-").append(month).append("-0").append(day));
        }else
            dateTaken.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    private void updateDisplayToTime() {
        if(hour <10 && minute <10){
            timeTaken.setText(new StringBuilder().append("0").append(hour).append(":0").append(minute));
        }else if(hour <10){
            timeTaken.setText(new StringBuilder().append("0").append(hour).append(":").append(minute));
        }else if(minute <10) {
            timeTaken.setText(new StringBuilder().append(hour).append(":0").append(minute));
        }else{
            timeTaken.setText(new StringBuilder().append(hour).append(":").append(minute));
        }
    }

    public void goBack(View view){
        this.finish();
    }

}