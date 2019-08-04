package com.ktm.thumb_first;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "ThumbDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableDiaryListQuery = "create table Diary(_id INTEGER primary key AUTOINCREMENT, date TEXT, time TEXT,content TEXT) ";
        db.execSQL(tableDiaryListQuery);

        String tableShoppingListQuery = "create table ShoppingList(_id INTEGER primary key AUTOINCREMENT, item TEXT, quantity TEXT, date TEXT, isBought INTEGER default 0) ";
        db.execSQL(tableShoppingListQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
