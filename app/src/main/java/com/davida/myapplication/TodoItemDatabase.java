package com.davida.myapplication;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TodoItemDatabase extends SQLiteOpenHelper {
    private static final int VER = 1 ;
    private static final String TAG = "DatabaseHelper" ;
    private static final String TABLE_NAME = "Item_Table" ;
    private static final String ID = "ID" ;
    private static final String ITEM = "Item" ;

    public TodoItemDatabase(@Nullable Context context) {
        super(context, TABLE_NAME, null, VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME) ;

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1 ;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME ;
        Cursor data = db.rawQuery(query, null);
        return data ;
    }
}
