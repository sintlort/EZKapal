package com.ezcats.ezkapal;

import androidx.annotation.NonNull;
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
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.Activity.CaptureActivity;
import com.ezcats.ezkapal.Adapter.BeritaAdapter;
import com.ezcats.ezkapal.Fragment.LogoutFragment;
import com.ezcats.ezkapal.Fragment.VerifikasiFragment;
import com.ezcats.ezkapal.Model.BeritaModel;
import com.ezcats.ezkapal.Model.JSONModel.UserJSONModel;
import com.ezcats.ezkapal.Model.PemegangTicketModel;
import com.ezcats.ezkapal.databinding.ActivityMainBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
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
    String token, name, email, number;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String type_account = sharedPreferences.getString(getString(R.string.type_account), "");
        token = sharedPreferences.getString(getString(R.string.token), "");
        name = sharedPreferences.getString(getString(R.string.name_shared_preference), "");
        email = sharedPreferences.getString(getString(R.string.email_shared_preference), "");
        number = sharedPreferences.getString(getString(R.string.email_shared_preference), "");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutID, new home_fragment_without_shimmer());
        ft.addToBackStack(null);
        ft.commit();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("MAINACTIVITY", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String tokens = task.getResult();

                        Log.d("MAINACTIVITY", tokens);
                        AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
                        Call<UserJSONModel> call = accountService.receiveFCM("application/json","XMLHttpRequest",token, tokens);
                        call.enqueue(new Callback<UserJSONModel>() {
                            @Override
                            public void onResponse(Call<UserJSONModel> call, Response<UserJSONModel> response) {
                            }

                            @Override
                            public void onFailure(Call<UserJSONModel> call, Throwable t) {

                            }
                        });
                    }
                });

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

            switch (item.getItemId()) {
                case R.id.bot_frag_home:
                    replaceFragment(new home_fragment_without_shimmer(), "left", "HOME_WITHOUT_SHIMMER_FRAGMENT");
                    break;
                case R.id.bot_frag_ticket:
                    replaceFragment(new fragment_ticket(), "right","TICKET_FRAGMENT");
                    break;

                case R.id.bot_frag_profile:
                    replaceFragment(new fragment_profile(), "right", "PROFILE_FRAGMENT");
                    break;
            }
            return true;
        });

        binding.fabButton.setVisibility(View.GONE);
        if (type_account.equals("TAdmin")) {
            Log.d("TYPE_ACCOUNT", "TESTCLICKQR: " + type_account);
            binding.fabButton.setVisibility(View.VISIBLE);
            binding.fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                    intentIntegrator.setPrompt("Flash tekan volume atas!");
                    intentIntegrator.setOrientationLocked(true);
                    intentIntegrator.setCaptureActivity(CaptureActivity.class);
                    intentIntegrator.initiateScan();
                }
            });
        }

    }

    public void replaceFragment(Fragment fragment, String s, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (s) {
            case "left":
                ft.setCustomAnimations(R.anim.from_left, R.anim.to_right, R.anim.from_left, R.anim.to_right);
                break;
            case "right":
                ft.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_right, R.anim.to_left);
                break;
        }
        ft.replace(R.id.frameLayoutID, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null) {
            Log.d("QRCODEDATA", "onActivityResult: CODE " + intentResult.getContents());
            TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
            Call<ResponseBody> call = transactionService.getTicketData(token, "application/json", "XMLHttpRequest", intentResult.getContents());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String message = jsonObject.getString("message");
                                if (message.equals("success")) {
                                    JSONObject dataObject = jsonObject.getJSONObject("data");
                                    PemegangTicketModel pemegangTicketModel =
                                            new PemegangTicketModel(dataObject.getInt("id_detail_pembelian"),
                                                    dataObject.getInt("id_pembelian"),
                                                    dataObject.getString("nama_pemegang_tiket"),
                                                    dataObject.getString("kode_tiket"),
                                                    dataObject.getString("status"),
                                                    dataObject.getString("tanggal"));
                                    VerifikasiFragment verifikasiFragment = new VerifikasiFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("ticket_info", pemegangTicketModel);
                                    bundle.putString("intentResults", intentResult.getContents());
                                    verifikasiFragment.setArguments(bundle);
                                    verifikasiFragment.show(getSupportFragmentManager(), "VERIFIKASI_FRAGMENT");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Informasi tiket tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali, \njika kesalahan terjadi berulang, harap menghubungi administrator", Toast.LENGTH_SHORT).show();
                                Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API FAILED IOEXCEPTION");
                                e.printStackTrace();
                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali, \njika kesalahan terjadi berulang, harap menghubungi administrator", Toast.LENGTH_SHORT).show();
                                Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API FAILED IOEXCEPTION");
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                            Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API NOT 200");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                        Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API NOT SUCCESSFUL");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                    Log.d("GET TICKET DATA", "onResponse: GET TICKET DATA API ON FAILURE");
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "QRCode tidak ditemukan!", Toast.LENGTH_LONG).show();
        }
    }
}