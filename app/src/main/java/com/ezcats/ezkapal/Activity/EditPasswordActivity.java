package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.MainActivity;
import com.ezcats.ezkapal.Model.JSONModel.UserJSONModel;
import com.ezcats.ezkapal.Model.UserModel;
import com.ezcats.ezkapal.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditPasswordActivity extends AppCompatActivity {
    EditText old_password, new_password, new_password_confirm;
    Button btn_update;

    String s_old_password, s_new_password, s_new_password_confirm;

    SharedPreferences sharedPreferences;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        initialization();
    }

    private void initialization() {
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        new_password_confirm = findViewById(R.id.new_confirm_password);
        btn_update = findViewById(R.id.btn_password);
        initToken();
        initOnClick();
    }

    private void initToken() {
        this.sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString(getString(R.string.token), "");
    }

    private void initOnClick() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        getStringData();
        if(checkPassword(this.s_new_password, this.s_new_password_confirm)){
            AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
            Call<UserJSONModel> call = accountService.updatePassword("application/json", "XMLHttpRequest", this.token, this.s_old_password, this.s_new_password);
            call.enqueue(new Callback<UserJSONModel>() {
                @Override
                public void onResponse(Call<UserJSONModel> call, Response<UserJSONModel> response) {
                    if(response.code()==200){
                        newActivity(response.body().getData());
                        showMessage("Edit password berhasil !");
                        onBackPressed();
                    } else {
                        showMessage("Password lama salah !");
                    }
                }

                @Override
                public void onFailure(Call<UserJSONModel> call, Throwable t) {
                    showMessage("Mohon periksa koneksi anda !");
                }
            });
        } else {
            showMessage("Konfirmasi password salah !");
        }
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void newActivity(UserModel data) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    }

    private boolean checkPassword(String new1, String new2) {
        if(new1.equals(new2)){
            return true;
        } else {
            return false;
        }
    }

    private void getStringData() {
        this.s_old_password = old_password.getText().toString();
        this.s_new_password = new_password.getText().toString();
        this.s_new_password_confirm = new_password_confirm.getText().toString();
    }
}