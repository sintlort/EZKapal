package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("notification_by")
    private int notification_by;

    @SerializedName("status")
    private int status;

    @SerializedName("type")
    private int type;

    @SerializedName("click_action")
    private String click_action;

    public NotificationModel(int id, int user_id, String title, String body, int notification_by, int status, int type, String click_action) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.body = body;
        this.notification_by = notification_by;
        this.status = status;
        this.type = type;
        this.click_action = click_action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getNotification_by() {
        return notification_by;
    }

    public void setNotification_by(int notification_by) {
        this.notification_by = notification_by;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClick_action() {
        return click_action;
    }

    public void setClick_action(String click_action) {
        this.click_action = click_action;
    }

    @Override
    public String toString() {
        return "NotificationModel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", notification_by=" + notification_by +
                ", status=" + status +
                ", type=" + type +
                ", click_action='" + click_action + '\'' +
                '}';
    }
}
