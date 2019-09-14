package com.ktm.thumb_first;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {

    TextView client,venue, date , time;
    Button upButton , delButton;


    public AppointmentViewHolder(View itemView) {
        super(itemView);
        this.client = (TextView)itemView.findViewById(R.id.showClient);
        this.venue = (TextView)itemView.findViewById(R.id.showVenue);
        this.date = (TextView)itemView.findViewById(R.id.showDate);
        this.time = (TextView)itemView.findViewById(R.id.showTime);
        this.upButton = (Button)itemView.findViewById(R.id.btnUpdate);
        this.delButton = (Button)itemView.findViewById(R.id.btnDelete);
    }
}
