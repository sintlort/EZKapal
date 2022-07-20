package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.MainActivity;
import com.ezcats.ezkapal.Model.LoginModelPage;
import com.ezcats.ezkapal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText email, password;
    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        email = (EditText) findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.password_login);

        accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAPI();
            }
        });
    }

    private void loginAPI() {
        if(!email.getText().toString().equals("") || !password.getText().toString().equals("")){
            Log.d("LOGINAPI", "onClick: "+email.getText().toString());
            Log.d("LOGINAPI", "onClick: "+password.getText().toString());
            LoginModelPage loginModelPage = new LoginModelPage(email.getText().toString(), password.getText().toString());
            Call<ResponseBody> call = accountService.loginAccount("application/json","XMLHttpRequest", loginModelPage);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    try {
                        if(response.isSuccessful()){
                            if(response.code()==200){
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String message = jsonObject.getString("message");
                                if(message.equals("success")){
                                    JSONObject userIdentity = jsonObject.getJSONObject("user");
                                    editor.putString(getString(R.string.token), "Bearer "+jsonObject.getString("token"));
                                    editor.putString(getString(R.string.type_account), jsonObject.getString("type_account"));
                                    editor.putString(getString(R.string.name_shared_preference), userIdentity.getString("nama"));
                                    editor.putString(getString(R.string.email_shared_preference), userIdentity.getString("email"));
                                    editor.putString(getString(R.string.number_shared_preference), userIdentity.getString("nohp"));
                                    editor.putString(getString(R.string.picture_shared_preference), userIdentity.getString("foto"));
                                    editor.apply();
                                    gotoIntent(MainActivity.class);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Email atau password salah!!",Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email atau password salah!!",Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void gotoIntent(Class classTarget) {
        Intent intent = new Intent(this, classTarget);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}