package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.R;

import java.util.ArrayList;
import java.util.List;

public class PelabuhanFragmentTujuan extends DialogFragment implements PelabuhanAdapter.OnPelabuhanListener{

    private static final String TAG = "PELABUHAN_DIALOG_TUJUAN";

    RecyclerView recyclerView;
    List<PelabuhanModel> pelabuhanModelList;

    public interface SendDataTujuan{
        void sendPelabuhanDataTujuan(int id, String nama_pelabuhan);
    }

    public SendDataTujuan mSendData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mSendData = (SendDataTujuan) getTargetFragment();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: "+e.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pelabuhan, container, false);
        recyclerView = v.findViewById(R.id.pelabuhan_recycler);

        initData();
        startRecycler();

        return v;
    }

    private void initData() {
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
    }

    @Override
    public void OnPelabuhanClick(int id, String nama_pelabuhan) {
        Log.d(TAG, "OnPelabuhanClick: " + id + " ; " + nama_pelabuhan);
        mSendData.sendPelabuhanDataTujuan(id, nama_pelabuhan);
        getDialog().dismiss();
    }
}