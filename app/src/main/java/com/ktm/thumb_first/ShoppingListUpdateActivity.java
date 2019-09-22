package com.ktm.thumb_first;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ShoppingListUpdateActivity extends AppCompatActivity {

    String itemname, qty, idtemp;
    int id;
    EditText editText, editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_update);

        Intent intent = getIntent();
        itemname = intent.getStringExtra("ITEM");
        qty = intent.getStringExtra("QTY");
        idtemp = intent.getStringExtra("ID");

        id = Integer.parseInt(idtemp);

        editText = findViewById(R.id.item_name_shopping_list_update);
        editText1 = findViewById(R.id.item_qty_shopping_list_update);

        editText.setText(itemname);
        editText1.setText(qty);

    }

    public void clearUpdateShoppingItem(View v){
        editText.setText("");
        editText1.setText("");
    }

    public void updateItem(View v){

        EditText itemNAME = findViewById(R.id.item_name_shopping_list_update);
        EditText itemQTY = findViewById(R.id.item_qty_shopping_list_update);

        String itemName  = itemNAME.getText().toString();
        String itemQty = itemQTY.getText().toString();

        if (itemName.isEmpty() || itemQty.isEmpty()) {    //if no details are entered, error msg will be displayed
            //Toasty.info(this, "Enter details before updated!", Toast.LENGTH_SHORT).show();
            Snackbar.make(v, "Enter details before save!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        } else {

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();


            String query = "update " + ThumbMaster.ShoppingList.TABLE_NAME + " set " + ThumbMaster.ShoppingList.COLUMN_NAME_ITEM + " = '" + itemName + "', " + ThumbMaster.ShoppingList.COLUMN_NAME_QUANTITY + " = '" + itemQty + "' where " + ThumbMaster.ShoppingList._ID + " = " + id + "";

            String query = "update " + ThumbMaster.ShoppingList.TABLE_NAME + " set " + ThumbMaster.ShoppingList.COLUMN_NAME_ITEM + " = '" + itemName + "', " + ThumbMaster.ShoppingList.COLUMN_NAME_QUANTITY + " = '" + itemQty + "' where " + ThumbMaster.ShoppingList._ID+ " = " + id + "";


            db.execSQL(query);

            Toasty.success(this, "Updated successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,ShoppingListActivity.class);

            finish();

            startActivity(intent);


        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ShoppingListActivity.class);

        finish();

        startActivity(intent);
    }
}
