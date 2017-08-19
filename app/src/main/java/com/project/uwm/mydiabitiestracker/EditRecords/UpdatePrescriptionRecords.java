package com.project.uwm.mydiabitiestracker.EditRecords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.PrescriptionReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

public class UpdatePrescriptionRecords extends AppCompatActivity {
    DatabaseManager DBManager;
    PrescriptionReadingObject pro;
    String prescriptionTime, prescriptionDate;
    EditText presValue;
    EditText dosage;
    EditText etPersDate ;
    EditText etPersTime;
    String userName;
    UserPreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_prescription_records);
        Bundle extras = getIntent().getExtras();
        userName = pref.getUserName();
        prescriptionTime = extras.getString("PRESCRIPTION_TIME");
        prescriptionDate = extras.getString("PRESCRIPTION_DATE");
        DBManager = new DatabaseManager(this);
        pro = DBManager.selectPrescriptionByDateTime(prescriptionTime,prescriptionDate,userName);
        presValue = (EditText)findViewById(R.id.update_prescription_value);
        dosage = (EditText)findViewById(R.id.update_dosage_value);
        etPersDate = (EditText)findViewById(R.id.update_pdate);
        etPersTime = (EditText)findViewById(R.id.update_pTime);
        presValue.setText(pro.getDrugName());
        dosage.setText(Integer.toString(pro.getDosage()));
        etPersDate.setText(pro.getDate());
        etPersTime.setText(pro.getTime());
        etPersDate.setFocusable(false);
        etPersTime.setFocusable(false);
    }
    public void backFromPrescriptionUpdate(View view){
        this.finish();
    }
    public void prescriptionDelete(View view){
        userName = pref.getUserName();
        DBManager.deletePrescriptionByDateTime(prescriptionTime,prescriptionDate,userName);
        presValue.setText("");
        dosage.setText("");
        etPersTime.setText("");
        etPersDate.setText("");
    }
    public void prescriptionUpdate(View view){
        DBManager = new DatabaseManager(this);
        presValue = (EditText)findViewById(R.id.update_prescription_value);
        dosage = (EditText)findViewById(R.id.update_dosage_value);
        etPersDate = (EditText)findViewById(R.id.update_pdate);
        etPersTime = (EditText)findViewById(R.id.update_pTime);

        String drugNameString = presValue.getText().toString();
        int doseInt = Integer.parseInt(dosage.getText().toString());

        try {
            PrescriptionReadingObject pro = new PrescriptionReadingObject(0,userName, drugNameString,doseInt,prescriptionDate, prescriptionTime );
            DBManager.updatePrescription(pro);
            Toast.makeText(this, "Details added", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Food Insert error", Toast.LENGTH_LONG).show();
        }

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
}