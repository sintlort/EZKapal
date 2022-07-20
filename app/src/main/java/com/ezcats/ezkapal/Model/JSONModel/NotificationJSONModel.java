package com.ezcats.ezkapal.Model.JSONModel;

import com.ezcats.ezkapal.Model.NotificationModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationJSONModel {
    @SerializedName("errors")
    private String errors_message;

    @SerializedName("message")
    private String messages;

    @SerializedName("data")
    private List<NotificationModel> notificationModel;

    public NotificationJSONModel(String errors_message, String messages, List<NotificationModel> notificationModel) {
        this.errors_message = errors_message;
        this.messages = messages;
        this.notificationModel = notificationModel;
    }

    public String getErrors_message() {
        return errors_message;
    }

    public void setErrors_message(String errors_message) {
        this.errors_message = errors_message;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public List<NotificationModel> getNotificationModel() {
        return notificationModel;
    }

    public void setNotificationModel(List<NotificationModel> notificationModel) {
        this.notificationModel = notificationModel;
    }

    @Override
    public String toString() {
        return "NotificationJSONModel{" +
                "errors_message='" + errors_message + '\'' +
                ", messages='" + messages + '\'' +
                ", notificationModel=" + notificationModel +
                '}';
    }
}
