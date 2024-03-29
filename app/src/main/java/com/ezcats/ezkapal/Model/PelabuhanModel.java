package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class PelabuhanModel {

    @SerializedName("id")
    private int idPelabuhan;
    @SerializedName("kode_pelabuhan")
    private String kodePelabuhan;
    @SerializedName("nama_pelabuhan")
    private String namaPelabuhan;
    @SerializedName("status")
    private String statusPelabuhan;
    @SerializedName("tipe_pelabuhan")
    private String tipePelabuhan;
    @SerializedName("lama_beroperasi")
    private String lamaBeroperasi;
    @SerializedName("alamat_kantor")
    private String alamatPelabuhan;
    @SerializedName("foto")
    private String fotoPelabuhan;
    private boolean expandable;

    public PelabuhanModel(int idPelabuhan, String namaPelabuhan, String statusPelabuhan, String kodePelabuhan, String tipePelabuhan, String lamaBeroperasi, String alamatPelabuhan, String fotoPelabuhan) {
        this.idPelabuhan = idPelabuhan;
        this.namaPelabuhan = namaPelabuhan;
        this.statusPelabuhan = statusPelabuhan;
        this.kodePelabuhan = kodePelabuhan;
        this.tipePelabuhan = tipePelabuhan;
        this.lamaBeroperasi = lamaBeroperasi;
        this.alamatPelabuhan = alamatPelabuhan;
        this.fotoPelabuhan = fotoPelabuhan;
        this.expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public int getIdPelabuhan() {
        return idPelabuhan;
    }

    public void setIdPelabuhan(int idPelabuhan) {
        this.idPelabuhan = idPelabuhan;
    }

    public String getNamaPelabuhan() {
        return namaPelabuhan;
    }

    public void setNamaPelabuhan(String namaPelabuhan) {
        this.namaPelabuhan = namaPelabuhan;
    }

    public String getStatusPelabuhan() {
        return statusPelabuhan;
    }

    public void setStatusPelabuhan(String statusPelabuhan) {
        this.statusPelabuhan = statusPelabuhan;
    }

    public String getKodePelabuhan() {
        return kodePelabuhan;
    }

    public void setKodePelabuhan(String kodePelabuhan) {
        this.kodePelabuhan = kodePelabuhan;
    }

    public String getTipePelabuhan() {
        return tipePelabuhan;
    }

    public void setTipePelabuhan(String tipePelabuhan) {
        this.tipePelabuhan = tipePelabuhan;
    }

    public String getLamaBeroperasi() {
        return lamaBeroperasi;
    }

    public void setLamaBeroperasi(String lamaBeroperasi) {
        this.lamaBeroperasi = lamaBeroperasi;
    }

    public String getAlamatPelabuhan() {
        return alamatPelabuhan;
    }

    public void setAlamatPelabuhan(String alamatPelabuhan) {
        this.alamatPelabuhan = alamatPelabuhan;
    }

    public String getFotoPelabuhan() {
        return fotoPelabuhan;
    }

    public void setFotoPelabuhan(String fotoPelabuhan) {
        this.fotoPelabuhan = fotoPelabuhan;
    }

    @Override
    public String toString() {
        return "PelabuhanModel{" +
                "idPelabuhan='" + idPelabuhan + '\'' +
                ", namaPelabuhan='" + namaPelabuhan + '\'' +
                ", statusPelabuhan='" + statusPelabuhan + '\'' +
                ", kodePelabuhan='" + kodePelabuhan + '\'' +
                ", tipePelabuhan='" + tipePelabuhan + '\'' +
                ", lamaBeroperasi='" + lamaBeroperasi + '\'' +
                ", alamatPelabuhan='" + alamatPelabuhan + '\'' +
                ", fotoPelabuhan='" + fotoPelabuhan + '\'' +
                '}';
    }
}
