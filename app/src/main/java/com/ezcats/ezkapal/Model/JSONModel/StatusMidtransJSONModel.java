package com.ezcats.ezkapal.Model.JSONModel;

import com.ezcats.ezkapal.Model.MidtransModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusMidtransJSONModel implements Serializable {

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private MidtransModel midtransModel;

    public StatusMidtransJSONModel(String error, String message, MidtransModel midtransModel) {
        this.error = error;
        this.message = message;
        this.midtransModel = midtransModel;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MidtransModel getMidtransModel() {
        return midtransModel;
    }

    public void setMidtransModel(MidtransModel midtransModel) {
        this.midtransModel = midtransModel;
    }

    @Override
    public String toString() {
        return "StatusMidtransJSONModel{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", midtransModel=" + midtransModel +
                '}';
    }
}
