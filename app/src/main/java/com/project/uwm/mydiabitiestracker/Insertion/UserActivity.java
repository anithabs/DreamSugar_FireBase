package com.project.uwm.mydiabitiestracker.Insertion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.LoginActivity;
import com.project.uwm.mydiabitiestracker.R;
import com.project.uwm.mydiabitiestracker.Objects.UserObject;

public class UserActivity extends AppCompatActivity {
    public static final String UA = "UserActivity";
    private DatabaseManager dbManager;
    EditText userName, passWord, firstName, lastName, email;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        dbManager = new DatabaseManager(this);
        btnCreate = (Button)findViewById(R.id.user_add_button);
    }
    protected void onStart() {
        super.onStart();
        Log.w(UA, "inside UserActivity:onStart()\n");
    }
    protected void onRestart() {
        super.onRestart();
        Log.v(UA, "inside UserActivity:onRestart()\n");
    }
    protected void onResume() {

        super.onResume();
        Log.v(UA, "inside UserActivity:onResume()\n");
    }
    protected void onPause() {
        dbManager.close();
        super.onPause();
        Log.v(UA, "inside UserActivity:onPause()\n");
    }
    protected void onStop() {
        dbManager.close();
        super.onStop();
        Log.v(UA, "inside UserActivity:onStop()\n");
    }
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
        Log.v(UA, "inside UserActivity:onDestroy()\n");
    }
    public void newUserCreate( View v ) {
        if (v == btnCreate) {
            userName = (EditText) findViewById(R.id.user_name_value);
            passWord = (EditText) findViewById(R.id.password_value);
            firstName = (EditText) findViewById(R.id.first_name_value);
            lastName = (EditText) findViewById(R.id.last_name_value);
            email = (EditText) findViewById(R.id.email_value);

            String userNameString = userName.getText().toString();
            String passWordString = passWord.getText().toString();
            String firstNameString = firstName.getText().toString();
            String lastNameString = lastName.getText().toString();
            String emailString = email.getText().toString();


            UserObject uo = new UserObject(userNameString,passWordString,firstNameString,lastNameString,emailString);

            if (
                    userNameString.trim().length() == 0 || passWordString.trim().length() == 0
                            || firstNameString.trim().length() == 0 || lastNameString.trim().length() == 0
                            || emailString.trim().length() == 0) {
                clearText();
                Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
                return;
            }
            try {
                String name = userNameString.trim();
                int statusUser = dbManager.selectUser(name);
                if (statusUser == 1) {
                    clearText();
                    Toast.makeText(this, name + " user_already_exists", Toast.LENGTH_LONG).show();
                    return;
                }
                String emailId = emailString.trim();

                int statusEmail = dbManager.selectEmail(emailId);
                if (statusEmail == 1) {
                    clearText();
                    Toast.makeText(this, emailId + " user_already_exists", Toast.LENGTH_LONG).show();
                    return;
                }
                dbManager.insertUser(uo);
                String userCreated = "Your new username is: " + userNameString + ".";
                String welcome = "Welcome to My Diabetes Tracker. Here's to your good health, " + firstNameString + "!";

                showMessage(userCreated, welcome);
                clearText();
                Toast.makeText(this, "Details added", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "User Insert error", Toast.LENGTH_LONG).show();
            }

            String userCreated = "Your new username is: " + userName.getText() + ".";
            String welcome = "Welcome to My Diabetes Tracker. Here's to your good health, " + firstName.getText() + "!";
            showMessage(userCreated, welcome);
            clearText();
            // Proceed to the Main Menu:
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            dbManager.close();
        }

    }
    public void goBack(View view){
        this.finish();
    }

    private void clearText() {
        userName.setText("");
        passWord.setText("");
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        userName.requestFocus();
    }
    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.begin, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
