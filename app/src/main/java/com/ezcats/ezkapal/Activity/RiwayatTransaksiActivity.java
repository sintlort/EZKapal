package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.Adapter.RiwayatTransaksiAdapter;
import com.ezcats.ezkapal.Model.TransaksiModel;
import com.ezcats.ezkapal.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatTransaksiActivity extends AppCompatActivity implements RiwayatTransaksiAdapter.OnTransaksiClick {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi);

        recyclerView = findViewById(R.id.transaksi_recycler_riwayat);

        initData();
    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<List<TransaksiModel>> call = transactionService.getHistoryTransaction(token,"application/json","XMLHttpRequest");
        call.enqueue(new Callback<List<TransaksiModel>>() {
            @Override
            public void onResponse(Call<List<TransaksiModel>> call, Response<List<TransaksiModel>> response) {
                if (response.isSuccessful()){
                    Runnable run = new Runnable() {
                        @Override
                        public void run(){
                            initRecycler(response.body());
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(run, 1000);
                } else {
                    Toast.makeText(getApplicationContext(), "Harap periksa koneksi anda!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiModel>> call, Throwable t) {

            }
        });
    }

    private void initRecycler(List<TransaksiModel> transaksiModels) {
        RiwayatTransaksiAdapter transaksiAdapter = new RiwayatTransaksiAdapter(transaksiModels, this::onClick);
        recyclerView.setAdapter(transaksiAdapter);
    }

    @Override
    public void onClick(TransaksiModel transaksiModel) {

    }
}