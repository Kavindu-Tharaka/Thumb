package com.ktm.thumb_first;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class TaskAdaptor extends RecyclerView.Adapter<TaskViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Tasks> listTasks;
    private ArrayList<Tasks> mArraylist;
    private DatabaseHelper mdatabase;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    public TaskAdaptor(Context context, ArrayList<Tasks> listTasks) {
        this.context = context;
        this.listTasks = listTasks;
        this.mArraylist = listTasks;
        this.mdatabase =  new DatabaseHelper(context);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent , int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_adapter, parent, false);
        return new TaskViewHolder(view);
    }














    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        final Tasks tasks =  listTasks.get(position);

        holder.task.setText(tasks.getTask());
        holder.date.setText(tasks.getDate());
        holder.time.setText(tasks.getTime());


         holder.updateButton.setOnClickListener(
         new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        editTaskDialog(tasks);
        }
        });


         holder.deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        //delete row from database

        mdatabase.deleteTasks(tasks.getId());

        //refresh the activity page.
        ((Activity)context).finish();
        context.startActivity(((Activity) context).getIntent());
        }
        });


    }

    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    private void editTaskDialog(final Tasks tasksObj){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.activity_dialog, null);

        final EditText taskField = (EditText)subView.findViewById(R.id.etTask);
        final EditText dateField = (EditText)subView.findViewById(R.id.etDate);
        final EditText timeField = (EditText)subView.findViewById(R.id.etTime);





        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        dateField.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        timeField.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {

            timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
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
            });



        if(tasksObj != null){
            taskField.setText(tasksObj.getTask());
            dateField.setText(tasksObj.getDate());
            timeField.setText(tasksObj.getTime());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Task");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String task = taskField.getText().toString();
                final String date = dateField.getText().toString();
                final String time = timeField.getText().toString();

                if(TextUtils.isEmpty(task)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                   // mdatabase.updateTask(new Tasks(task.getId(), task, date , time));
                    mdatabase.updateTask(new Tasks(tasksObj.getId() , task, date , time));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listTasks = mArraylist;
                } else {

                    ArrayList<Tasks> filteredList = new ArrayList<>();

                    for (Tasks tasks : mArraylist) {

                        if (tasks.getTask().toLowerCase().contains(charString)) {

                            filteredList.add(tasks);
                        }
                    }

                    listTasks = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listTasks;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listTasks = (ArrayList<Tasks>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
