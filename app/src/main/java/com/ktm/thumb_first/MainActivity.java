package com.ktm.thumb_first;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);

        boolean loginStatus = LoginActivity.getLoginStatus();

        if(welcome.contains("USER_NAME")){

            if(loginStatus != true) {

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        else {
            //if the very first time then go to WelcomeActivity
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);

        if(welcome.getString("LOGIN_SECURITY", "").equals("ON")){
            //noinspection SimplifiableIfStatement
            if (id == R.id.nav_logout) {

            }
            else {
                //findViewById(R.id.nav_logout).setEnabled(false);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View view = findViewById(id);

        if (id == R.id.nav_home) {
            showHome(view);
        } else if (id == R.id.nav_proflie) {
            showProfile(view);
        }else if (id == R.id.nav_logout) {
            LoginActivity.setLoginStatus(false);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }



    public void redirectToDoList(View v) {
        Intent intent = new Intent(this, AddToDo.class);
        startActivity(intent);
    }
    /*public void redirectAppointments(View v){
        Intent intent = new Intent(this,AddAppointment.class);
        startActivity(intent);
    }*/
    public void redirectDiary(View v) {
        Intent intent = new Intent(this,DiaryListsActivity.class);
        startActivity(intent);
    }
    public void redirectShoppingList(View v){
        Intent intent = new Intent(this,ShoppingListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Do you really want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No",null)
        // .setCancelable(false)
        ;

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
