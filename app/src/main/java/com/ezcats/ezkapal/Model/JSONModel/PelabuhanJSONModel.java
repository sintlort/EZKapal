package com.ezcats.ezkapal.Model.JSONModel;

import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PelabuhanJSONModel {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private PelabuhanModel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PelabuhanModel getData() {
        return data;
    }

    public void setData(PelabuhanModel data) {
        this.data = data;
    }
}
