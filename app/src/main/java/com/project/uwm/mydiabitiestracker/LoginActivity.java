package com.project.uwm.mydiabitiestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.Insertion.UserActivity;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;


public class LoginActivity extends AppCompatActivity {
    Button login_button;
    EditText userName, passWord;
    private static final String USER_KEY = "userKey";
    private static final String PASSWORD_KEY ="passwordKey";
    private static final String USER_DETAILS ="userDetails";
    UserPreference pref;
    public static final String LA = "LoginActivity";
    public String Signout;
    DatabaseManager dbManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create new user");
        pref = new UserPreference(this);
        setContentView(R.layout.login_main);

    }
    protected void onStart() {
        super.onStart();
        updateView();
        Log.w(LA, "inside LoginActivity:onStart()\n");
    }
    public void updateView(){
        passWord = (EditText) findViewById(R.id.hint_user);
        userName =(EditText) findViewById(R.id.hint_password);
        passWord.setText(pref.getPassword());
        userName.setText(pref.getUserName());
        userName.requestFocus();
    }
    protected void onRestart() {
        super.onRestart();
        Log.v(LA, "inside LoginActivity:onRestart()\n");
    }
    protected void onResume() {
        super.onResume();
        Log.v(LA, "inside LoginActivity:onResume()\n");
    }
    protected void onPause() {
        super.onPause();

        Log.v(LA, "inside LoginActivity:onPause()\n");
    }
    protected void onStop() {

        super.onStop();
        Log.v(LA, "inside LoginActivity:onStop()\n");
    }
    protected void onDestroy() {

        super.onDestroy();
        Log.v(LA, "inside LoginActivity:onDestroy()\n");
    }

    public void verifyLogin(View v){
        passWord = (EditText) findViewById(R.id.hint_user);
        userName =(EditText) findViewById(R.id.hint_password);
        String userNameString = userName.getText().toString().trim();
        String passWordString = passWord.getText().toString().trim();
        pref.setUserName(userNameString);
        pref.setPassword(passWordString);
        pref.setSignout("false");
        pref.setPreference(this);
        dbManager = new DatabaseManager(this);
        int statusUser = dbManager.verifyLogin(userNameString,passWordString);
        if (statusUser <= 0) {
            clearText();
            Toast.makeText(this, "UserName/Password is incorrect! Please try again!" , Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        dbManager.close();
    }
    private void clearText() {
        userName.setText("");
        passWord.setText("");
        userName.requestFocus();
    }
    public void userCreate(View v){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}