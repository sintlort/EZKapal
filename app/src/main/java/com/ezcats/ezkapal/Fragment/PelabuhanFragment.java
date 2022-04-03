package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.PelabuhanService;
import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PelabuhanFragment extends DialogFragment implements PelabuhanAdapter.OnPelabuhanListener{

    private static final String TAG = "PELABUHAN_DIALOG_ASAL";

    RecyclerView recyclerView;

    public interface SendData{
        void sendPelabuhanData(int id, String nama_pelabuhan);
    }

    public SendData mSendData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mSendData = (SendData) getTargetFragment();
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

        return v;
    }

    private void initData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference),Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        if(!token.equals("")){
            PelabuhanService service = RetrofitClient.getRetrofitInstance().create(PelabuhanService.class);
            Call<List<PelabuhanModel>> call = service.getListPelabuhan("application/json","XMLHttpRequest","application/json",token);
            call.enqueue(new Callback<List<PelabuhanModel>>() {
                @Override
                public void onResponse(Call<List<PelabuhanModel>> call, Response<List<PelabuhanModel>> response) {
                    startRecycler(response.body());
                }

                @Override
                public void onFailure(Call<List<PelabuhanModel>> call, Throwable t) {
                    Toast.makeText(getContext(), "Harap memeriksa koneksi internet anda", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Harap login terlebih dahulu", Toast.LENGTH_LONG).show();
        }
    }

    private void startRecycler(List<PelabuhanModel> pelabuhanModels) {
        PelabuhanAdapter pelabuhanAdapter = new PelabuhanAdapter(pelabuhanModels, this::OnPelabuhanClick);
        recyclerView.setAdapter(pelabuhanAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void OnPelabuhanClick(int id, String nama_pelabuhan) {
        Log.d(TAG, "OnPelabuhanClick: " + id + " ; " + nama_pelabuhan);
        mSendData.sendPelabuhanData(id, nama_pelabuhan);
        getDialog().dismiss();
    }
}