package com.ktm.thumb_first;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class FragmentBoughtList extends Fragment implements View.OnClickListener {

    View view;
    ListView listView;
    ImageButton search;
    EditText searchText;

    FloatingActionButton floatingActionButton;

    public FragmentBoughtList() {
    }

    @Override
    public void onResume() {
        super.onResume();

        DatabaseHelper mydb = new DatabaseHelper(getActivity());
        SQLiteDatabase db = mydb.getReadableDatabase();

        String query = "select * from " + ThumbMaster.ShoppingList.TABLE_NAME + " where " + ThumbMaster.ShoppingList.COLUMN_NAME_ISBOUGHT + " = 1";
        Cursor cursor = db.rawQuery(query, null);   //Cursor -> resultSet , rawQuery -> executeQuery()
        cursor.moveToFirst();


        int layout = R.layout.one_element_bought_list;  //choose layout created manually
        String[] columns = {"item", "quantity", "_id", "date"};  //get values from table columns
        int[] views = {R.id.item_name_bought_list, R.id.qty_bought_list, R.id.item_id_bought_list, R.id.created_date_bought_list}; //IDs of fields that table data will be mapped

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), layout, cursor, columns, views);
        listView.setAdapter(simpleCursorAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_basic2, container, false);

        listView = view.findViewById(R.id.bought_list); //access the listView which is ID = "bought_list"
        floatingActionButton = view.findViewById(R.id.fab2);
        search = view.findViewById(R.id.searchBtnBoughtList);
        searchText = view.findViewById(R.id.searchTextBoughtList);

        search.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab2:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Do you want to delete all?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                DatabaseHelper myDB = new DatabaseHelper(getActivity());
                                SQLiteDatabase db = myDB.getWritableDatabase();
                                String sqlQuery = "delete from " + ThumbMaster.ShoppingList.TABLE_NAME + " where " + ThumbMaster.ShoppingList.COLUMN_NAME_ISBOUGHT + " = 1";
                                db.execSQL(sqlQuery);

                                Toasty.success(getActivity(), "Deleted!", Toast.LENGTH_SHORT).show();

                                //to refresh
                                onResume();
                            }
                        })
                        .setNegativeButton("No", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;


            case R.id.searchBtnBoughtList:

                String searchQueary = searchText.getText().toString();

                DatabaseHelper mydb = new DatabaseHelper(getActivity());
                SQLiteDatabase db = mydb.getReadableDatabase();

                String query = "select * from " + ThumbMaster.ShoppingList.TABLE_NAME + " where " + ThumbMaster.ShoppingList.COLUMN_NAME_ISBOUGHT + " = 1 AND " + ThumbMaster.ShoppingList.COLUMN_NAME_ITEM + " LIKE  '%"+searchQueary+"%'  ";
                Cursor cursor = db.rawQuery(query, null);   //Cursor -> resultSet , rawQuery -> executeQuery()
                cursor.moveToFirst();


                int layout = R.layout.one_element_shopping_list;  //choose layout created manually
                String[] columns = {"item", "quantity", "_id", "date"};  //get values from table columns
                int[] views = {R.id.item_name_shopping_list, R.id.item_qty_shopping_list, R.id.item_id_shopping_list, R.id.created_date_shopping_list}; //IDs of fields that table data will be mapped

                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), layout, cursor, columns, views);
                listView.setAdapter(simpleCursorAdapter);


                break;


        }
    }
}