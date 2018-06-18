package com.example.karthik.spider3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by karthik on 09-06-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String table_name = "myTable";
    private final static String databse = "myDatabase";
    private final static String Col1 = "_id";
    private final static String Col2 = "Task";

    DatabaseHelper(Context context) {
        super(context, databse, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table_name + " (" + Col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Col2 + "  VARCHAR(225));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROB TABLE IF EXISTS" + table_name);

    }

    public void addData(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, task);
        db.insert(table_name, null, contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("Select * from " + table_name + "", null);
        return data;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, Col1 + "="+ String.valueOf(id),null);
    }
}
