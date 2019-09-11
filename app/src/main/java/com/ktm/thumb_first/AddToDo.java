package com.ktm.thumb_first;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddToDo extends AppCompatActivity {


    private DatabaseHelper mDatabase;
    private ArrayList<Tasks> allTasks = new ArrayList<>();
    private TaskAdaptor mAdapter;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    //private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);

        RecyclerView listview = (RecyclerView)findViewById(R.id.rev_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listview.setLayoutManager(linearLayoutManager);
        listview.setHasFixedSize(true);

        mDatabase = new DatabaseHelper(this);
        allTasks = mDatabase.listTasks();

        if(allTasks.size() > 0){
            listview.setVisibility(View.VISIBLE);
            //mAdapter = new TaskAdaptor(this, allTasks);
            mAdapter = new TaskAdaptor(this , allTasks);
            listview.setAdapter(mAdapter);

        }else {
            listview.setVisibility(View.GONE);
            Toast.makeText(this, "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }

        Button opnDialog = findViewById(R.id.pluseButton);
        opnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog();
            }
        });

    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.activity_dialog, null);

        final EditText taskField = (EditText)subView.findViewById(R.id.etTask);
        final EditText dateField = (EditText)subView.findViewById(R.id.etDate);
        final EditText timeField = (EditText)subView.findViewById(R.id.etTime);
        ///poddak methana gahala balamu date time picker eka

        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(AddToDo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        dateField.setText(day+"/"+(month+1)+"/"+year);
                    }
                },year , month,day);
                datePickerDialog.show();

            }
        });

        timeField.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddToDo.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String am_pm;
                        if(hour<12)
                        {
                            am_pm = "AM";
                            timeField.setText(hour+"."+minute+" "+am_pm);
                        }
                        else if(hour == 12)
                        {
                            am_pm = "PM";
                            timeField.setText(hour+"."+minute+" "+am_pm);
                        }
                        else
                        {
                            am_pm = "PM";
                            timeField.setText(hour+"."+minute+" "+am_pm);
                        }

                    }
                }, hour ,minute,false);
                timePickerDialog.show();
            }
        } );

        ////////////////////////////////////////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");
        builder.setView(subView);
        builder.create();

        //mthanata thamai man hithanne date and time pickers enna one balamu passe dala

        builder.setPositiveButton("ADD TASK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String task = taskField.getText().toString();
                final String date = dateField.getText().toString();
                final String time = timeField.getText().toString();

                if(TextUtils.isEmpty(task)){
                    Toast.makeText(AddToDo.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Tasks newTask = new Tasks(task,date,time);
                    mDatabase.addTasks(newTask);

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddToDo.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_menu, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter!=null)
                    mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }



}
