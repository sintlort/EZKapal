package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class PenumpangModel {

    @SerializedName("nama_pemegang_tiket")
    public String namaPenumpang;

    @SerializedName("no_id_card")
    public String ktpPenumpang;


    public PenumpangModel(String namaPenumpang, String ktpPenumpang) {
        this.namaPenumpang = namaPenumpang;
        this.ktpPenumpang = ktpPenumpang;
    }

    public String getNamaPenumpang() {
        return namaPenumpang;
    }

    public void setNamaPenumpang(String namaPenumpang) {
        this.namaPenumpang = namaPenumpang;
    }

    public String getKtpPenumpang() {
        return ktpPenumpang;
    }

    public void setKtpPenumpang(String ktpPenumpang) {
        this.ktpPenumpang = ktpPenumpang;
    }

    @Override
    public String toString() {
        return "PenumpangModel{" +
                "namaPenumpang='" + namaPenumpang + '\'' +
                ", ktpPenumpang='" + ktpPenumpang + '\'' +
                '}';
    }
}
