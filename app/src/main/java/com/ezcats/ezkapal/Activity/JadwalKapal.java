package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TicketService;
import com.ezcats.ezkapal.Adapter.JadwalKapalAdapter;
import com.ezcats.ezkapal.Adapter.SearchTicketAdapter;
import com.ezcats.ezkapal.Model.JSONModel.JadwalKapalJSONModel;
import com.ezcats.ezkapal.Model.JadwalKapalModel;
import com.ezcats.ezkapal.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalKapal extends AppCompatActivity {

    RecyclerView recyclerView;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_kapal);

        recyclerView = findViewById(R.id.jadwal_recycler);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");
        getData();

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
}