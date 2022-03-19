package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PelabuhanActivity extends AppCompatActivity /*implements PelabuhanAdapter.OnPelabuhanListener*/ {

    RecyclerView recyclerView;
    List<PelabuhanModel> pelabuhanModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelabuhan);

        recyclerView = findViewById(R.id.pelabuhan_recycler);

        /*initData();
        startRecycler();*/
    }

    /*private void initData() {
        pelabuhanModelList = new ArrayList<>();
        pelabuhanModelList.add(new PelabuhanModel(1, "Pelabuhan 1", "Beroperasi", "AFF", "Kapal", "5", "Jalan Raya Sesetan Gang Gumuk Sari", "aaaaaa"));
        pelabuhanModelList.add(new PelabuhanModel(2, "Pelabuhan 2", "Beroperasi", "AFS", "Kapal & Speedboat", "2", "Jalan Raya Sesetan Gang Gumuk Sari", "aaaaaa"));
        pelabuhanModelList.add(new PelabuhanModel(3, "Pelabuhan 3", "Beroperasi", "AFD", "Speedboat", "1", "Jalan Raya Sesetan Gang Gumuk Sari", "aaaaaa"));
        pelabuhanModelList.add(new PelabuhanModel(4, "Pelabuhan 4", "Beroperasi", "AFG", "Kapal", "3", "Jalan Raya Sesetan Gang Gumuk Sari", "aaaaaa"));
    }

    private void startRecycler() {
        PelabuhanAdapter pelabuhanAdapter = new PelabuhanAdapter(pelabuhanModelList, this::OnPelabuhanClick);
        recyclerView.setAdapter(pelabuhanAdapter);
        recyclerView.setHasFixedSize(true);
    }*/

//    @Override
//    public void OnPelabuhanClick(int id, String nama_pelabuhan) {
//        Log.d(TAG, "OnPelabuhanClick: "+id + " ; " +nama_pelabuhan);
//    }
}