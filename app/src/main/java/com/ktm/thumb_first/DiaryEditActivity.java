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

public class DiaryEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView textView;
    TextView textView1;
    Calendar currentTime;
    int hour, minute;
    String format;
    EditText editText;
    int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);

        Intent intent = getIntent();
        String idTemp = intent.getStringExtra("ID");
        String date = intent.getStringExtra("DATE");
        String time = intent.getStringExtra("TIME");
        String content = intent.getStringExtra("CONTENT");
        id = Integer.parseInt(idTemp);

        //ListView listView = findViewById(R.id.diaryListView); //access the listView of "DiaryLisr"

        textView = findViewById(R.id.diary_edit_date);
        textView1 = findViewById(R.id.diary_edit_time);
        editText = findViewById(R.id.diary_edit_content);

        textView.setText(date);
        editText.setText(content);
        textView1.setText( time );

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        selectedTimeFormat(hour);


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(DiaryEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        String date = " "+year+" - "+month+" - "+dayOfMonth+" ";
        textView.setText(date);
    }

    public void clearDiaryUpdate(View v){
        TextView textView1 = findViewById(R.id.diary_edit_date);
        TextView textView2 = findViewById(R.id.diary_edit_time);
        EditText editText2 = findViewById(R.id.diary_edit_content);

        textView1.setText("");
        textView2.setText("");
        editText2.setText("");
    }

    public void updateDiaryEntry(View view){

        TextView textView = findViewById(R.id.diary_edit_date);
        TextView textView2 = findViewById(R.id.diary_edit_time);
        EditText editText = findViewById(R.id.diary_edit_content);

        String date = textView.getText().toString();
        String time = textView2.getText().toString();
        String content = editText.getText().toString();

        if(date.equals("") && content.equals("")){
            Toasty.warning(DiaryEditActivity.this,"Please enter details", Toast.LENGTH_SHORT).show();
        }

        else {
            DatabaseHelper dbHelper = new DatabaseHelper(DiaryEditActivity.this);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            String query = " UPDATE "+ThumbMaster.Diary.TABLE_NAME+" SET "+ThumbMaster.Diary.COLUMN_NAME_DATE+" = '" +date+ "', "+ ThumbMaster.Diary.COLUMN_NAME_CONTENT +" = '"+content+"',"+ ThumbMaster.Diary.COLUMN_NAME_TIME +" = '" +time+ "'  where "+ThumbMaster.Diary._ID+" = '"+id+"'";
            database.execSQL(query);

            Toasty.success(DiaryEditActivity.this, "Updated Successful",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this,DiaryListsActivity.class);

            finish();

            startActivity(intent);
        }
    }
}
