package com.example.rahul.mygrocerylist.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rahul.mygrocerylist.Model.Grocery;
import com.example.rahul.mygrocerylist.Util.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;
    public DatabaseHandler(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constant.TABLE_NAME + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY," + Constant.KEY_GROCERY_ITEM + " TEXT,"
                + Constant.KEY_QTY_NUMBER + " TEXT,"
                + Constant.KEY_DATE_NAME + " LONG);";

        db.execSQL(CREATE_GROCERY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);

        onCreate(db);

    }

    /**
     *  CRUD OPERATIONS: Create, Read, Update, Delete Methods
     */

    //Add Grocery
    public void addGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constant.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constant.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //Insert the row
        db.insert(Constant.TABLE_NAME, null, values);

        Log.d("Saved!!", "Saved to DB");

    }


    //Get a Grocery
    public Grocery getGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constant.TABLE_NAME, new String[] {Constant.KEY_ID,
                        Constant.KEY_GROCERY_ITEM, Constant.KEY_QTY_NUMBER, Constant.KEY_DATE_NAME},
                Constant.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Grocery grocery = new Grocery();
        grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
        grocery.setName(cursor.getString(cursor.getColumnIndex(Constant.KEY_GROCERY_ITEM)));
        grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constant.KEY_QTY_NUMBER)));

        //convert timestamp to something readable
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATE_NAME)))
                .getTime());

        grocery.setDateItemAdded(formatedDate);



        return grocery;
    }


    //Get all Groceries
    public List<Grocery> getAllGroceries() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constant.TABLE_NAME, new String[] {
                Constant.KEY_ID, Constant.KEY_GROCERY_ITEM, Constant.KEY_QTY_NUMBER,
                Constant.KEY_DATE_NAME}, null, null, null, null, Constant.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constant.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constant.KEY_QTY_NUMBER)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATE_NAME)))
                        .getTime());

                grocery.setDateItemAdded(formatedDate);

                // Add to the groceryList
                groceryList.add(grocery);

            }while (cursor.moveToNext());
        }

        return groceryList;
    }


    //Updated Grocery
    public int updateGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constant.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constant.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//get system time


        //update row
        return db.update(Constant.TABLE_NAME, values, Constant.KEY_ID + "=?", new String[] { String.valueOf(grocery.getId())} );
    }


    //Delete Grocery
    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.TABLE_NAME, Constant.KEY_ID + " = ?",
                new String[] {String.valueOf(id)});

        db.close();

    }


    //Get count
    public int getGroceriesCount() {
        String countQuery = "SELECT * FROM " + Constant.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
