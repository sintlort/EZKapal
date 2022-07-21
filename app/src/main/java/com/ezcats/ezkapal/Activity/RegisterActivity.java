package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.Model.RegisterModel;
import com.ezcats.ezkapal.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    EditText nama, nohp, alamat, email, password;
    CheckBox tos;

    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.btn_register_post);
        nama = (EditText) findViewById(R.id.nama_register);
        nohp = (EditText) findViewById(R.id.nohp_register);
        alamat = (EditText) findViewById(R.id.alamat_register);
        email = (EditText) findViewById(R.id.email_register);
        password = (EditText) findViewById(R.id.password_register);

        accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!password.getText().toString().equals("") && !email.getText().toString().equals("") && !alamat.getText().toString().equals("") && !nohp.getText().toString().equals("")){
                    try {
                        if(password.getText().toString().length()<=6){
                            Toast.makeText(getApplicationContext(), "Password harus lebih dari 6 huruf", Toast.LENGTH_LONG).show();
                            return;
                        }
                        double nohps = Long.parseLong(nohp.getText().toString());
                        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                            RegisterModel registerModel = new RegisterModel(email.getText().toString(), nama.getText().toString(), password.getText().toString(), alamat.getText().toString(),nohp.getText().toString());
                            Call<ResponseBody> call = accountService.registerAccount("application/json","XMLHttpRequest","application/json", registerModel);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    JSONObject jsonObject = null;
                                    String message = null;
                                    if(response.isSuccessful()){
                                        if(response.code() == 200){
                                            try {
                                                jsonObject = new JSONObject(response.body().string());
                                                message = jsonObject.getString("message");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getApplicationContext(), "Register tidak berhasil, harap ulangi beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getApplicationContext(), "Register tidak berhasil, harap ulangi beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Register tidak berhasil, harap ulangi beberapa saat lagi", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    Log.d("APIREGISTER", "onResponse: "+message);
                                    Log.d("APIREGISTER", "onResponse: "+jsonObject.toString());
                                    if (message.equals("success")) {
                                        Toast.makeText(getApplicationContext(), "Register berhasil, silahkan login terlebih dahulu", Toast.LENGTH_LONG).show();
                                        gotoIntent(LoginActivity.class);
                                    }
                                    Toast.makeText(getApplicationContext(), "Register tidak berhasil, harap ulangi beberapa saat lagi", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Register tidak berhasil, harap ulangi beberapa saat lagi", Toast.LENGTH_LONG).show();

                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(),"Email salah", Toast.LENGTH_LONG).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Nomor handphone tidak boleh huruf", Toast.LENGTH_LONG).show();
                    } catch (Exception exception){
                        Toast.makeText(getApplicationContext(), "Nomor handphone tidak boleh huruf", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if(password.getText().toString().length()<=6){
                        Toast.makeText(getApplicationContext(), "Password harus lebih dari 6 huruf", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Beberapa masukan kosong harap diisi terlebih dahulu", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void gotoIntent(Class classTarget) {
        Intent intent = new Intent(this, classTarget);
        startActivity(intent);
    }
}