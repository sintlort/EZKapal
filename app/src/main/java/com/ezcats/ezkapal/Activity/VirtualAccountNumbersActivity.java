package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ezcats.ezkapal.Model.JSONModel.StatusMidtransJSONModel;
import com.ezcats.ezkapal.R;

public class VirtualAccountNumbersActivity extends AppCompatActivity {

    StatusMidtransJSONModel statusMidtransJSONModel;

    TextView nama_bank, van, expiry;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_account_numbers);

        initData();
    }

    private void initData() {
        nama_bank = findViewById(R.id.nama_bank_van);
        van = findViewById(R.id.vans);
        expiry = findViewById(R.id.batas_waktu_van);
        constraintLayout = findViewById(R.id.constraint_back_main_menu);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        statusMidtransJSONModel = (StatusMidtransJSONModel) getIntent().getSerializableExtra("va_number");

        displayData();
    }

    private void displayData() {
        nama_bank.setText(statusMidtransJSONModel.getMidtransModel().getVirtualAccountModel().get(0).getBank().toUpperCase());
        van.setText(statusMidtransJSONModel.getMidtransModel().getVirtualAccountModel().get(0).getVirtual_account_number());
        expiry.setText(statusMidtransJSONModel.getExpiry());
    }
}