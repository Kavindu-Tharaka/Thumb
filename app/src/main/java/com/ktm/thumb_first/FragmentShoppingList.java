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
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;


public class FragmentShoppingList extends Fragment implements View.OnClickListener {

    View view;
    ListView listView;
    Button edit, delete, bought;
    FloatingActionButton floatingActionButton;

    public FragmentShoppingList() {
    }

    @Override
    public void onResume() {
        super.onResume();

        //listView = findViewById(R.id.shopping_list); //access the listView which is ID = "shopping_list"
        //List<String> list = new ArrayList<>();

        DatabaseHelper mydb = new DatabaseHelper(getActivity());
        SQLiteDatabase db = mydb.getReadableDatabase();

        String query = "select * from ShoppingList where isBought = 0";
        Cursor cursor = db.rawQuery(query,null);   //Cursor -> resultSet , rawQuery -> executeQuery()
        cursor.moveToFirst();

/*        while (cursor.isAfterLast() == false){
            String item = cursor.getString(1);
            list.add(item);  //the name which add to list
            cursor.moveToNext();  //point to next line
        }*/

        int layout = R.layout.one_element_shopping_list;  //choose layout created manually
        String[] columns = {"item","quantity","_id","date"};  //get values from table columns
        int[] views = {R.id.item_name_shopping_list,R.id.item_qty_shopping_list,R.id.item_id_shopping_list,R.id.created_date_shopping_list}; //IDs of fields that table data will be mapped

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),layout,cursor,columns,views);
        listView.setAdapter(simpleCursorAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_basic,container,false);

        listView = view.findViewById(R.id.shopping_list);
        floatingActionButton = view.findViewById(R.id.fab1);
        edit = view.findViewById(R.id.edit_shopping_list);
        delete = view.findViewById(R.id.delete_shopping_list);
        bought = view.findViewById(R.id.bought_shopping_list);

        floatingActionButton.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_shopping_list_add, null);
                final EditText item = view.findViewById(R.id.item_name_save);
                final EditText qty = view.findViewById(R.id.quantity_save);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Enter New Item & Quantity")
                        .setView(view)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //getText from edit text components
                                String itemName = item.getText().toString();
                                String quantity = qty.getText().toString();

                                //get current date
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String currentDate = simpleDateFormat.format(c.getTime());

                                if (itemName.isEmpty() || quantity.isEmpty()) {    //if no details are entered, error msg will be displayed
                                    Toasty.info(getActivity(), "Enter details before save!", Toast.LENGTH_SHORT).show();
                                } else {
                                    //save data
                                    DatabaseHelper myDB = new DatabaseHelper(getActivity());
                                    SQLiteDatabase db = myDB.getWritableDatabase();  //WritableDatabase

                                    String query = "insert into ShoppingList(item, quantity, date) values ( '" + itemName + "', '" + quantity + "', '" + currentDate + "' ) ";
                                    db.execSQL(query);

                                    Toasty.success(getActivity(), "Saved Successfully!", Toast.LENGTH_SHORT).show();

                                    //in order to refresh the list
                                    onResume();


                                }
                            }
                        })
                        .setNegativeButton("Cancel", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
    }

}
