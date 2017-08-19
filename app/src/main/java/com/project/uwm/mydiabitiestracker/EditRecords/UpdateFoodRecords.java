package com.project.uwm.mydiabitiestracker.EditRecords;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.FoodConsumedObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

public class UpdateFoodRecords extends AppCompatActivity {
    DatabaseManager DBManager;
    FoodConsumedObject fco;
    String foodTime,foodDate;
    EditText etFoodType;
    EditText etFoodAmount;
    EditText etFoodTime;
    EditText etFoodDate;
    String userName;
    UserPreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food_records);
        Bundle extras = getIntent().getExtras();
        foodTime = extras.getString("FOOD_TIME");
        foodDate = extras.getString("FOOD_DATE");
        DBManager = new DatabaseManager(this);
        userName = pref.getUserName();
        fco = DBManager.selectFoodByTime(foodTime,foodDate,userName);

        etFoodType = (EditText)findViewById(R.id.updatefoodtypevalue);
        etFoodAmount = (EditText) findViewById(R.id.update_amount_food_value);
        etFoodTime = (EditText) findViewById(R.id.update_time_value_food);
        etFoodDate = (EditText) findViewById(R.id.update_date_value_food);


        etFoodAmount.setText(Integer.toString(fco.getAmountOfFood()));
        etFoodDate.setText(fco.getDate());
        etFoodTime.setText(fco.getTime());
        etFoodType.setText(fco.getTypeOfFood());
        etFoodDate.setFocusable(false);
        etFoodTime.setFocusable(false);

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
    public void foodDelete(View view){
        userName = pref.getUserName();
        DBManager.deleteFoodByDateTime(foodTime,foodDate,userName);
        etFoodAmount.setText("");
        etFoodType.setText("");
        etFoodTime.setText("");
        etFoodDate.setText("");
        Toast.makeText(this,"Data Deleted!",Toast.LENGTH_LONG).show();
    }
    public void foodUpdate(View view){
        etFoodType = (EditText)findViewById(R.id.updatefoodtypevalue);
        etFoodAmount = (EditText) findViewById(R.id.update_amount_food_value);
        etFoodTime = (EditText) findViewById(R.id.update_time_value_food);
        etFoodDate = (EditText) findViewById(R.id.update_date_value_food);


        String timeString = etFoodTime.getText().toString();
        String dateString = etFoodDate.getText().toString();

        String foodtypeamount = etFoodAmount.getText().toString();
        String foodtype = etFoodType.getText().toString();;
        int amountOfFood = Integer.parseInt(foodtypeamount);


        try{
            FoodConsumedObject fco = new FoodConsumedObject( 0,userName,foodtype, amountOfFood ,dateString,timeString );
            DBManager.updateFoodByObject(fco);
            Toast.makeText( this, "Details added", Toast.LENGTH_SHORT ).show( );
        } catch ( NumberFormatException nfe ) {
            Toast.makeText( this, "Food Insert error", Toast.LENGTH_LONG ).show( );
        }

    }
   public void backFromFoodUpdate(View view){
       this.finish();
   }
}
