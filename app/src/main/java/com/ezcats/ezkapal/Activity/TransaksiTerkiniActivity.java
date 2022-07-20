package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.Adapter.TransaksiTerkiniAdapter;
import com.ezcats.ezkapal.Model.TransaksiModel;
import com.ezcats.ezkapal.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiTerkiniActivity extends AppCompatActivity implements TransaksiTerkiniAdapter.OnTerkiniClick {

    RecyclerView recyclerView;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_terkini);

        recyclerView = findViewById(R.id.transaksi_recycler);
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initData();
    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<List<TransaksiModel>> call = transactionService.getTransactionRecently(token,"application/json","XMLHttpRequest");
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
        TransaksiTerkiniAdapter transaksiTerkiniAdapter = new TransaksiTerkiniAdapter(transaksiModels, this::onClick, getApplicationContext());
        recyclerView.setAdapter(transaksiTerkiniAdapter);
    }

    @Override
    public void onClick(TransaksiModel transaksiModel) {
        Intent intent = new Intent(this, DetailTransaksiActivity.class);
        intent.putExtra("Transaksi",transaksiModel);
        startActivity(intent);
    }
}