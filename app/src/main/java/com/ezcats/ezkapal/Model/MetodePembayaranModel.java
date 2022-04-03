package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class MetodePembayaranModel {

    @SerializedName("id")
    private int id;

    @SerializedName("metode")
    private String metode;

    @SerializedName("nama_metode")
    private String nama_metode;

    @SerializedName("deskripsi_metode")
    private String deskripsi_metode;

    @SerializedName("nomor_rekening")
    private String nomor_rekening;

    public MetodePembayaranModel(int id, String metode, String nama_metode, String deskripsi_metode, String nomor_rekening) {
        this.id = id;
        this.metode = metode;
        this.nama_metode = nama_metode;
        this.deskripsi_metode = deskripsi_metode;
        this.nomor_rekening = nomor_rekening;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public String getNama_metode() {
        return nama_metode;
    }

    public void setNama_metode(String nama_metode) {
        this.nama_metode = nama_metode;
    }

    public String getDeskripsi_metode() {
        return deskripsi_metode;
    }

    public void setDeskripsi_metode(String deskripsi_metode) {
        this.deskripsi_metode = deskripsi_metode;
    }

    public String getNomor_rekening() {
        return nomor_rekening;
    }

    public void setNomor_rekening(String nomor_rekening) {
        this.nomor_rekening = nomor_rekening;
    }

    @Override
    public String toString() {
        return "MetodePembayaranModel{" +
                "id=" + id +
                ", metode='" + metode + '\'' +
                ", nama_metode='" + nama_metode + '\'' +
                ", deskripsi_metode='" + deskripsi_metode + '\'' +
                ", nomor_rekening='" + nomor_rekening + '\'' +
                '}';
    }
}
