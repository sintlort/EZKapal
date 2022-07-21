package com.ezcats.ezkapal.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VirtualAccountModel implements Serializable {

    @SerializedName("bank")
    private String bank;

    @SerializedName("va_number")
    private String virtual_account_number;

    public VirtualAccountModel(String bank, String virtual_account_number) {
        this.bank = bank;
        this.virtual_account_number = virtual_account_number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getVirtual_account_number() {
        return virtual_account_number;
    }

    public void setVirtual_account_number(String virtual_account_number) {
        this.virtual_account_number = virtual_account_number;
    }

    @Override
    public String toString() {
        return "VirtualAccountModel{" +
                "bank='" + bank + '\'' +
                ", virtual_account_number='" + virtual_account_number + '\'' +
                '}';
    }
}
