package com.coinnpursecoding.maggieslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;


public class mySQLiteDBHandler extends SQLiteOpenHelper {

    //public static int VERSION = 1;
    private static final String TAG = "DatabaseHelper";
    public static String TABLE_NAME = "MAGGIES_LISTS";
    public static String COL1 = "subject";
    public static String COL2 = "description";


    public mySQLiteDBHandler(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " VARCHAR, " + COL2 +
                " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* POSSIBLY SEVERELY OVER-ENGINEERED CODE BELOW */

    public boolean addData(String listName, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, listName);
        contentValues.put(COL2, description);

        Log.i(TAG, "Adding " + listName + " into " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean findList(String listName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        boolean success = false;
        data.moveToFirst();

        for(int i = 0; i < data.getCount(); i++) {
            if (data.getString(data.getColumnIndex(COL1)).equals(listName)) {
                success = true;
            } else {
                data.moveToNext();
            }
        }

        data.close();
        return success;
    }

    public int getDatabaseSize(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        Log.i("# of lists in app", Integer.toString(data.getCount()));
        data.close();

        return data.getCount();
    }


    public void fillArrayList(ArrayList<String> leest){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        // If database isn't empty and array isn't full
        if (data.getCount() != 0 && leest.size() != data.getCount()) {
            data.moveToFirst();

            do {
                String columnName = data.getString(data.getColumnIndex(COL1));

                //String entry = data.getString(data.getColumnIndex(column_name));
                leest.add(columnName);
            } while (data.moveToNext());

            data.close();
        }
    }

    public String fillViewList(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL1 + " ASC";
        Cursor data = db.rawQuery(query, null);

        data.moveToFirst();

        String entry = "";


        for (int i = 0; i < data.getCount(); i++){
            if (data.getString(data.getColumnIndex(COL1)).equals(title)){
                entry = data.getString(data.getColumnIndex(COL2));
            }
            data.moveToNext();
        }
        data.close();

        Log.i("description", entry);
        return entry;
    }



    public boolean removeData(String listName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL1 + " ASC";
        Cursor data = db.rawQuery(query, null);

        boolean success = false;
        data.moveToFirst();

        do {
            if (data.getString(data.getColumnIndex(COL1)).equals(listName)){
                db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE "+ COL1 + "='"+
                        listName +"'");
                success = true;
            }
        } while (data.moveToNext());

        data.close();
        return success;
    }
}