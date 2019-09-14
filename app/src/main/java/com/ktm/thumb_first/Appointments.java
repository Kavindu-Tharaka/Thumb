package com.ktm.thumb_first;

public class Appointments {

    private int id1;
    private String client;
    private String venue;
    private String date;
    private String time;

    public Appointments(String client, String venue, String date, String time) {
        this.client = client;
        this.venue = venue;
        this.date = date;
        this.time = time;
    }

    public Appointments(int id1, String client, String venue, String date, String time) {
        this.id1 = id1;
        this.client = client;
        this.venue = venue;
        this.date = date;
        this.time = time;
    }

    public int getId1() {
        return id1;
    }

    public String getClient() {
        return client;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id1 = id;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
