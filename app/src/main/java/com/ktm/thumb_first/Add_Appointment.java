package com.ktm.thumb_first;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class Add_Appointment extends AppCompatActivity {



    private DatabaseHelper myDB;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    private ArrayList<Appointments> allAppointments = new ArrayList<>();
    private AppointmentAdaptor adaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__appointment);

        RecyclerView RevView = (RecyclerView)findViewById(R.id.rev_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RevView.setLayoutManager(linearLayoutManager);
        RevView.setHasFixedSize(true);

        myDB = new DatabaseHelper(this);
        allAppointments = myDB.listAppointments();

        if(allAppointments.size() > 0){
            RevView.setVisibility(View.VISIBLE);
            adaptor = new AppointmentAdaptor(this, allAppointments);
            RevView.setAdapter(adaptor);

        }else {
            RevView.setVisibility(View.GONE);
            Toast.makeText(this, "No Appointments. Add one here !", Toast.LENGTH_LONG).show();
        }

        Button opnDialog = findViewById(R.id.addButton);
        opnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppointmentDialog();;
            }
        });

    }

    private void addAppointmentDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.activity_popup, null);
        final EditText client_et = (EditText)subView.findViewById(R.id.popClient);
        final EditText venue_et = (EditText)subView.findViewById(R.id.popVenue);
        final EditText date_et = (EditText)subView.findViewById(R.id.popDate);
        final EditText time_et = (EditText)subView.findViewById(R.id.popTime);


        date_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePicker = new DatePickerDialog(Add_Appointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        date_et.setText(day+"/"+(month+1)+"/"+year);
                    }
                },year , month,day);
                datePicker.show();

            }
        });

        time_et.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {

                timePicker = new TimePickerDialog(Add_Appointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String timeGap;
                        if(hour<12)
                        {
                            timeGap = "AM";
                            time_et.setText(hour+"."+minute+" "+timeGap);
                        }
                        else if(hour == 12)
                        {
                            timeGap = "PM";
                            time_et.setText(hour+"."+minute+" "+timeGap);
                        }
                        else
                        {
                            timeGap = "PM";
                            time_et.setText(hour+"."+minute+" "+timeGap);
                        }

                    }
                }, hour ,minute,false);
                timePicker.show();
            }
        } );


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add An Appointment ");
        builder.setView(subView);
        builder.create();



        builder.setPositiveButton("PLACE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String client = client_et.getText().toString();
                final String Venue = venue_et.getText().toString();
                final String date = date_et.getText().toString();
                final String time = time_et.getText().toString();

                if(TextUtils.isEmpty(client)){
                    Toast.makeText(Add_Appointment.this, "Client Name Can Not Be Null !", Toast.LENGTH_LONG).show();
                }
                else{
                    Appointments NewApp = new Appointments(client,Venue,date,time);
                    myDB.addAppointments(NewApp);

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Add_Appointment.this, "Appoitment cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myDB != null){
            myDB.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_menu2, menu);

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
                if (adaptor!=null)
                    adaptor.getFilter().filter(newText);
                return true;
            }
        });
    }


}
