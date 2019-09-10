package com.ktm.thumb_first;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ShoppingListActivity extends AppCompatActivity {

    TabLayout tabLayout;
    //AppBarLayout appBarLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        tabLayout = findViewById(R.id.tab_layout_id);
        //appBarLayout = findViewById(R.id.app_bar_id);
        viewPager = findViewById(R.id.viewpager_id);

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentShoppingList(),"Shopping List");
        adapter.AddFragment(new FragmentBoughtList(),"Bought List");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void editItem(View v){

        LinearLayout parent1 = (LinearLayout) v.getParent();
        LinearLayout parent2 = (LinearLayout) parent1.getParent();

        TextView tv1 = parent1.findViewById(R.id.item_id_shopping_list);
        TextView tv2 = parent2.findViewById(R.id.item_name_shopping_list);
        TextView tv3 = parent2.findViewById(R.id.item_qty_shopping_list);

        String id = tv1.getText().toString();
        String itemName = tv2.getText().toString();
        String qty = tv3.getText().toString();

        Intent intent = new Intent(this, ShoppingListUpdateActivity.class);

        intent.putExtra("ID", id); //pass  parametrs to next activity which start by start intent
        intent.putExtra("ITEM", itemName); //pass  parametrs to next activity which start by start intent
        intent.putExtra("QTY", qty); //pass  parametrs to next activity which start by start intent

        finish(); //to avoid many backs
        startActivity(intent);
    }

    public void deleteItemShoppingList(View v){

        final View view = v;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to delete item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LinearLayout parent = (LinearLayout) view.getParent();
                        TextView tv = parent.findViewById(R.id.item_id_shopping_list);
                        String idTemp = tv.getText().toString();
                        int id = Integer.parseInt(idTemp);

                        DatabaseHelper myDB = new DatabaseHelper(ShoppingListActivity.this);
                        SQLiteDatabase db = myDB.getWritableDatabase();
                        String sqlQuery = "delete from "+ThumbMaster.ShoppingList.TABLE_NAME+" where "+ThumbMaster.ShoppingList.COLUMN_NAME_ID+" = " + id+ "";
                        db.execSQL(sqlQuery);

                        Toasty.success(ShoppingListActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();

                        //to refresh
                        finish();
                        startActivity(getIntent());
                    }
                })
                .setNegativeButton("No",null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void boughtItem(View v){

        LinearLayout parent = (LinearLayout) v.getParent();
        TextView tv = parent.findViewById(R.id.item_id_shopping_list);
        String idTemp = tv.getText().toString();
        int id = Integer.parseInt(idTemp);

        //save data to db
        DatabaseHelper myDB = new DatabaseHelper(this);
        SQLiteDatabase db = myDB.getWritableDatabase();  //WritableDatabase

        String sqlQuery = "UPDATE "+ThumbMaster.ShoppingList.TABLE_NAME+" SET "+ThumbMaster.ShoppingList.COLUMN_NAME_ISBOUGHT+" = 1 WHERE "+ThumbMaster.ShoppingList.COLUMN_NAME_ID+" = " + id + "";
        db.execSQL(sqlQuery);

        Toasty.success(this, "Bought!", Toast.LENGTH_SHORT).show();

        //to refresh
        finish();
        startActivity(getIntent());
    }

    public void UnBoughtItem(View v){
        LinearLayout parent = (LinearLayout) v.getParent();
        TextView tv = parent.findViewById(R.id.item_id_bought_list);
        String idTemp = tv.getText().toString();
        int id = Integer.parseInt(idTemp);

        //save data to db
        DatabaseHelper myDB = new DatabaseHelper(this);
        SQLiteDatabase db = myDB.getWritableDatabase();  //WritableDatabase

        String sqlQuery = "UPDATE "+ThumbMaster.ShoppingList.TABLE_NAME+" SET "+ThumbMaster.ShoppingList.COLUMN_NAME_ISBOUGHT+" = 0 WHERE "+ThumbMaster.ShoppingList.COLUMN_NAME_ID+" = " + id + "";
        db.execSQL(sqlQuery);

        Toasty.success(this, "Made Unbought!", Toast.LENGTH_SHORT).show();

        //to refresh
        finish();
        startActivity(getIntent());

    }

    public void deleteItemBoughtList(View v){

        final View view = v;

        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this);

        builder.setMessage("Do you want to delete item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LinearLayout parent = (LinearLayout) view.getParent();
                        TextView tv = parent.findViewById(R.id.item_id_bought_list);
                        String idTemp = tv.getText().toString();
                        int id = Integer.parseInt(idTemp);

                        DatabaseHelper myDB = new DatabaseHelper(ShoppingListActivity.this);
                        SQLiteDatabase db = myDB.getWritableDatabase();
                        String sqlQuery = "delete from "+ThumbMaster.ShoppingList.TABLE_NAME+" where "+ThumbMaster.ShoppingList.COLUMN_NAME_ID+" = " + id+ "";
                        db.execSQL(sqlQuery);

                        Toasty.success(ShoppingListActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();

                        //to refresh
                        finish();
                        startActivity(getIntent());

                    }
                })
                .setNegativeButton("No",null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }




}
