package com.ezcats.ezkapal.Model;

public class PenumpangModel {

    public String namaPenumpang, ktpPenumpang;

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
