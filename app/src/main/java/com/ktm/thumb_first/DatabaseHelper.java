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
    private static final String TABLE_TASKS = "todo_table";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK = "task_col";
    private static final String COLUMN_DATE = "date_col";
    private static final String COLUMN_TIME = "time_col";
    //
    //
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


        db.execSQL(SQL_CREATE_ENTRIES_DIARY);*/
        //savindri
        String	CREATE_TASK_TABLE = "CREATE	TABLE " + TABLE_TASKS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_TASK + "  TEXT, " + COLUMN_DATE + " TEXT, "+COLUMN_TIME+" TEXT" + ")";
        db.execSQL(CREATE_TASK_TABLE);
        //savindri

        String SQL_CREATE_ENTRIES_DIARY = "create table "+ ThumbMaster.Diary.TABLE_NAME +
                "("+ ThumbMaster.Diary._ID +" INTEGER primary key AUTOINCREMENT, "+
                ThumbMaster.Diary.COLUMN_NAME_DATE +" TEXT, "+
                ThumbMaster.Diary.COLUMN_NAME_TIME +" TEXT,"+
                ThumbMaster.Diary.COLUMN_NAME_CONTENT+" TEXT) ";

        db.execSQL(SQL_CREATE_ENTRIES_DIARY);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Savindri

    public void addTasks(Tasks tasks){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, tasks.getTask());
        values.put(COLUMN_DATE, tasks.getDate());
        values.put(COLUMN_TIME, tasks.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
    }

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

    public void deleteTasks(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }

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



}
