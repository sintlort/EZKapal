package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PemegangTicketModel implements Serializable {

    @SerializedName("id_detail_pembelian")
    private int id_detail;

    @SerializedName("id_pembelian")
    private int id_pembelian;

    @SerializedName("nama_pemegang_tiket")
    private String nama_pemegang_tiket;

    @SerializedName("kode_tiket")
    private String kode_tiket;

    @SerializedName("status")
    private String status;

    @SerializedName("tanggal")
    private String tanggal;

    public PemegangTicketModel(int id_detail, int id_pembelian, String nama_pemegang_tiket, String kode_tiket, String status, String tanggal) {
        this.id_detail = id_detail;
        this.id_pembelian = id_pembelian;
        this.nama_pemegang_tiket = nama_pemegang_tiket;
        this.kode_tiket = kode_tiket;
        this.status = status;
        this.tanggal = tanggal;
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getId_pembelian() {
        return id_pembelian;
    }

    public void setId_pembelian(int id_pembelian) {
        this.id_pembelian = id_pembelian;
    }

    public String getNama_pemegang_tiket() {
        return nama_pemegang_tiket;
    }

    public void setNama_pemegang_tiket(String nama_pemegang_tiket) {
        this.nama_pemegang_tiket = nama_pemegang_tiket;
    }

    public String getKode_tiket() {
        return kode_tiket;
    }

    public void setKode_tiket(String kode_tiket) {
        this.kode_tiket = kode_tiket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return "PemegangTicketModel{" +
                "id_detail=" + id_detail +
                ", id_pembelian=" + id_pembelian +
                ", nama_pemegang_tiket='" + nama_pemegang_tiket + '\'' +
                ", kode_tiket='" + kode_tiket + '\'' +
                ", status='" + status + '\'' +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}
