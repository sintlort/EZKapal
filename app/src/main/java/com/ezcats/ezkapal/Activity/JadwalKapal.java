package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TicketService;
import com.ezcats.ezkapal.Adapter.JadwalKapalAdapter;
import com.ezcats.ezkapal.Adapter.SearchTicketAdapter;
import com.ezcats.ezkapal.Fragment.PelabuhanFragment;
import com.ezcats.ezkapal.Fragment.PencarianFragment;
import com.ezcats.ezkapal.Model.JSONModel.JadwalKapalJSONModel;
import com.ezcats.ezkapal.Model.JadwalKapalModel;
import com.ezcats.ezkapal.R;
import com.ezcats.ezkapal.ticket_kapal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalKapal extends AppCompatActivity implements PencarianFragment.PencarianData {

    RecyclerView recyclerView;
    ConstraintLayout filterConstraint;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_kapal);

        recyclerView = findViewById(R.id.jadwal_recycler);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");
        getData();

        filterConstraint = findViewById(R.id.filter_constraint);
        filterConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pencarianInit();
            }
        });
    }

    private void pencarianInit() {
        PencarianFragment pencarianFragment = new PencarianFragment();
        pencarianFragment.show(getSupportFragmentManager(), "PENCARIAN_DIALOG_FRAGMENT");
    }

    private void getData() {
        TicketService ticketService = RetrofitClient.getRetrofitInstance().create(TicketService.class);
        Call<JadwalKapalJSONModel> call = ticketService.getJadwalKapal(token, "application/json", "XMLHttpRequest");
        call.enqueue(new Callback<JadwalKapalJSONModel>() {
            @Override
            public void onResponse(Call<JadwalKapalJSONModel> call, Response<JadwalKapalJSONModel> response) {
                if(response.isSuccessful()){
                    if (response.code()==200){
                        initRecylcer(response.body().getJadwalKapalModels());
                    } else {
                        broadcastToast("Sepertinya terjadi kesalahan dalam server, ulang kembali beberapa saat lagi");
                    }
                } else {
                    broadcastToast("Sepertinya terjadi kesalahan dalam server, ulang kembali beberapa saat lagi");
                }
            }

            @Override
            public void onFailure(Call<JadwalKapalJSONModel> call, Throwable t) {
                Log.d("JadwalKapal", "onFailure: "+t);
                broadcastToast("Sepertinya terjadi kesalahan, harap hubungi administrator");
            }
        });
    }

    private void broadcastToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void initRecylcer(List<JadwalKapalModel> jadwalKapalModels) {
        JadwalKapalAdapter adapter = new JadwalKapalAdapter(jadwalKapalModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void sendData(int asal, int tujuan, int golongan, String date) {
        filterData(asal, tujuan, golongan, date);
    }

    private void filterData(int asal, int tujuan, int golongan, String date) {
        TicketService ticketService = RetrofitClient.getRetrofitInstance().create(TicketService.class);
        Call<JadwalKapalJSONModel> call = ticketService.sendFilter(token, "application/json", "XMLHttpRequest", asal, tujuan, golongan, date);
        call.enqueue(new Callback<JadwalKapalJSONModel>() {
            @Override
            public void onResponse(Call<JadwalKapalJSONModel> call, Response<JadwalKapalJSONModel> response) {
                if(response.isSuccessful()){
                    if (response.code()==200){
                        reInitRecycler(response.body().getJadwalKapalModels());
                    }else {
                        broadcastToast("Sepertinya terjadi kesalahan dalam server, ulang kembali beberapa saat lagi");
                    }
                }else {
                    broadcastToast("Sepertinya terjadi kesalahan dalam server, ulang kembali beberapa saat lagi");
                }
            }

            @Override
            public void onFailure(Call<JadwalKapalJSONModel> call, Throwable t) {
                Log.d("JadwalKapal", "onFailure: "+t);
                broadcastToast("Sepertinya terjadi kesalahan, harap hubungi administrator");
            }
        });
    }

    private void reInitRecycler(List<JadwalKapalModel> jadwalKapalModels) {
        JadwalKapalAdapter adapter = new JadwalKapalAdapter(jadwalKapalModels);
        recyclerView.swapAdapter(adapter, false);
    }
}