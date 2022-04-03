package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ezcats.ezkapal.MainActivity;
import com.ezcats.ezkapal.R;

public class LandingActivity extends AppCompatActivity {


    Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        btn_login = findViewById(R.id.btn_login_landing);
        btn_register = findViewById(R.id.btn_register_landing);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        if(!token.equals("")){
            gotoIntent(MainActivity.class);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoIntent(LoginActivity.class);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoIntent(RegisterActivity.class);
            }
        });
    }

    private void gotoIntent(Class classTarget) {
        Intent intent = new Intent(this, classTarget);
        startActivity(intent);
    }
}