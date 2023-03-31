package com.sig.baubau.UI;

import java.util.ArrayList;

public class KMedoid {
    private float[][] centroid;
    private float[][] distance;
    private float[][] CDistance;
    private ArrayList<String> tidak;
    private ArrayList<String> rendah;
    private ArrayList<String> sedang;
    private ArrayList<String> tinggi;
    private ArrayList acak;

    public KMedoid(){}

    public KMedoid(float[][] centroid, float[][] distance, float[][] CDistance,ArrayList<String> tidak,
                   ArrayList<String> rendah, ArrayList<String> sedang, ArrayList<String> tinggi, ArrayList acak){
        this.centroid = centroid;
        this.distance = distance;
        this.CDistance = CDistance;
        this.tidak = tidak;
        this.rendah = rendah;
        this.sedang = sedang;
        this.tinggi = tinggi;
        this.acak = acak;
    }

    public float[][] getCentroid(){
        return centroid;
    }

    public void setCentroid(float[][] centroid){
        this.centroid = centroid;
    }

    public float[][] getDistance(){
        return distance;
    }

    public void setDistance(float[][] distance){
        this.distance = distance;
    }

    public float[][] getCDistance(){
        return CDistance;
    }

    public void setCDistance(float[][] CDistance){
        this.CDistance = CDistance;
    }

    public ArrayList<String> getTidak(){
        return tidak;
    }

    public void setTidak(ArrayList<String> tidak){
        this.tidak = tidak;
    }

    public ArrayList<String> getRendah(){
        return rendah;
    }

    public void setRendah(ArrayList<String> rendah){
        this.rendah = rendah;
    }

    public ArrayList<String> getSedang(){
        return sedang;
    }

    public void setSedang(ArrayList<String> sedang){
        this.sedang = sedang;
    }

    public ArrayList<String> getTinggik(){
        return tinggi;
    }

    public void setTinggi(ArrayList<String> tinggi){
        this.tinggi = tinggi;
    }

    public ArrayList getAcak() {
        return acak;
    }

    public void setAcak(ArrayList acak) {
        this.acak = acak;
    }
}
