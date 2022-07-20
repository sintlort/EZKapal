package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class PenumpangModel {

    @SerializedName("nama_pemegang_tiket")
    public String namaPenumpang;

    @SerializedName("no_id_card")
    public String ktpPenumpang;

    public String card_color;


    public PenumpangModel(String namaPenumpang, String ktpPenumpang, String card_color) {
        this.namaPenumpang = namaPenumpang;
        this.ktpPenumpang = ktpPenumpang;
        this.card_color = card_color;
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

    public String getCard_color() {
        return card_color;
    }

    public void setCard_color(String card_color) {
        this.card_color = card_color;
    }

    @Override
    public String toString() {
        return "PenumpangModel{" +
                "namaPenumpang='" + namaPenumpang + '\'' +
                ", ktpPenumpang='" + ktpPenumpang + '\'' +
                ", card_color='" + card_color + '\'' +
                '}';
    }
}
