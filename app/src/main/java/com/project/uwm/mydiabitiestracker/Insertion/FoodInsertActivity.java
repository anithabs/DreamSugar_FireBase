package com.project.uwm.mydiabitiestracker.Insertion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.FoodConsumedObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.Objects.WordObject;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class FoodInsertActivity extends AppCompatActivity implements AutoCompleteTextView.OnEditorActionListener {

    private DatabaseManager dbManager;
    private AutoCompleteTextView text;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> ArrayWords = new ArrayList<>();
    private int day;
    private int month;
    private int year, hour,minute;
    public static final String FI = "FoodInsertActivity";
    UserPreference pref;
    EditText foodType, foodAmount, dateFood, timeFood;
    String userName;
    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Add Food");
        pref = new UserPreference(this);
        setContentView(R.layout.activity_food);

        AddWords();
        foodType = (AutoCompleteTextView) findViewById(R.id.foodtypevalue);
        foodAmount = (EditText) findViewById(R.id.amount_food_value);
        userName = pref.getUserName();
        foodType.setText(pref.getTypeOfFoodField());
        foodAmount.setText(pref.getAmountOfFoodField());
        timeFood = (EditText) findViewById(R.id.time_value_f);
        dateFood = (EditText) findViewById(R.id.date_value_f);

     /*   Date date = new Date();

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        dateFood.setText(df.format("yyyy-MM-dd",date));

        timeFood.setText(df.format("hh:mm",date));*/

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth +1;
                day = monthday;
                updateToDisplayToDay();
            }
        };
        dateFood.setOnClickListener(new View.OnClickListener() {
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
        timeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                TimePickerDialog dialog = new TimePickerDialog(view.getContext(),timeListener,calender.get(calender.HOUR),calender.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        this.AddWords();
        super.onStart();
        Log.w(FI, "inside FoodInsertActivity:onStart()\n");
    }
    @Override
    public void onRestart() {
        this.AddWords();
        super.onRestart();
        Log.v(FI, "inside FoodInsertActivity:onRestart()\n");
    }
    @Override
    protected void onResume() {
        this.AddWords();
        super.onResume();
        Log.v(FI, "inside FoodInsertActivity:onResume()\n");
    }
    @Override
    public void onPause() {
        dbManager.close();
        super.onPause();
        Log.v(FI, "inside FoodInsertActivity:onPause()\n");
    }
    @Override
    public void onStop() {
        super.onStop();
        dbManager.close();
        //savePrefs();
        Log.v(FI, "inside FoodInsertActivity:onStop()\n");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
        Log.v(FI, "inside FoodInsertActivity:onDestroy()\n");

    }
    public void AddWords() {
        dbManager = new DatabaseManager(this);
        try{
            if (dbManager.ifSourceColumnHasId("R.id.foodtypevalue")) {
                ArrayWords = dbManager.selectWordsBySource("R.id.foodtypevalue");
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayWords);
                text = (AutoCompleteTextView) findViewById(R.id.foodtypevalue);
                // set adapter for the auto complete field
                text.setAdapter(adapter);
                // specify the minimum type of characters before drop-down list is shown
                text.setThreshold(1);
            }
        }
        catch (NullPointerException npe){
            Toast.makeText( this, "No current words in source 'R.id.foodValuetype'", Toast.LENGTH_LONG ).show( );
        }
        dbManager.close();
    }
    public void foodInsertDataBase( View v ) {

        dbManager = new DatabaseManager(this);
        foodType = (AutoCompleteTextView) findViewById(R.id.foodtypevalue);
        foodAmount = (EditText) findViewById(R.id.amount_food_value);
        dateFood = (EditText) findViewById(R.id.date_value_f);
        timeFood = (EditText) findViewById(R.id.time_value_f);
        String timeString = timeFood.getText().toString();
        String dateString = dateFood.getText().toString();
        String foodtype = foodType.getText().toString();


        String foodtypeamount = foodAmount.getText().toString();
        int amountOfFood;
        try {
            amountOfFood = Integer.parseInt( foodtypeamount );
        }
        catch( Exception e ) {
            Toast.makeText( this, "Food amount must be an integer. Changed to a quantity of '0'.", Toast.LENGTH_LONG ).show( );
            amountOfFood = 0;
        }
        try{
            FoodConsumedObject fco = new FoodConsumedObject(
                    0,
                    userName,
                    foodtype,
                    amountOfFood,
                    dateString,
                    timeString );
            dbManager.insertFood(fco);

            WordObject wo = new WordObject(
                    0,
                    foodtype,
                    "R.id.foodtypevalue");
            int occurrences = dbManager.numOccurrencesByObject(wo);
            if (occurrences == 0) {
                dbManager.insertWord(wo);
            }
            Toast.makeText( this, "Details added", Toast.LENGTH_SHORT ).show( );
        } catch ( NumberFormatException nfe ) {
            Toast.makeText( this, "Food Insert error", Toast.LENGTH_LONG ).show( );
        }
        pref.setTypeOfFoodField(foodtype);
        pref.setAmountOfFoodField(foodtypeamount);
        pref.setPreference(this);
        foodAmount.setText("");
        foodType.setText("");
        foodType.requestFocus();
        AddWords();
        dbManager.close();

    }

    private void updateToDisplayToDay(){
        if(month <10 && day <10 ) {
            dateFood.setText(new StringBuilder().append(year).append("-0").append(month).append("-0").append(day));
        }else if(month <10){
            dateFood.setText(new StringBuilder().append(year).append("-0").append(month).append("-").append(day));
        }else if(day <10){
            dateFood.setText(new StringBuilder().append(year).append("-").append(month).append("-0").append(day));
        }else
            dateFood.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    private void updateDisplayToTime() {
        if(hour <10 && minute <10){
            timeFood.setText(new StringBuilder().append("0").append(hour).append(":0").append(minute));
        }else if(hour <10){
            timeFood.setText(new StringBuilder().append("0").append(hour).append(":").append(minute));
        }else if(minute <10) {
            timeFood.setText(new StringBuilder().append(hour).append(":0").append(minute));
        }else{
            timeFood.setText(new StringBuilder().append(hour).append(":").append(minute));
        }
    }

    public void goBack(View view){
        this.finish();
    }



    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i== EditorInfo.IME_ACTION_DONE){
            return  true;
        }
        return false;
    }
}