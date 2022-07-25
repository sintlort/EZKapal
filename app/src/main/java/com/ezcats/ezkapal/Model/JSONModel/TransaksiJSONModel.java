package com.ezcats.ezkapal.Model.JSONModel;

import com.ezcats.ezkapal.Model.MidtransModel;
import com.ezcats.ezkapal.Model.PembayaranModel;
import com.google.gson.annotations.SerializedName;

public class TransaksiJSONModel {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private PembayaranModel pembayaranModel;

    @SerializedName("midtrans_response")
    private MidtransModel midtransModel;

    @SerializedName("expiry")
    private String expiry;

    public TransaksiJSONModel(String message, PembayaranModel pembayaranModel, MidtransModel midtransModel, String expiry) {
        this.message = message;
        this.pembayaranModel = pembayaranModel;
        this.midtransModel = midtransModel;
        this.expiry = expiry;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PembayaranModel getPembayaranModel() {
        return pembayaranModel;
    }

    public void setPembayaranModel(PembayaranModel pembayaranModel) {
        this.pembayaranModel = pembayaranModel;
    }

    public MidtransModel getMidtransModel() {
        return midtransModel;
    }

    public void setMidtransModel(MidtransModel midtransModel) {
        this.midtransModel = midtransModel;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "TransaksiJSONModel{" +
                "message='" + message + '\'' +
                ", pembayaranModel=" + pembayaranModel +
                ", midtransModel=" + midtransModel +
                ", expiry='" + expiry + '\'' +
                '}';
    }
}
