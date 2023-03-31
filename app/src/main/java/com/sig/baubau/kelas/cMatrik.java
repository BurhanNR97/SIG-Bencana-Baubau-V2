package com.sig.baubau.kelas;

public class cMatrik {
    private String kecamatan;
    private String aman;
    private String rendah;
    private String sedang;

    public cMatrik(String kecamatan, String aman, String rendah, String sedang) {
        this.kecamatan = kecamatan;
        this.aman = aman;
        this.rendah = rendah;
        this.sedang = sedang;
    }

    public String getKec() {
        return kecamatan;
    }

    public void setKec(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getAman() {
        return aman;
    }

    public void setAman(String aman) {
        this.aman = aman;
    }

    public String getRendah() {
        return rendah;
    }

    public void setSedang(String sedang) {
        this.sedang = sedang;
    }

    public String getSedang() {
        return sedang;
    }

    public void setRendah(String rendah) {
        this.rendah = rendah;
    }
}
