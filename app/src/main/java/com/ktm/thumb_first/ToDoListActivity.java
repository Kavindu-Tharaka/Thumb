package com.ktm.thumb_first;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ImageButton btnplus =  findViewById(R.id.plus);

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder tsk_builder = new AlertDialog.Builder(ToDoListActivity.this);

                View task_view = getLayoutInflater().inflate(R.layout.dialog_activity, null);

                final EditText task_addtask = (EditText) task_view.findViewById(R.id.tasktxt);
                EditText task_adddate = (EditText) task_view.findViewById(R.id.datetxt);
                EditText task_addtime = (EditText) task_view.findViewById(R.id.timetxt);

                Button submit_task = task_view.findViewById(R.id.tsk_submit);

                submit_task.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (!task_addtask.getText().toString().isEmpty()) {

                            Toast.makeText(ToDoListActivity.this, "Task has added", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(ToDoListActivity.this, "Task cannot be empty!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                tsk_builder.setView(task_view);
                AlertDialog dialog = tsk_builder.create();
                dialog.show();
            }
        });

    }
}
