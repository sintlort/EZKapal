package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TicketModel implements Serializable {

    @SerializedName("id")
    private int id_detail;

    @SerializedName("nama_asal")
    private String nama_asal;

    @SerializedName("nama_tujuan")
    private String nama_tujuan;

    @SerializedName("kode_pelabuhan_asal")
    private String kode_pelabuhan_asal;

    @SerializedName("kode_pelabuhan_tujuan")
    private String kode_pelabuhan_tujuan;

    @SerializedName("status_asal")
    private String status_asal;

    @SerializedName("status_tujuan")
    private String status_tujuan;

    @SerializedName("dermaga_asal")
    private String dermaga_asal;

    @SerializedName("dermaga_tujuan")
    private String dermaga_tujuan;

    @SerializedName("estimasi_waktu")
    private String estimasi_waktu;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("hari")
    private String hari;

    @SerializedName("harga")
    private String harga;

    @SerializedName("nama_kapal")
    private String nama_kapal;

    @SerializedName("terbayarkan")
    private String terbayarkan;

    @SerializedName("jumlah_tiket")
    private String jumlah_tiket;

    @SerializedName("waktu_berangkat_asal")
    private String waktu_berangkat_asal;

    @SerializedName("waktu_berangkat_tujuan")
    private String waktu_berangkat_tujuan;

    public TicketModel(int id_detail, String nama_asal, String nama_tujuan, String kode_pelabuhan_asal, String kode_pelabuhan_tujuan, String status_asal, String status_tujuan, String dermaga_asal, String dermaga_tujuan, String estimasi_waktu, String tanggal, String hari, String harga, String nama_kapal, String terbayarkan, String jumlah_tiket, String waktu_berangkat_asal, String waktu_berangkat_tujuan) {
        this.id_detail = id_detail;
        this.nama_asal = nama_asal;
        this.nama_tujuan = nama_tujuan;
        this.kode_pelabuhan_asal = kode_pelabuhan_asal;
        this.kode_pelabuhan_tujuan = kode_pelabuhan_tujuan;
        this.status_asal = status_asal;
        this.status_tujuan = status_tujuan;
        this.dermaga_asal = dermaga_asal;
        this.dermaga_tujuan = dermaga_tujuan;
        this.estimasi_waktu = estimasi_waktu;
        this.tanggal = tanggal;
        this.hari = hari;
        this.harga = harga;
        this.nama_kapal = nama_kapal;
        this.terbayarkan = terbayarkan;
        this.jumlah_tiket = jumlah_tiket;
        this.waktu_berangkat_asal = waktu_berangkat_asal;
        this.waktu_berangkat_tujuan = waktu_berangkat_tujuan;
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public String getNama_asal() {
        return nama_asal;
    }

    public void setNama_asal(String nama_asal) {
        this.nama_asal = nama_asal;
    }

    public String getNama_tujuan() {
        return nama_tujuan;
    }

    public void setNama_tujuan(String nama_tujuan) {
        this.nama_tujuan = nama_tujuan;
    }

    public String getKode_pelabuhan_asal() {
        return kode_pelabuhan_asal;
    }

    public void setKode_pelabuhan_asal(String kode_pelabuhan_asal) {
        this.kode_pelabuhan_asal = kode_pelabuhan_asal;
    }

    public String getKode_pelabuhan_tujuan() {
        return kode_pelabuhan_tujuan;
    }

    public void setKode_pelabuhan_tujuan(String kode_pelabuhan_tujuan) {
        this.kode_pelabuhan_tujuan = kode_pelabuhan_tujuan;
    }

    public String getStatus_asal() {
        return status_asal;
    }

    public void setStatus_asal(String status_asal) {
        this.status_asal = status_asal;
    }

    public String getStatus_tujuan() {
        return status_tujuan;
    }

    public void setStatus_tujuan(String status_tujuan) {
        this.status_tujuan = status_tujuan;
    }

    public String getDermaga_asal() {
        return dermaga_asal;
    }

    public void setDermaga_asal(String dermaga_asal) {
        this.dermaga_asal = dermaga_asal;
    }

    public String getDermaga_tujuan() {
        return dermaga_tujuan;
    }

    public void setDermaga_tujuan(String dermaga_tujuan) {
        this.dermaga_tujuan = dermaga_tujuan;
    }

    public String getEstimasi_waktu() {
        return estimasi_waktu;
    }

    public void setEstimasi_waktu(String estimasi_waktu) {
        this.estimasi_waktu = estimasi_waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }

    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
    }

    public String getTerbayarkan() {
        return terbayarkan;
    }

    public void setTerbayarkan(String terbayarkan) {
        this.terbayarkan = terbayarkan;
    }

    public String getJumlah_tiket() {
        return jumlah_tiket;
    }

    public void setJumlah_tiket(String jumlah_tiket) {
        this.jumlah_tiket = jumlah_tiket;
    }

    public String getWaktu_berangkat_asal() {
        return waktu_berangkat_asal;
    }

    public void setWaktu_berangkat_asal(String waktu_berangkat_asal) {
        this.waktu_berangkat_asal = waktu_berangkat_asal;
    }

    public String getWaktu_berangkat_tujuan() {
        return waktu_berangkat_tujuan;
    }

    public void setWaktu_berangkat_tujuan(String waktu_berangkat_tujuan) {
        this.waktu_berangkat_tujuan = waktu_berangkat_tujuan;
    }

    @Override
    public String toString() {
        return "TicketModel{" +
                "id_detail=" + id_detail +
                ", nama_asal='" + nama_asal + '\'' +
                ", nama_tujuan='" + nama_tujuan + '\'' +
                ", kode_pelabuhan_asal='" + kode_pelabuhan_asal + '\'' +
                ", kode_pelabuhan_tujuan='" + kode_pelabuhan_tujuan + '\'' +
                ", status_asal='" + status_asal + '\'' +
                ", status_tujuan='" + status_tujuan + '\'' +
                ", dermaga_asal='" + dermaga_asal + '\'' +
                ", dermaga_tujuan='" + dermaga_tujuan + '\'' +
                ", estimasi_waktu='" + estimasi_waktu + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", hari='" + hari + '\'' +
                ", harga='" + harga + '\'' +
                ", nama_kapal='" + nama_kapal + '\'' +
                ", terbayarkan='" + terbayarkan + '\'' +
                ", jumlah_tiket='" + jumlah_tiket + '\'' +
                ", waktu_berangkat_asal='" + waktu_berangkat_asal + '\'' +
                ", waktu_berangkat_tujuan='" + waktu_berangkat_tujuan + '\'' +
                '}';
    }
}
