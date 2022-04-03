package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class GolonganModel {

    @SerializedName("id")
    private int id_golongan;

    @SerializedName("golongan")
    private String golongan;

    @SerializedName("keterangan")
    private String keterangan;

    public GolonganModel(int id_golongan, String golongan, String keterangan) {
        this.id_golongan = id_golongan;
        this.golongan = golongan;
        this.keterangan = keterangan;
    }

    public int getId_golongan() {
        return id_golongan;
    }

    public void setId_golongan(int id_golongan) {
        this.id_golongan = id_golongan;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public String toString() {
        return golongan;
    }
}
