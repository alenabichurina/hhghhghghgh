package com.example.hhghhghghgh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db_class extends SQLiteOpenHelper {
    public static final String Data = "Maps.db";
    public static final String Table = "Spots";
    public static final String Latitude = "Latitude";
    public static final String Longitude = "Longitude";

    public Db_class(Context context) {
        super(context, Data, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Table +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, Latitude TEXT, Longitude TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ Table);
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase getWritable(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase;
    }
    public Cursor GetData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + Table, null);
        return res;
    }

}
