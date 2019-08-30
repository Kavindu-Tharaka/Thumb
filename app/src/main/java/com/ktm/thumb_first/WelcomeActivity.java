package com.ktm.thumb_first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void saveProfile(View view){
        // Create shared preferences set called welcome
        SharedPreferences profile = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        SharedPreferences.Editor profileEditor = profile.edit();

        // Read values from form
        EditText etWelcomeUserName = findViewById(R.id.et_welcome_name);
        EditText etWelcomePassword = findViewById(R.id.et_welcome_password);
        EditText etWelcomeConfirmedPassword = findViewById(R.id.et_welcome_confirmed_password);

        String userName = etWelcomeUserName.getText().toString();
        String password = etWelcomePassword.getText().toString();
        String confirmedPassword = etWelcomeConfirmedPassword.getText().toString();

        // Check for empty fields
        if(userName.isEmpty() || password.isEmpty() ||confirmedPassword.isEmpty()){
            Snackbar.make(view, "Please fill all the fields", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        else {
            // Check for password validations
            if(password.equals(confirmedPassword)){
                // Add values to sharedPreferences set
                profileEditor.putString("USER_NAME", etWelcomeUserName.getText().toString());
                profileEditor.putString("PASSWORD", etWelcomePassword.getText().toString());

                profileEditor.commit();

                Toasty.success(this, "Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            else {
                Snackbar.make(view, "Password Mismatched", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

    }
}
