package com.sig.baubau.Adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.DB.LongsorDB;
import com.sig.baubau.R;
import com.sig.baubau.UI.BanjirActivity;
import com.sig.baubau.UI.LongsorActivity;

public class AdpLongsor extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdpLongsor(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_longsor, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ly = (LinearLayout) v.findViewById(R.id.indeksAncaman1);
        holder.ListIndeks = (TextView) v.findViewById(R.id.tvNoLongsor);
        holder.ListID = (TextView)v.findViewById(R.id.tvIDLongsor);
        holder.ListKec = (TextView)v.findViewById(R.id.tvKecLongsor);
        holder.ListTidak = (TextView)v.findViewById(R.id.tvTidakLongsor);
        holder.ListRendah = (TextView)v.findViewById(R.id.tvRendahLongsor);
        holder.ListSedang = (TextView)v.findViewById(R.id.tvSedangLongsor);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();
        int n = 1;
        KecDB db = new KecDB((LongsorActivity) context);
        Cursor cur = db.namaKec(cursor.getString(cursor.getColumnIndex(LongsorDB.row_kdKec)));
        cur.moveToFirst();
        String nn = cursor.getString(cursor.getColumnIndex(LongsorDB.row_ket)).trim();
        if (nn.equals("0")){
            holder.ly.setBackgroundResource(R.color.green);
        } else
        if (nn.equals("1")){
            holder.ly.setBackgroundResource(R.color.kuning);
        } else
        if (nn.equals("2")){
            holder.ly.setBackgroundResource(R.color.orange);
        }

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(LongsorDB.row_id)));
        holder.ListKec.setText(cur.getString(cur.getColumnIndex(KecDB.row_nmKec)));
        holder.ListTidak.setText("Indeks Aman: "+cursor.getString(cursor.getColumnIndex(LongsorDB.row_tidak)));
        holder.ListRendah.setText("Indeks Rendah: "+cursor.getString(cursor.getColumnIndex(LongsorDB.row_rendah)));
        holder.ListSedang.setText("Indeks Sedang: "+cursor.getString(cursor.getColumnIndex(LongsorDB.row_sedang)));
    }

    class MyHolder{
        LinearLayout ly;
        TextView ListIndeks;
        TextView ListID;
        TextView ListKec;
        TextView ListTidak;
        TextView ListRendah;
        TextView ListSedang;

    }
}
