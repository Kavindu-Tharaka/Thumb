package com.ktm.thumb_first;

import android.provider.BaseColumns;

public final class ThumbMaster {

    private ThumbMaster(){}

    public static class ShoppingList implements BaseColumns{

        public static final String TABLE_NAME = "ShoppingList";
        public static final String COLUMN_NAME_ITEM = "item";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_ISBOUGHT = "isBought";
    }

    public static class Diary implements BaseColumns{
        String tableDiaryListQuery = "create table Diary(_id INTEGER primary key AUTOINCREMENT, date TEXT, time TEXT,content TEXT) ";

        public  static final String TABLE_NAME = "Diary";
        public  static final String COLUMN_NAME_DATE = "date";
        public  static final String COLUMN_NAME_TIME= "time";
        public  static final String COLUMN_NAME_CONTENT= "content";

    }

    public static class TodoList implements BaseColumns{


    }

    public static class AppointmestList implements BaseColumns{


    }



}
