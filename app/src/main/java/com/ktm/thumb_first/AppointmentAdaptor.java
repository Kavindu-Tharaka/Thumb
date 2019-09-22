package com.ktm.thumb_first;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AppointmentAdaptor extends RecyclerView.Adapter<AppointmentViewHolder> implements Filterable {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    private Context context;
    private ArrayList<Appointments> listAppointments;
    private ArrayList<Appointments> ArrayApps;
    private DatabaseHelper mydb;


    public AppointmentAdaptor(Context context, ArrayList<Appointments> list) {
        this.context = context;
        this.listAppointments = list;
        this.ArrayApps = list;
        this.mydb =  new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent , int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointment_adapter, parent, false);
        return new AppointmentViewHolder(view);
    }














    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {

        final Appointments appointments =  listAppointments.get(position);

        holder.client.setText(appointments.getClient());
        holder.venue.setText(appointments.getVenue());
        holder.date.setText(appointments.getDate());
        holder.time.setText(appointments.getTime());


         holder.upButton.setOnClickListener(
         new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        editTaskDialog(appointments);
        }
        });


         holder.delButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mydb.deleteAppointments(appointments.getId1());


        ((Activity)context).finish();
        context.startActivity(((Activity) context).getIntent());
        }
        });


    }
    @Override
    public Filter getFilter() {
        return  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listAppointments = ArrayApps;
                } else {

                    ArrayList<Appointments> filteredList = new ArrayList<>();

                    for (Appointments appointments : ArrayApps) {

                        if (appointments.getClient().toLowerCase().contains(charString)) {

                            filteredList.add(appointments);
                        }
                    }

                    listAppointments = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listAppointments;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                listAppointments = (ArrayList<Appointments>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listAppointments.size();
    }

    private void editTaskDialog(final Appointments appointmentsObj){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.activity_popup, null);

        final EditText Client_Etext = (EditText)subView.findViewById(R.id.popClient);
        final EditText Venue_Etext = (EditText)subView.findViewById(R.id.popVenue);
        final EditText Date_Etext = (EditText)subView.findViewById(R.id.popDate);
        final EditText Time_Etext = (EditText)subView.findViewById(R.id.popTime);





        Date_Etext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        Date_Etext.setText(day+"/"+(month+1)+"/"+year);
                    }
                },year , month,day);
                datePickerDialog.show();

            }
        });

        Time_Etext.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String time_gap;
                        if(hour<12)
                        {
                            time_gap = "AM";
                            Time_Etext.setText(hour+"."+minute+" "+time_gap);
                        }
                        else if(hour == 12)
                        {
                            time_gap = "PM";
                            Time_Etext.setText(hour+"."+minute+" "+time_gap);
                        }
                        else
                        {
                            time_gap = "PM";
                            Time_Etext.setText(hour+"."+minute+" "+time_gap);
                        }

                    }
                }, hour ,minute,false);
                timePickerDialog.show();
            }
        } );



        if(appointmentsObj != null){
            Client_Etext.setText(appointmentsObj.getClient());
            Venue_Etext.setText(appointmentsObj.getVenue());
            Date_Etext.setText(appointmentsObj.getDate());
            Time_Etext.setText(appointmentsObj.getTime());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Appointment");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String Client2 = Client_Etext.getText().toString();
                final String Venue2 = Venue_Etext.getText().toString();
                final String Date2 = Date_Etext.getText().toString();
                final String Time2 = Time_Etext.getText().toString();

                if(TextUtils.isEmpty(Client2)){
                    Toast.makeText(context, "Client Name Can Not Be Null !", Toast.LENGTH_LONG).show();
                }
                else{

                    mydb.updateAppointment(new Appointments(appointmentsObj.getId1() , Client2, Venue2, Date2, Time2));

                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Appoitment cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }



}
