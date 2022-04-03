package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("email")
    private String email;

    @SerializedName("nama")
    private String nama;

    @SerializedName("password")
    private String password;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("nohp")
    private String nohp;

    public RegisterModel(String email, String nama, String password, String alamat, String nohp) {
        this.email = email;
        this.nama = nama;
        this.password = password;
        this.alamat = alamat;
        this.nohp = nohp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "RegisterModel{" +
                "email='" + email + '\'' +
                ", nama='" + nama + '\'' +
                ", password='" + password + '\'' +
                ", alamat='" + alamat + '\'' +
                ", nohp='" + nohp + '\'' +
                '}';
    }
}
