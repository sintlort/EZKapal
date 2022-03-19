package com.ezcats.ezkapal.Model;

public class BeritaModel {

    String judulBerita, tanggalBerita, fotoBerita;

    public BeritaModel(String judulBerita, String tanggalBerita, String fotoBerita) {
        this.judulBerita = judulBerita;
        this.tanggalBerita = tanggalBerita;
        this.fotoBerita = fotoBerita;
    }

    public String getJudulBerita() {
        return judulBerita;
    }

    public void setJudulBerita(String judulBerita) {
        this.judulBerita = judulBerita;
    }

    public String getTanggalBerita() {
        return tanggalBerita;
    }

    public void setTanggalBerita(String tanggalBerita) {
        this.tanggalBerita = tanggalBerita;
    }

    public String getFotoBerita() {
        return fotoBerita;
    }

    public void setFotoBerita(String fotoBerita) {
        this.fotoBerita = fotoBerita;
    }

    @Override
    public String toString() {
        return "BeritaModel{" +
                "judulBerita='" + judulBerita + '\'' +
                ", tanggalBerita='" + tanggalBerita + '\'' +
                ", fotoBerita='" + fotoBerita + '\'' +
                '}';
    }
}

