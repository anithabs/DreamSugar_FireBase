package com.project.uwm.mydiabitiestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.project.uwm.mydiabitiestracker.Alarm.ReminderEditActivity;
import com.project.uwm.mydiabitiestracker.Alarm.ReminderListActivity;
import com.project.uwm.mydiabitiestracker.Insertion.ExerciseActivity;
import com.project.uwm.mydiabitiestracker.Insertion.FoodInsertActivity;
import com.project.uwm.mydiabitiestracker.Insertion.GlucoseInsertActivity;
import com.project.uwm.mydiabitiestracker.Insertion.PrescriptionActivity;
import com.project.uwm.mydiabitiestracker.Insertion.RegimenActivity;
import com.project.uwm.mydiabitiestracker.Objects.RegimenReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String MA = "MainActivity";
    private RegimenReadingObject ra;
    TextView etTestedBGValue, etExercise,etPresValue,etDietValue ,etDateValue,etTimeValue;
    String userName;
    UserPreference pref;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyLogin();
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);
        userName = pref.getUserName();
        etTestedBGValue = (TextView) findViewById(R.id.tested_bgl_value_main);
        etExercise = (TextView) findViewById(R.id.exercise_regimen_value_main);
        etPresValue = (TextView) findViewById(R.id.prescription_regimen_value_main);
        etDietValue = (TextView) findViewById(R.id.diet_regimen_value_main);

        etExercise.setText(pref.getRexerciseField());
        etDietValue.setText(pref.getRdietField());
        etTestedBGValue.setText(pref.getRtestedBGLField());
        etPresValue.setText(pref.getRprescriptionField());
        etTestedBGValue.setFocusable(false);
        etExercise.setFocusable(false);
        etPresValue.setFocusable(false);
        etDietValue.setFocusable(false);
        Log.v(MA, "inside AddActivity:onCreate\n");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void verifyLogin() {
        UserPreference pref = new UserPreference(this);
        String userName,password,signoutStatus;
        userName = pref.getUserName();
        password = pref.getPassword();
        signoutStatus = pref.getSignout();

        dbManager = new DatabaseManager(this);

        int statusUser = dbManager.verifyLogin(userName,password);
        if (statusUser <= 0 ||signoutStatus.equals("true")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    protected void onStart() {
        super.onStart();
        Log.w(MA, "inside MainActivity:onStart()\n");
    }
    protected void onRestart() {
        super.onRestart();
        Log.v(MA, "inside MainActivity:onRestart()\n");
    }
    protected void onResume() {
        super.onResume();
        Log.v(MA, "inside MainActivity:onResume()\n");
    }
    protected void onPause() {
        dbManager.close();
        super.onPause();
        Log.v(MA, "inside MainActivity:onPause()\n");
    }
    protected void onStop() {
        dbManager.close();
        super.onStop();
        Log.v(MA, "inside MainActivity:onStop()\n");
    }
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
        Log.v(MA, "inside MainActivity:onDestroy()\n");
    }

    public void foodInsert(View v) {
        Intent intent = new Intent(this, FoodInsertActivity.class);
        startActivity(intent);
    }
    public void glucoseInsert(View v){
        Intent intent = new Intent(this, GlucoseInsertActivity.class);
        startActivity(intent);
    }
    public void exerciseInsert(View v){
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }
    public void prescriptionInsert(View v){
        Intent intent = new Intent(this, PrescriptionActivity.class);
        startActivity(intent);
    }
    public void regimenInsert(View v){
        Intent intent = new Intent(this, RegimenActivity.class);
        startActivity(intent);
    }
    public void RemindersCheck(View v){
        Intent intent = new Intent(this, ReminderEditActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_records) {
            Intent intent = new Intent(this,ListMainRecords.class);
            startActivity(intent);

        } else if (id == R.id.view_graphs) {
            Intent intent = new Intent(this, ListGraphsActivity.class);
            startActivity(intent);

        } else if (id == R.id.view_alarms) {
            Intent intent = new Intent(this, ReminderListActivity.class);
            startActivity(intent);

        } else if (id == R.id.user_details){
/*
            Intent intent = new Intent(this, ListGraphsActivity.class);
            startActivity(intent);*/

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
  /*  public void selectAddDetails(View v) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_signout) {
            pref = new UserPreference(this);
            // signOutStatus = pref.getUserName();
            pref.setSignout("true");
            pref.setPreference(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
