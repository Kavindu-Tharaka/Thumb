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

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get shared preferences file
        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);

        // Get values from shared preferences
        String storedUserName = welcome.getString("USER_NAME", "");
        String storedPassword = welcome.getString("PASSWORD", "");

        // Create reference to form fields
        EditText etProfileUserName = findViewById(R.id.profile_user_name);
        EditText etProfilePassword = findViewById(R.id.profile_password);
        EditText etProfileConfirmedPassword = findViewById(R.id.profile_confirmed_password);

        // Set existing values to edit text fields
        etProfileUserName.setText(storedUserName);
        etProfilePassword.setText(storedPassword);
        etProfileConfirmedPassword.setText(storedPassword);
    }

    public void update(View view){
        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        SharedPreferences.Editor profileEditor = welcome.edit();

        EditText etProfileUserName = findViewById(R.id.profile_user_name);
        EditText etProfilePassword = findViewById(R.id.profile_password);
        EditText etProfileConfirmedPassword = findViewById(R.id.profile_confirmed_password);

        String userName = etProfileUserName.getText().toString();
        String password = etProfilePassword.getText().toString();
        String confirmedPassword = etProfileConfirmedPassword.getText().toString();

        // Check for empty fields
        if(userName.isEmpty() || password.isEmpty() ||confirmedPassword.isEmpty()){
            Snackbar.make(view, "Please fill all the fields", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        else {
            // Check for password validations
            if (password.equals(confirmedPassword)) {
                // Add values to sharedPreferences set
                profileEditor.putString("USER_NAME", etProfileUserName.getText().toString());
                profileEditor.putString("PASSWORD", etProfilePassword.getText().toString());

                profileEditor.commit();

                Toasty.success(this, "Profile Updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
            } else {
                Snackbar.make(view, "Password Mismatched", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);

    }
}
