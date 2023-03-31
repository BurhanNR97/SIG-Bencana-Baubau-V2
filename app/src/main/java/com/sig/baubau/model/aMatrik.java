package com.sig.baubau.model;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sig.baubau.R;
import com.sig.baubau.kelas.cMatrik;
import com.sig.baubau.kelas.cMatriks;

import java.util.List;

public class aMatrik extends RecyclerView.Adapter {

    private List<cMatrik> dataList;

    public aMatrik(List<cMatrik> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.tabel_matrik, parent, false);

        return new RowViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtKecamatan.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtAman.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtRendah.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtSedang.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtKecamatan.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtAman.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtRendah.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtSedang.setTypeface(null, Typeface.BOLD);

            rowViewHolder.txtKecamatan.setText("Kecamatan");
            rowViewHolder.txtAman.setText("Aman");
            rowViewHolder.txtRendah.setText("Rendah");
            rowViewHolder.txtSedang.setText("Sedang");
        } else {
            cMatrik data = dataList.get(rowPos - 1);

            rowViewHolder.txtKecamatan.setBackgroundResource(R.drawable.border_left);
            rowViewHolder.txtAman.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtRendah.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtSedang.setBackgroundResource(R.drawable.border_right_bottom);

            rowViewHolder.txtKecamatan.setText(data.getKec() + "");
            rowViewHolder.txtAman.setText(data.getAman() + "");
            rowViewHolder.txtRendah.setText(data.getRendah() + "");
            rowViewHolder.txtSedang.setText(data.getSedang() + "");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtKecamatan;
        TextView txtAman;
        TextView txtRendah;
        TextView txtSedang;

        RowViewHolder(View itemView) {
            super(itemView);
            txtKecamatan = itemView.findViewById(R.id.tv_kecmtn1);
            txtAman = itemView.findViewById(R.id.tv_indexaman1);
            txtRendah = itemView.findViewById(R.id.tv_indexrendah1);
            txtSedang = itemView.findViewById(R.id.tv_indexsedang1);
        }
    }
}
