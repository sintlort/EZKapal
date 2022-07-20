package com.ezcats.ezkapal.Model.JSONModel;

import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.Model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserJSONModel {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private UserModel data;

    public UserJSONModel(String message, UserModel data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserJSONModel{" +
                "message='" + message + '\'' +
                '}';
    }
}
