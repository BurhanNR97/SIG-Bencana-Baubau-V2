package com.sig.baubau.Adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;
import com.sig.baubau.UI.BanjirActivity;

public class AdpBanjir extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdpBanjir(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_banjir, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ly = (LinearLayout) v.findViewById(R.id.indeksAncaman);
        holder.ListIndeks = (TextView) v.findViewById(R.id.tvNoBanjir0);
        holder.ListID = (TextView)v.findViewById(R.id.tvIDBanjir0);
        holder.ListKec = (TextView)v.findViewById(R.id.tvKecBanji0);
        holder.ListTidak = (TextView)v.findViewById(R.id.tvTidakBanjir0);
        holder.ListRendah = (TextView)v.findViewById(R.id.tvRendahBanjir0);
        holder.ListSedang = (TextView)v.findViewById(R.id.tvSedangBanjir0);
        holder.ListTinggi = (TextView)v.findViewById(R.id.tvTinggiBanjir0);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();
        int n = 1;
        KecDB db = new KecDB((BanjirActivity) context);
        Cursor cur = db.namaKec(cursor.getString(cursor.getColumnIndex(BanjirDB.row_kdKec)));
        cur.moveToFirst();
        String nn = cursor.getString(cursor.getColumnIndex(BanjirDB.row_ket)).trim();
        if (nn.equals("0")){
            holder.ly.setBackgroundResource(R.color.green);
        } else
        if (nn.equals("1")){
            holder.ly.setBackgroundResource(R.color.kuning);
        } else
        if (nn.equals("2")){
            holder.ly.setBackgroundResource(R.color.orange);
        } else
        if (nn.equals("3")){
            holder.ly.setBackgroundResource(R.color.red);
        }
        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(BanjirDB.row_id)));
        holder.ListKec.setText(cur.getString(cur.getColumnIndex(KecDB.row_nmKec)));
        holder.ListTidak.setText("Indeks Aman: "+cursor.getString(cursor.getColumnIndex(BanjirDB.row_tidak)));
        holder.ListRendah.setText("Indeks Rendah: "+cursor.getString(cursor.getColumnIndex(BanjirDB.row_rendah)));
        holder.ListSedang.setText("Indeks Sedang: "+cursor.getString(cursor.getColumnIndex(BanjirDB.row_sedang)));
        holder.ListTinggi.setText("Indeks Tinggi: "+cursor.getString(cursor.getColumnIndex(BanjirDB.row_tinggi)));
    }

    class MyHolder{
        LinearLayout ly;
        TextView ListIndeks;
        TextView ListID;
        TextView ListKec;
        TextView ListTidak;
        TextView ListRendah;
        TextView ListSedang;
        TextView ListTinggi;

    }
}
