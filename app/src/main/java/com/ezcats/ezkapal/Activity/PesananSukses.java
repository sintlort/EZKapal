package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezcats.ezkapal.MainActivity;
import com.ezcats.ezkapal.Model.VirtualAccountModel;
import com.ezcats.ezkapal.R;

public class PesananSukses extends AppCompatActivity {

    Button lanjutkanButton;

    TextView vaNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_sukses);
        lanjutkanButton = findViewById(R.id.lanjutkan);
        vaNumber = findViewById(R.id.van);
        VirtualAccountModel virtualAccountModel = (VirtualAccountModel) getIntent().getSerializableExtra("va_number");
        vaNumber.setText(virtualAccountModel.getVirtual_account_number());
        lanjutkanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}