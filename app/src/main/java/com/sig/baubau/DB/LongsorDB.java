package com.sig.baubau.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LongsorDB extends SQLiteOpenHelper {

    public static final String database_name = "openfffffind.db";
    public static final String table_name = "longsorr";
    public static final String row_id = "_id";
    public static final String row_kdKec = "kd_kec";
    public static final String row_ket = "ket";
    public static final String row_tidak = "tidak_berpotensi";
    public static final String row_rendah = "rendah";
    public static final String row_sedang = "sedang";

    private SQLiteDatabase db;

    public LongsorDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdKec + " TEXT,"+ row_ket + " TEXT,"
                + row_tidak + " DOUBLE DEFAULT 0.0, " + row_rendah + " DOUBLE DEFAULT 0.0, " + row_sedang + " DOUBLE DEFAULT 0.0)";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdKec + ", " + row_ket+ ", " + row_tidak + ", " + row_rendah +", "
                + row_sedang +") VALUES "
                + "('747201','0',3206.83,112.98,0.0)," +
                "('747202','0',2784.22,490.39,0.0)," +
                "('747203','0',8716.11,2900.48,1035.70)," +
                "('747204','0',5298.58,1030.49,39.74)," +
                "('747205','0',1727.0,213.19,0.0)," +
                "('747206','0',466.85,34.36,0.0)," +
                "('747207','0',3093.32,296.90,0.0)," +
                "('747208','0',205.05,0.0,0.0)";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", new String[]{});
        return cur;
    }

    public Cursor allData0(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_ket + "= '0'", null);
        return cur;
    }

    public Cursor allData1(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_ket + "= '1'", null);
        return cur;
    }

    public boolean cekKode(String kode) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + row_kdKec + " FROM " + table_name + " WHERE " + row_kdKec +"= ?",new String[] {kode});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor allData2(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_ket + "= '2'", null);
        return cur;
    }

    public Cursor allData3(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_ket + "= '3'", null);
        return cur;
    }

    public Cursor oneData(long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    public Cursor dGejala(String kode){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_kdKec + "= ?", new String[] {kode});
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

    public void updateData1(ContentValues values, String id){
        db.update(table_name, values, row_kdKec + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }

    public Cursor id(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " DESC", null);
        return cur;
    }
}
