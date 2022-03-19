package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Adapter.SearchTicketAdapter;
import com.ezcats.ezkapal.Model.TicketModel;
import com.ezcats.ezkapal.R;

import java.util.ArrayList;
import java.util.List;

public class SearchTicketActivity extends AppCompatActivity implements SearchTicketAdapter.OnPilihTiketListener {

    RecyclerView recyclerView;
    List<TicketModel> ticketModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ticket);

        initData();
        startRecycler();
    }

    private void startRecycler() {
        SearchTicketAdapter adapter = new SearchTicketAdapter(ticketModelList, this::onClickPilihTiket);
        recyclerView = findViewById(R.id.ticket_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {
        ticketModelList = new ArrayList<>();
        ticketModelList.add(new TicketModel(1,"Padangbai","Kusamba","PDB","KSB","Beroperasi","Beroperasi","Dermaga 1","Dermaga Kusamba","60 Menit","15-03-2022","Selasa","75.000","Caspla","09:00","10:00"));
        ticketModelList.add(new TicketModel(2,"Kusamba","Padangbai","KSB","PDB","Beroperasi","Beroperasi","Dermaga Kusamba","Dermaga 1","60 Menit","15-03-2022","Selasa","75.000","Caspla","10:30","11:30"));
        ticketModelList.add(new TicketModel(3,"Sanur","Nusa Penida","SNR","NPD","Beroperasi","Beroperasi","Dermaga Sanur","Dermaga Nusa Penida","30 Menit","15-03-2022","Selasa","95.000","Mola Mola","09:00","09:30"));
        ticketModelList.add(new TicketModel(4,"Nusa Penida","Sanur","NPD","SNR","Beroperasi","Beroperasi","Dermaga Nusa Penida","Dermaga Sanur","30 Menit","15-03-2022","Selasa","95.000","Mola Mola","09:00","10:30"));
    }


    @Override
    public void onClickPilihTiket(Class a) {
        Intent intent = new Intent(this, a);
        startActivity(intent);
    }
}