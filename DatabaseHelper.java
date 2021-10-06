package com.example.careapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String CREATE_TABLE;
    private Context _context;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        CREATE_TABLE = "create table Schedule (" + "id integer primary key autoincrement, "+
        "date text, " + "time text, " + "location text, "+"title text, "+"description text)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
