package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Adapter.PenumpangAdapter;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;
import com.ezcats.ezkapal.Model.PenumpangModel;
import com.ezcats.ezkapal.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailPesanan extends AppCompatActivity implements PenumpangAdapter.OnPenumpangView, PenumpangFragment.SendDataPenumpang {

    RecyclerView recyclerView;

    List<String> namaList = new ArrayList<>();
    List<PenumpangModel> penumpangModels;
    PenumpangAdapter penumpangAdapter;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        recyclerView = findViewById(R.id.recycler_penumpang);
        initData();
        initView();
        Log.d("INITDATA", "initData: "+penumpangModels.size());

    }

    private void initView() {
        penumpangAdapter = new PenumpangAdapter(penumpangModels, this::onPenumpangClick);
        recyclerView.setAdapter(penumpangAdapter);
    }

    private void initData() {
        penumpangModels = new ArrayList<>();
        while(i <= 3){
            penumpangModels.add(new PenumpangModel("",""));
            i++;
        }
    }

    @Override
    public void onPenumpangClick(int position, String name, String ktp_number) {
        PenumpangFragment penumpangFragment = new PenumpangFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("name",name);
        bundle.putString("ktp_number", ktp_number);
        penumpangFragment.setArguments(bundle);
        penumpangFragment.show(getSupportFragmentManager(), "PENUMPANG_FRAGMENT");
    }

    @Override
    public void sendData(int position, String nama_penumpang, String ktp_penumpang) {
        penumpangModels.set(position, new PenumpangModel(nama_penumpang, ktp_penumpang));
        penumpangAdapter.notifyDataSetChanged();
    }
}