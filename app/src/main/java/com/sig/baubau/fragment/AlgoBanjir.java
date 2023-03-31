package com.sig.baubau.fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.R;
import com.sig.baubau.UI.KMedoid;
import com.sig.baubau.kelas.cMatriks;
import com.sig.baubau.model.aMatriks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoBanjir extends Fragment {
    String ambilData = "";
    String teks = "";
    BanjirDB db;
    int iterasi = 0;
    int k =4;
    float[][] centroid = null;
    float[][] distance = null;
    ArrayList<String> kumpulKode = new ArrayList<>();
    ArrayList<String> kode = new ArrayList<>();
    ArrayList<Float> tidak = new ArrayList<>();
    ArrayList<Float> rendah = new ArrayList<>();
    ArrayList<Float> sedang = new ArrayList<>();
    ArrayList<Float> tinggi = new ArrayList<>();
    ArrayList<String> Ktidak;
    ArrayList<String> Krendah;
    ArrayList<String> Ksedang;
    ArrayList<String> Ktinggi;
    ArrayList acak = new ArrayList<>();
    ArrayList acakBaru;
    ArrayList finalAcak;
    float[] hasilDistance = null;
    float[][] Cdisntance = null;
    float[][] urut = null;
    String[][] klp = null;
    String[][] urutKlp = null;
    String[][] klpMedoid = null;
    public static ArrayList<KMedoid> KEY_ACTIVITY;

    public AlgoBanjir() {
        // Required empty public constructor
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_algo_banjir, container, false);
        db = new BanjirDB(v.getContext());

        RecyclerView rvMatriks = v.findViewById(R.id.rvMariks);
        aMatriks adapter1 = new aMatriks(getMatriks());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(v.getContext());
        rvMatriks.setLayoutManager(linearLayoutManager1);
        rvMatriks.setAdapter(adapter1);

        Cursor cursor = db.allData();

        if (cursor.moveToFirst()) {
            do {
                String ex = cursor.getString(cursor.getColumnIndex(BanjirDB.row_kdKec));
                kumpulKode.add(ex);
                float a = Float.parseFloat(cursor.getString(cursor.getColumnIndex(BanjirDB.row_tidak)));
                float b = Float.parseFloat(cursor.getString(cursor.getColumnIndex(BanjirDB.row_rendah)));
                float c = Float.parseFloat(cursor.getString(cursor.getColumnIndex(BanjirDB.row_sedang)));
                float d = Float.parseFloat(cursor.getString(cursor.getColumnIndex(BanjirDB.row_tinggi)));
                kode.add(ex);
                tidak.add(a);
                rendah.add(b);
                sedang.add(c);
                tinggi.add(d);
            } while (cursor.moveToNext());
        }

        String[] kk = {"0", "1", "2", "3"};
        klp = new String[tidak.size()][kk.length];
        for (int i=0; i<tidak.size(); i++){
            for (int j=0; j<kk.length; j++){
                klp[i][j] = kk[j];
            }
        }

        float[][] matriks = new float[cursor.getCount()][k];
        for (int i = 0; i < tidak.size(); i++) {
            matriks[i][0] = tidak.get(i);
            matriks[i][1] = rendah.get(i);
            matriks[i][2] = sedang.get(i);
            matriks[i][3] = tinggi.get(i);
        }

        int[] k2 = {4,2,0,5};
        int[] k1 = {3,1,5,4};
        int[] k3 = {0,6,2,7};
        teks+= "ITERASI 1\n";
        kMedoid(matriks, k2);
        teks+= "ITERASI 2\n";
        kMedoid(matriks, k1);
        teks+= "ITERASI 3\n";
        kMedoid(matriks, k3);
        finalkMedoid(matriks, k1);

        TextView tampil = v.findViewById(R.id.tvAlgoalgo);
        tampil.setText(teks);
        return v;
    }

    @SuppressLint("Range")
    private List getMatriks(){
        Cursor cursor = db.allData();
        List<cMatriks> data = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                data.add(new cMatriks(cursor.getString(cursor.getColumnIndex(BanjirDB.row_kdKec)),
                        cursor.getString(cursor.getColumnIndex(BanjirDB.row_tidak)),
                        cursor.getString(cursor.getColumnIndex(BanjirDB.row_rendah)),
                        cursor.getString(cursor.getColumnIndex(BanjirDB.row_sedang)),
                        cursor.getString(cursor.getColumnIndex(BanjirDB.row_tinggi))));
            } while (cursor.moveToNext());
        }
        return data;
    }

    private void kMedoid(float[][] matriks, int[] acaklk){
        //menentukan pusat medoid
        centroid = new float[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                centroid[i][j] = matriks[acaklk[i]][j];
            }
        }

        //menghitung manhattan distance
        distance = new float[tidak.size()][k];
        for (int h = 0; h < k; h++){
            for (int i = 0; i < tidak.size(); i++){
                for (int j = 0; j < k; j++){
                    float m = matriks[i][j] - centroid[h][j];
                    if (m < 0 ){
                        distance[i][j] = m * -1;
                    } else {
                        distance[i][j] = m;
                    }
                }
            }
        }

        //mencari selisih terkecil manhattan distance
        Cdisntance = new float[distance.length][k];
        for (int i = 0; i < distance.length; i++){
            for (int j = 0; j < k; j++){
                Cdisntance[i][j] = matriks[i][j] - distance[i][j];
            }
        }

        //menentukan kelompok medoid
        urut = new float[distance.length][k];
        urutKlp = new String[tidak.size()][k];
        for (int m=0; m<distance.length; m++){
            for (int n=0; n<k; n++){
                urut[m][n]= distance[m][n];
                urutKlp[m][n] = klp[m][n];
            }
        }
        for (int i=0; i<distance.length; i++){
            for (int j=0; j<k; j++){
                for (int y=j; y<k; y++){
                    if (urut[i][y] > urut[i][j]){
                        float tmp = urut[i][j];
                        String tm = urutKlp[i][j];
                        urut[i][j] = urut[i][y];
                        urutKlp[i][j] = klp[i][y];
                        urut[i][y] = tmp;
                        klp[i][y] = tm;
                    }
                }
            }
        }
        klpMedoid = new String[tidak.size()][2];
        for (int i=0; i<tidak.size(); i++){
            klpMedoid[i][0] = kumpulKode.get(i);
            klpMedoid[i][1] = urutKlp[i][3];
        }

        Ktidak = new ArrayList<>();
        Krendah = new ArrayList<>();
        Ksedang = new ArrayList<>();
        Ktinggi = new ArrayList<>();
        Ktidak.clear();
        Krendah.clear();
        Ksedang.clear();
        Ktinggi.clear();
        for (int i=0; i<klpMedoid.length; i++){
            if (klpMedoid[i][1] == "0"){
                Ktidak.add(klpMedoid[i][0]);
            } else
            if (klpMedoid[i][1] == "1"){
                Krendah.add(klpMedoid[i][0]);
            } else
            if (klpMedoid[i][1] == "2"){
                Ksedang.add(klpMedoid[i][0]);
            } else
            if (klpMedoid[i][1] == "3"){
                Ktinggi.add(klpMedoid[i][0]);
            }
        }
        //hasil manhattan distance
        hasilDistance = new float[tidak.size()];
        for (int i=0; i<tidak.size(); i++){
            float tambah = 0;
            for (float num : distance[i]){
                tambah = tambah+num;
                hasilDistance[i] = tambah;
            }
        }

        // total cost manhattan distance
        float cluesterLama = 0;
        for (int i=0; i<hasilDistance.length; i++){
            float tambah = 0;
            for (float num : hasilDistance){
                tambah = tambah+num;
                cluesterLama = tambah;
            }
        }

        teks += "1) Menentukan pusat cluster\n";
        teks += Arrays.deepToString(centroid)+"\n\n";
        teks += "2) Menghitung medoid dengan manhattan distance\n";
        teks += Arrays.deepToString(distance)+"\n\n";
        teks += Arrays.toString(hasilDistance)+"\n\n";
        teks += "Total cost iterasi = "+cluesterLama+"\n";
        teks += "==========================================\n\n";
    }

    private void finalkMedoid(float[][] matriks, int[] acakkk) {
        //menentukan pusat medoid
        centroid = new float[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                centroid[i][j] = matriks[acakkk[i]][j];
            }
        }

        //menghitung manhattan distance
        distance = new float[tidak.size()][k];
        for (int h = 0; h < k; h++) {
            for (int i = 0; i < tidak.size(); i++) {
                for (int j = 0; j < k; j++) {
                    float m = matriks[i][j] - centroid[h][j];
                    if (m < 0) {
                        distance[i][j] = m * -1;
                    } else {
                        distance[i][j] = m;
                    }
                }
            }
        }

        //mencari selisih terkecil manhattan distance
        Cdisntance = new float[distance.length][k];
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < k; j++) {
                Cdisntance[i][j] = matriks[i][j] - distance[i][j];
            }
        }

        //menentukan kelompok medoid
        urut = new float[distance.length][k];
        urutKlp = new String[tidak.size()][k];
        for (int m = 0; m < distance.length; m++) {
            for (int n = 0; n < k; n++) {
                urut[m][n] = distance[m][n];
                urutKlp[m][n] = klp[m][n];
            }
        }
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < k; j++) {
                for (int y = j; y < k; y++) {
                    if (urut[i][y] > urut[i][j]) {
                        float tmp = urut[i][j];
                        String tm = urutKlp[i][j];
                        urut[i][j] = urut[i][y];
                        urutKlp[i][j] = klp[i][y];
                        urut[i][y] = tmp;
                        klp[i][y] = tm;
                    }
                }
            }
        }
        klpMedoid = new String[tidak.size()][2];
        for (int i = 0; i < tidak.size(); i++) {
            klpMedoid[i][0] = kumpulKode.get(i);
            klpMedoid[i][1] = urutKlp[i][3];
        }

        ContentValues vv = new ContentValues();
        Ktidak = new ArrayList<>();
        Krendah = new ArrayList<>();
        Ksedang = new ArrayList<>();
        Ktinggi = new ArrayList<>();
        for (int i = 0; i < klpMedoid.length; i++) {
            vv.put(BanjirDB.row_ket, String.valueOf(klpMedoid[i][1]));
            db.updateData1(vv, String.valueOf(klpMedoid[i][0]));
            if (klpMedoid[i][1] == "0") {
                Ktidak.add(klpMedoid[i][0]);
            } else if (klpMedoid[i][1] == "1") {
                Krendah.add(klpMedoid[i][0]);
            } else if (klpMedoid[i][1] == "2") {
                Ksedang.add(klpMedoid[i][0]);
            } else if (klpMedoid[i][1] == "3") {
                Ktinggi.add(klpMedoid[i][0]);
            }
        }

        //hasil manhattan distance
        hasilDistance = new float[tidak.size()];
        for (int i = 0; i < tidak.size(); i++) {
            float tambah = 0;
            for (float num : distance[i]) {
                tambah = tambah + num;
                hasilDistance[i] = tambah;
            }
        }

        // total cost manhattan distance
        float cluesterLama = 0;
        for (int i = 0; i < hasilDistance.length; i++) {
            float tambah = 0;
            for (float num : hasilDistance) {
                tambah = tambah + num;
                cluesterLama = tambah;
            }
        }

        teks += "Maka didapat kelompok medoid yang terbuat:\n";
        teks += "K1: Ancaman tidak berpotensi\n";
        teks += "K2: Ancaman rendah\n";
        teks += "K3: Ancaman sedang\n";
        teks += "K4: Ancaman tinggi\n\n";
        teks += "K1 = "+Ktidak.toString()+"\n";
        teks += "K2 = "+Krendah.toString()+"\n";
        teks += "K3 = "+Ksedang.toString()+"\n";
        teks += "K4 = "+Ktinggi.toString()+"\n\n";
    }
}