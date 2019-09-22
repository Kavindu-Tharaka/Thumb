package com.ktm.thumb_first;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class DiaryAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView textView;
    TextView textView1;
    Calendar currentTime;
    int hour, minute;
    String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        textView  = findViewById(R.id.diary_save_date);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        textView1 = findViewById(R.id.diary_save_time);
        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        selectedTimeFormat(hour);
        //textView1.setText( hour + ":" + minute + " " + format );

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(DiaryAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textView1.setText(hourOfDay + ":" + minute + " " + format);
                    }
                }, hour, minute,true);
                timePickerDialog.show();
            }
        });
    }

    public void selectedTimeFormat(int hour){
        if(hour == 0){
            hour += 12;
            format = "AM";
        }else if (hour == 12){
            format = "PM";
        }else if (hour >12){
            hour -= 12;
            format = "PM";
        }else {
            format = "AM";
        }
    }

    public void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = " "+year+" - "+(month+1)+" - "+dayOfMonth+" ";
        textView.setText(date);
    }
    public void saveDiary(View v){

        TextView textView = findViewById(R.id.diary_save_date);
        TextView textView1 = findViewById(R.id.diary_save_time);
        EditText editText = findViewById(R.id.diary_save_content);

        String date = textView.getText().toString();
        String time = textView1.getText().toString();
        String content = editText.getText().toString();

        if (date.equals("") || content.equals("")) {
            Toasty.warning(DiaryAddActivity.this, "Please enter details", Toast.LENGTH_SHORT).show();
        }
        else {
            //save data
            DatabaseHelper dbHelper = new DatabaseHelper(DiaryAddActivity.this);  //create DBHelper object
            SQLiteDatabase database = dbHelper.getWritableDatabase();  //get SQLiteDatabase Writable object

            //  insert into Diary(date, content) values('2019-07-28', 'Good day')
            String query = "insert into "+ThumbMaster.Diary.TABLE_NAME+"("+
                    ThumbMaster.Diary.COLUMN_NAME_DATE+"," +" "+
                    ThumbMaster.Diary.COLUMN_NAME_TIME+", "+
                    ThumbMaster.Diary.COLUMN_NAME_CONTENT+")" +
                    " values ( ' " + date + " ', ' " + time + " ',' " + content + " ' ) ";

            database.execSQL(query);
            Toasty.success(DiaryAddActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
            finish(); //finish this activity in order to avoid so many backs

            Intent intent = new Intent(this,DiaryListsActivity.class);

            startActivity(intent);
        }
    }
    public void clearDiary(View v){
        TextView textView1 = findViewById(R.id.diary_save_date);
        TextView textView2 = findViewById(R.id.diary_save_time);
        EditText editText2 = findViewById(R.id.diary_save_content);

        textView1.setText("");
        textView2.setText("");
        editText2.setText("");
    }

}
