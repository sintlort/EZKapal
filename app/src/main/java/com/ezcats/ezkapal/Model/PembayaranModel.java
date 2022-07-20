package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class PembayaranModel {

    @SerializedName("id")
    private int id_detail;

    @SerializedName("id_metode_pembayaran")
    private int id_metode_pembayaran;

    @SerializedName("id_user")
    private int id_user;

    @SerializedName("nomor_polisi")
    private String nomor_polisi;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("total_harga")
    private int total_harga;

    @SerializedName("status")
    private String status;

    public PembayaranModel(int id_detail, int id_metode_pembayaran, int id_user, String nomor_polisi, String tanggal, int total_harga, String status) {
        this.id_detail = id_detail;
        this.id_metode_pembayaran = id_metode_pembayaran;
        this.id_user = id_user;
        this.nomor_polisi = nomor_polisi;
        this.tanggal = tanggal;
        this.total_harga = total_harga;
        this.status = status;
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getId_metode_pembayaran() {
        return id_metode_pembayaran;
    }

    public void setId_metode_pembayaran(int id_metode_pembayaran) {
        this.id_metode_pembayaran = id_metode_pembayaran;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNomor_polisi() {
        return nomor_polisi;
    }

    public void setNomor_polisi(String nomor_polisi) {
        this.nomor_polisi = nomor_polisi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PembayaranModel{" +
                "id_detail=" + id_detail +
                ", id_metode_pembayaran=" + id_metode_pembayaran +
                ", id_user=" + id_user +
                ", nomor_polisi='" + nomor_polisi + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", total_harga=" + total_harga +
                ", status='" + status + '\'' +
                '}';
    }
}
