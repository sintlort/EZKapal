package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("nama")
    private String nama_user;

    @SerializedName("email")
    private String email;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("nohp")
    private String nohp;

    @SerializedName("foto")
    private String foto;

    public UserModel(String nama_user, String email, String alamat, String nohp, String foto) {
        this.nama_user = nama_user;
        this.email = email;
        this.alamat = alamat;
        this.nohp = nohp;
        this.foto = foto;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "nama_user='" + nama_user + '\'' +
                ", email='" + email + '\'' +
                ", alamat='" + alamat + '\'' +
                ", nohp='" + nohp + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
