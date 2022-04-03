package com.ezcats.ezkapal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.Activity.CaptureActivity;
import com.ezcats.ezkapal.Adapter.BeritaAdapter;
import com.ezcats.ezkapal.Model.BeritaModel;
import com.ezcats.ezkapal.databinding.ActivityMainBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    CountDownTimer countDownTimer;
    String token;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String type_account = sharedPreferences.getString(getString(R.string.type_account), "");
        token = sharedPreferences.getString(getString(R.string.token), "");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutID, new home_fragment_without_shimmer());
        ft.addToBackStack(null);
        ft.commit();

/*        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                replaceFragment(new home_fragment_without_shimmer());
            }
        }.start();*/

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.bot_frag_home:
                    replaceFragment(new home_fragment_without_shimmer());
                    break;
                case R.id.bot_frag_ticket:
                    replaceFragment(new fragment_ticket());
                    break;

                case R.id.bot_frag_profile:
                    replaceFragment(new fragment_profile());
                    break;
            }
            return true;
        });

        binding.fabButton.setVisibility(View.GONE);
        if(type_account.equals("PAdmin") || type_account.equals("HAdmin")){
            Log.d("TYPE_ACCOUNT", "TESTCLICKQR: "+type_account);
            binding.fabButton.setVisibility(View.VISIBLE);
            binding.fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                    intentIntegrator.setPrompt("Flash tekan volume atas!");
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setOrientationLocked(true);
                    intentIntegrator.setCaptureActivity(CaptureActivity.class);
                    intentIntegrator.initiateScan();
                }
            });
        }

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutID, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!= null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            builder.setMessage("Verifikasi ticket ini?");
            builder.setPositiveButton("Verifikasi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
                    Call<ResponseBody> call = transactionService.checkTicket(token,"application/json", "XMLHttpRequest",intentResult.getContents());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                if(response.code()==200){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        String message = jsonObject.getString("message");
                                        if(message.equals("success")){
                                            Toast.makeText(getApplicationContext(), "Tiket telah diverifikasi", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Tiket tidak ditemukan atau sudah digunakan", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            });
            builder.show();
        } else {
            Toast.makeText(MainActivity.this, "QRCode tidak ditemukan!", Toast.LENGTH_LONG).show();
        }
    }
}