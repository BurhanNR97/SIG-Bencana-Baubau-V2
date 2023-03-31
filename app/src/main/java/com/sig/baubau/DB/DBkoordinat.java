package com.sig.baubau.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBkoordinat extends SQLiteOpenHelper {

    public static final String database_name = "DBkec9000.db";
    public static final String table_name = "koordinat";
    public static final String row_id = "_id";
    public static final String row_kdKec = "kd_kec";
    public static final String row_nmKec = "nm_kec";
    public static final String row_lat = "latitude";
    public static final String row_long = "longitude";

    private SQLiteDatabase db;

    public DBkoordinat(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdKec + " TEXT,"
                + row_nmKec + " TEXT, " + row_lat + " TEXT DEFAULT NULL, " + row_long + " TEXT DEFAULT NULL)";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdKec + ", " + row_nmKec + ", " + row_lat + ", " + row_long +") VALUES "
                + "('747201', 'Betoambari', '-5.5042541','122.593828'),"
                + "('747202', 'Wolio', '-5.4834605','122.6390489'),"
                + "('747203', 'Sorawolio', '-5.47185349278','122.594597982'),"
                + "('747204', 'Bungi', '-5.3750761','122.6271032'),"
                + "('747205', 'Kokalukuna','-5.4478301','122.6002575'),"
                + "('747206', 'Murhum', '-5.4779475','122.5846039'),"
                + "('747207', 'Lea-Lea', '-5.3614271','122.5795187'),"
                + "('747208', 'Batupoaro', '-5.4609108','122.5585747')";
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

    public Cursor oneData(String id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_kdKec + "=" + id, null);
        return cur;
    }

    public Cursor namaKec(String kode){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_kdKec + "=" + kode, null);
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

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }

    public Cursor id(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " DESC", null);
        return cur;
    }
}
