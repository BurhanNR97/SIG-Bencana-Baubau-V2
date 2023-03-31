package com.sig.baubau.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PoskoDB extends SQLiteOpenHelper {

    public static final String database_name = "opeddddd.db";
    public static final String table_name = "poskopengungsian";
    public static final String row_id = "_id";
    public static final String row_nama = "nama_posko";
    public static final String row_alamat = "alamat";
    public static final String row_lat = "latitude";
    public static final String row_lng = "longitude";
    public static final String row_jarak = "jarak";

    private SQLiteDatabase db;

    public PoskoDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_nama + " TEXT,"
                + row_lat + " TEXT DEFAULT NULL, " + row_lng + " TEXT DEFAULT NULL, " + row_jarak + " INTEGER DEFAULT 0)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_jarak + " ASC ", new String[]{});
        return cur;
    }


    public Cursor oneData(long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }
}
