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

    @SerializedName("pelabuhan_asal")
    private String pelabuhan_asal;

    @SerializedName("pelabuhan_tujuan")
    private String pelabuhan_tujuan;

    @SerializedName("kapal")
    private String kapal;

    @SerializedName("waktu_asal")
    private String waktu_asal;

    @SerializedName("waktu_tujuan")
    private String waktu_tujuan;

    public PemegangTicketModel(int id_detail, int id_pembelian, String nama_pemegang_tiket, String kode_tiket, String status, String tanggal, String pelabuhan_asal, String pelabuhan_tujuan, String kapal, String waktu_asal, String waktu_tujuan) {
        this.id_detail = id_detail;
        this.id_pembelian = id_pembelian;
        this.nama_pemegang_tiket = nama_pemegang_tiket;
        this.kode_tiket = kode_tiket;
        this.status = status;
        this.tanggal = tanggal;
        this.pelabuhan_asal = pelabuhan_asal;
        this.pelabuhan_tujuan = pelabuhan_tujuan;
        this.kapal = kapal;
        this.waktu_asal = waktu_asal;
        this.waktu_tujuan = waktu_tujuan;
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

    public String getPelabuhan_asal() {
        return pelabuhan_asal;
    }

    public void setPelabuhan_asal(String pelabuhan_asal) {
        this.pelabuhan_asal = pelabuhan_asal;
    }

    public String getPelabuhan_tujuan() {
        return pelabuhan_tujuan;
    }

    public void setPelabuhan_tujuan(String pelabuhan_tujuan) {
        this.pelabuhan_tujuan = pelabuhan_tujuan;
    }

    public String getKapal() {
        return kapal;
    }

    public void setKapal(String kapal) {
        this.kapal = kapal;
    }

    public String getWaktu_asal() {
        return waktu_asal;
    }

    public void setWaktu_asal(String waktu_asal) {
        this.waktu_asal = waktu_asal;
    }

    public String getWaktu_tujuan() {
        return waktu_tujuan;
    }

    public void setWaktu_tujuan(String waktu_tujuan) {
        this.waktu_tujuan = waktu_tujuan;
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
                ", pelabuhan_asal='" + pelabuhan_asal + '\'' +
                ", pelabuhan_tujuan='" + pelabuhan_tujuan + '\'' +
                ", kapal='" + kapal + '\'' +
                ", waktu_asal='" + waktu_asal + '\'' +
                ", waktu_tujuan='" + waktu_tujuan + '\'' +
                '}';
    }
}
