package com.ezcats.ezkapal.Model.JSONModel;

import com.ezcats.ezkapal.Model.JadwalKapalModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JadwalKapalJSONModel implements Serializable {

    @SerializedName("errors")
    private String errors;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<JadwalKapalModel> jadwalKapalModels;

    public JadwalKapalJSONModel(String errors, String message, List<JadwalKapalModel> jadwalKapalModels) {
        this.errors = errors;
        this.message = message;
        this.jadwalKapalModels = jadwalKapalModels;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<JadwalKapalModel> getJadwalKapalModels() {
        return jadwalKapalModels;
    }

    public void setJadwalKapalModels(List<JadwalKapalModel> jadwalKapalModels) {
        this.jadwalKapalModels = jadwalKapalModels;
    }

    @Override
    public String toString() {
        return "JadwalKapalJSONModel{" +
                "errors='" + errors + '\'' +
                ", message='" + message + '\'' +
                ", jadwalKapalModels=" + jadwalKapalModels +
                '}';
    }
}
