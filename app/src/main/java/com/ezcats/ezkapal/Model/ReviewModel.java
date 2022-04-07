package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("id")
    private int id;

    @SerializedName("id_pembelian")
    private int id_pembelian;

    @SerializedName("review")
    private String review;

    @SerializedName("score")
    private int score;

    public ReviewModel(int id_pembelian, String review, int score) {
        this.id_pembelian = id_pembelian;
        this.review = review;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pembelian() {
        return id_pembelian;
    }

    public void setId_pembelian(int id_pembelian) {
        this.id_pembelian = id_pembelian;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ReviewModel{" +
                "id=" + id +
                ", id_pembelian=" + id_pembelian +
                ", review='" + review + '\'' +
                ", score=" + score +
                '}';
    }
}
