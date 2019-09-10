package com.ktm.thumb_first;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ThumbDB.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES_SHOPPING_LIST = " CREATE TABLE " +ThumbMaster.ShoppingList.TABLE_NAME+

                "( " +ThumbMaster.ShoppingList._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ThumbMaster.ShoppingList.COLUMN_NAME_ITEM + "TEXT" +
                ThumbMaster.ShoppingList.COLUMN_NAME_QUANTITY + "TEXT" +
                ThumbMaster.ShoppingList.COLUMN_NAME_DATE + "TEXT" +
                ThumbMaster.ShoppingList.COLUMN_NAME_ISBOUGHT + "INTEGER DEFAULT 0)";
      

        db.execSQL(SQL_CREATE_ENTRIES_SHOPPING_LIST);



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
}
