package com.ktm.thumb_first;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ThumbDB.db";

    //Savindri
    //table declaration
    private static final String TABLE_TASKS = "todo_table";

    //declarations of columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK = "task_col";
    private static final String COLUMN_DATE = "date_col";
    private static final String COLUMN_TIME = "time_col";
    //
    //

    //CHAMIKA

    private static final String TAB_APPOINTMENTS = "app_table";

    private static final String COL_ID = "id";
    private static final String COL_CLIENT = "client_col";
    private static final String COL_VENUE = "venue_col";
    private static final String COL_DATE = "date_col";
    private static final String COL_TIME = "time_col";

    //CHAMIKA

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES_SHOPPING_LIST = " CREATE TABLE " +ThumbMaster.ShoppingList.TABLE_NAME+

                "( " +ThumbMaster.ShoppingList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ThumbMaster.ShoppingList.COLUMN_NAME_ITEM + " TEXT, " +
                ThumbMaster.ShoppingList.COLUMN_NAME_QUANTITY + " TEXT, " +
                ThumbMaster.ShoppingList.COLUMN_NAME_DATE + " TEXT, " +
                ThumbMaster.ShoppingList.COLUMN_NAME_ISBOUGHT + " INTEGER DEFAULT 0)";


        db.execSQL(SQL_CREATE_ENTRIES_SHOPPING_LIST);

        //Shshini
        String SQL_CREATE_ENTRIES_DIARY = "create table "+ ThumbMaster.Diary.TABLE_NAME +
                "("+ ThumbMaster.Diary._ID +" INTEGER primary key AUTOINCREMENT, "+
                ThumbMaster.Diary.COLUMN_NAME_DATE +" TEXT, "+
                ThumbMaster.Diary.COLUMN_NAME_TIME +" TEXT,"+
                ThumbMaster.Diary.COLUMN_NAME_CONTENT+" TEXT) ";

        db.execSQL(SQL_CREATE_ENTRIES_DIARY);
        //Shshini


        //savindri
        //creating task table
        String	CREATE_TASK_TABLE = "CREATE	TABLE " + TABLE_TASKS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_TASK + "  TEXT, " + COLUMN_DATE + " TEXT, "+COLUMN_TIME+" TEXT" + ")";
        db.execSQL(CREATE_TASK_TABLE);
        //savindri


        //CHAMIKA

        String	CREATE_APPOINTMENT_TABLE = "CREATE	TABLE " + TAB_APPOINTMENTS + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_CLIENT + "  TEXT," + COL_VENUE + "  TEXT, " + COL_DATE + " TEXT, "+COL_TIME+" TEXT" + ")";
        db.execSQL(CREATE_APPOINTMENT_TABLE);

        //CHAMIKA



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CHAMIKA
        db.execSQL("DROP TABLE IF EXISTS " + TAB_APPOINTMENTS);
        onCreate(db);
        //CHAMIKA
    }

    //Savindri

    //to insert

    public void addTasks(Tasks tasks){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, tasks.getTask());
        values.put(COLUMN_DATE, tasks.getDate());
        values.put(COLUMN_TIME, tasks.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
    }
//to retriew
    public ArrayList<Tasks> listTasks(){
        String sql = "select * from " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Tasks> storeTasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String task = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                storeTasks.add(new Tasks(id, task, date,time));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeTasks;
    }
    public void updateTask(Tasks task){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task.getTask());
        values.put(COLUMN_DATE, task.getDate());
        values.put(COLUMN_TIME, task.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_TASKS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(task.getId())});
    }
//to delete
    public void deleteTasks(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
//to search
    public Tasks findTasks(String task){
        String query = "Select * FROM "	+ TABLE_TASKS + " WHERE " + COLUMN_TASK + " = " + "task";
        SQLiteDatabase db = this.getWritableDatabase();
        Tasks tasks = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int Find_id = Integer.parseInt(cursor.getString(0));
            String Find_task = cursor.getString(1);
            String Find_date = cursor.getString(2);
            String Find_time = cursor.getString(3);
            tasks = new Tasks(Find_id, Find_task, Find_date,Find_time);
        }
        cursor.close();
        return tasks;
    }

    //Savindri


    //CHAMIKA METHODS

    public void addAppointments(Appointments appointments){
        ContentValues values = new ContentValues();
        values.put(COL_CLIENT, appointments.getClient());
        values.put(COL_VENUE, appointments.getVenue());
        values.put(COL_DATE, appointments.getDate());
        values.put(COL_TIME, appointments.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TAB_APPOINTMENTS, null, values);
    }

    public ArrayList<Appointments> listAppointments(){
        String sql = "select * from " + TAB_APPOINTMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Appointments> storeAppointments = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String client1 = cursor.getString(1);
                String venue1 = cursor.getString(2);
                String date1 = cursor.getString(3);
                String time1 = cursor.getString(4);
                storeAppointments.add(new Appointments(id, client1,venue1,date1,time1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeAppointments;
    }
    public void updateAppointment(Appointments task){
        ContentValues values = new ContentValues();
        values.put(COL_CLIENT, task.getClient());
        values.put(COL_VENUE, task.getVenue());
        values.put(COL_DATE, task.getDate());
        values.put(COL_TIME, task.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TAB_APPOINTMENTS, values, COL_ID	+ "	= ?", new String[] { String.valueOf(task.getId1())});
    }

    public void deleteAppointments(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TAB_APPOINTMENTS, COL_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }

    public Appointments SearchApps(String task){
        String query = "Select * FROM "	+ TAB_APPOINTMENTS + " WHERE " + COL_CLIENT + " = " + "task";
        SQLiteDatabase db = this.getWritableDatabase();
        Appointments appointments = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int Find_id = Integer.parseInt(cursor.getString(0));
            String search_client = cursor.getString(1);
            String search_venue = cursor.getString(2);
            String search_date = cursor.getString(3);
            String search_time = cursor.getString(4);
            appointments = new Appointments(Find_id, search_client, search_venue,search_date,search_time);
        }
        cursor.close();
        return appointments;
    }



}
