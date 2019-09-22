package com.ktm.thumb_first;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView task , date , time;
    Button updateButton , deleteButton;


    public TaskViewHolder(View itemView) {
        super(itemView);
        this.task = (TextView)itemView.findViewById(R.id.tViewtask);
        this.date = (TextView)itemView.findViewById(R.id.tViewdate);
        this.time = (TextView)itemView.findViewById(R.id.tViewtime);
        this.updateButton = (Button)itemView.findViewById(R.id.btnUpdate);
        this.deleteButton = (Button)itemView.findViewById(R.id.btnDelete);
    }

}
