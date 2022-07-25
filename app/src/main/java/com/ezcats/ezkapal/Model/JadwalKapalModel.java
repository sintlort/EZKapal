package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JadwalKapalModel implements Serializable {

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("asal_pelabuhan")
    private String asal_pelabuhan;

    @SerializedName("tujuan_pelabuhan")
    private String tujuan_pelabuhan;

    @SerializedName("kode_pelabuhan_asal")
    private String kode_asal;

    @SerializedName("kode_pelabuhan_tujuan")
    private String kode_tujuan;

    @SerializedName("waktu_berangkat")
    private String waktu_berangkat;

    @SerializedName("waktu_sampai")
    private String waktu_sampai;

    @SerializedName("nama_dermaga_asal")
    private String dermaga_asal;

    @SerializedName("nama_dermaga_tujuan")
    private String dermaga_tujuan;

    @SerializedName("status_pelabuhan_asal")
    private String status_asal;

    @SerializedName("status_pelabuhan_tujuan")
    private String status_tujuan;

    public JadwalKapalModel(String tanggal, String asal_pelabuhan, String tujuan_pelabuhan, String kode_asal, String kode_tujuan, String waktu_berangkat, String waktu_sampai, String dermaga_asal, String dermaga_tujuan, String status_asal, String status_tujuan) {
        this.tanggal = tanggal;
        this.asal_pelabuhan = asal_pelabuhan;
        this.tujuan_pelabuhan = tujuan_pelabuhan;
        this.kode_asal = kode_asal;
        this.kode_tujuan = kode_tujuan;
        this.waktu_berangkat = waktu_berangkat;
        this.waktu_sampai = waktu_sampai;
        this.dermaga_asal = dermaga_asal;
        this.dermaga_tujuan = dermaga_tujuan;
        this.status_asal = status_asal;
        this.status_tujuan = status_tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAsal_pelabuhan() {
        return asal_pelabuhan;
    }

    public void setAsal_pelabuhan(String asal_pelabuhan) {
        this.asal_pelabuhan = asal_pelabuhan;
    }

    public String getTujuan_pelabuhan() {
        return tujuan_pelabuhan;
    }

    public void setTujuan_pelabuhan(String tujuan_pelabuhan) {
        this.tujuan_pelabuhan = tujuan_pelabuhan;
    }

    public String getKode_asal() {
        return kode_asal;
    }

    public void setKode_asal(String kode_asal) {
        this.kode_asal = kode_asal;
    }

    public String getKode_tujuan() {
        return kode_tujuan;
    }

    public void setKode_tujuan(String kode_tujuan) {
        this.kode_tujuan = kode_tujuan;
    }

    public String getWaktu_berangkat() {
        return waktu_berangkat;
    }

    public void setWaktu_berangkat(String waktu_berangkat) {
        this.waktu_berangkat = waktu_berangkat;
    }

    public String getWaktu_sampai() {
        return waktu_sampai;
    }

    public void setWaktu_sampai(String waktu_sampai) {
        this.waktu_sampai = waktu_sampai;
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

    @Override
    public String toString() {
        return "JadwalKapalModel{" +
                "tanggal='" + tanggal + '\'' +
                ", asal_pelabuhan='" + asal_pelabuhan + '\'' +
                ", tujuan_pelabuhan='" + tujuan_pelabuhan + '\'' +
                ", kode_asal='" + kode_asal + '\'' +
                ", kode_tujuan='" + kode_tujuan + '\'' +
                ", waktu_berangkat='" + waktu_berangkat + '\'' +
                ", waktu_sampai='" + waktu_sampai + '\'' +
                ", dermaga_asal='" + dermaga_asal + '\'' +
                ", dermaga_tujuan='" + dermaga_tujuan + '\'' +
                ", status_asal='" + status_asal + '\'' +
                ", status_tujuan='" + status_tujuan + '\'' +
                '}';
    }
}
