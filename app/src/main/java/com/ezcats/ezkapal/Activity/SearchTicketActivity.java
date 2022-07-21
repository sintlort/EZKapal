package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TicketService;
import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Adapter.SearchTicketAdapter;
import com.ezcats.ezkapal.Model.TicketModel;
import com.ezcats.ezkapal.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTicketActivity extends AppCompatActivity implements SearchTicketAdapter.OnPilihTiketListener {

    private static final String TAG = "TICKET_ACTIVITY";
    RecyclerView recyclerView;
    String tipe_kapal, date, nomor_polisi;
    int asal_pelabuhan, tujuan_pelabuhan, jumlah_penumpang, idGolongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ticket);


        Bundle bundle = getIntent().getExtras();
        this.idGolongan = bundle.getInt("id_golongan");
        this.asal_pelabuhan = bundle.getInt("asal_pelabuhan");
        this.tujuan_pelabuhan = bundle.getInt("tujuan_pelabuhan");
        this.jumlah_penumpang = bundle.getInt("jumlah_penumpang");
        this.tipe_kapal = bundle.getString("tipe_kapal");
        this.date = bundle.getString("date");
        this.nomor_polisi = bundle.getString("nomor_polisi");
        Log.d(TAG, "onCreate: "+date+" | "+tipe_kapal);
        initData();

    }

    private void startRecycler(List<TicketModel> ticketModels) {
        SearchTicketAdapter adapter = new SearchTicketAdapter(ticketModels, this::onClickPilihTiket);
        recyclerView = findViewById(R.id.ticket_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference),Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        TicketService ticketService = RetrofitClient.getRetrofitInstance().create(TicketService.class);
        Call<List<TicketModel>> call = ticketService.getTicketData(token,"application/json","XMLHttpRequest",idGolongan,asal_pelabuhan, tujuan_pelabuhan, date, tipe_kapal);
        call.enqueue(new Callback<List<TicketModel>>() {
            @Override
            public void onResponse(Call<List<TicketModel>> call, Response<List<TicketModel>> response) {
                if (response.isSuccessful()){
                    startRecycler(response.body());
                } else {
                    Toast.makeText(getApplicationContext(), "Mohon persiksa internet anda!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TicketModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Mohon persiksa internet anda!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClickPilihTiket(Class classTarget, TicketModel ticketModel) {
        Intent intent = new Intent(this, classTarget);
        TicketModel ticketModel1 = ticketModel;
        int harga = 0;
        if(idGolongan > 2 ){
            harga = Integer.parseInt(ticketModel1.getHarga());
        } else {
            harga = Integer.parseInt(ticketModel1.getHarga()) * jumlah_penumpang;
        }
        ticketModel1.setHarga(String.valueOf(harga));
        intent.putExtra("Ticket",ticketModel1);
        intent.putExtra("nomor_polisi",nomor_polisi);
        intent.putExtra("jumlah_penumpang",jumlah_penumpang);
        startActivity(intent);
    }
}