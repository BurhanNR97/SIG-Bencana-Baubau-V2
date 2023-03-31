package com.sig.baubau.kelas;

public class cMatriks {
    private String kecamatan;
    private String aman;
    private String rendah;
    private String sedang;
    private String tinggi;

    public cMatriks(String kecamatan, String aman, String rendah, String sedang, String tinggi) {
        this.kecamatan = kecamatan;
        this.aman = aman;
        this.rendah = rendah;
        this.sedang = sedang;
        this.tinggi = tinggi;
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

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }
}
