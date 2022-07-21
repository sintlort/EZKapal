package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MidtransModel implements Serializable {

    @SerializedName("status_code")
    private String status_code;

    @SerializedName("status_message")
    private String status_message;

    @SerializedName("transaction_id")
    private String transaction_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("gross_amount")
    private String gross_amount;

    @SerializedName("payment_type")
    private String payment_type;

    @SerializedName("transaction_time")
    private String transaction_time;

    @SerializedName("transaction_status")
    private String transaction_status;

    @SerializedName("va_numbers")
    private List<VirtualAccountModel> virtualAccountModel;

    public MidtransModel(String status_code, String status_message, String transaction_id, String order_id, String gross_amount, String payment_type, String transaction_time, String transaction_status, List<VirtualAccountModel> virtualAccountModel) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.transaction_id = transaction_id;
        this.order_id = order_id;
        this.gross_amount = gross_amount;
        this.payment_type = payment_type;
        this.transaction_time = transaction_time;
        this.transaction_status = transaction_status;
        this.virtualAccountModel = virtualAccountModel;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGross_amount() {
        return gross_amount;
    }

    public void setGross_amount(String gross_amount) {
        this.gross_amount = gross_amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public List<VirtualAccountModel> getVirtualAccountModel() {
        return virtualAccountModel;
    }

    public void setVirtualAccountModel(List<VirtualAccountModel> virtualAccountModel) {
        this.virtualAccountModel = virtualAccountModel;
    }


    @Override
    public String toString() {
        return "MidtransModel{" +
                "status_code='" + status_code + '\'' +
                ", status_message='" + status_message + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", gross_amount='" + gross_amount + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", transaction_time='" + transaction_time + '\'' +
                ", transaction_status='" + transaction_status + '\'' +
                ", virtualAccountModel=" + virtualAccountModel +
                '}';
    }
}
