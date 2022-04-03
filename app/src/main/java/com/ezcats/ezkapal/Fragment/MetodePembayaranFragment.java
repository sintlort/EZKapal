package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
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
import com.ezcats.ezkapal.APIClient.Service.PemesananService;
import com.ezcats.ezkapal.Adapter.MetodePembayaranAdapter;
import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Model.MetodePembayaranModel;
import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetodePembayaranFragment extends DialogFragment implements MetodePembayaranAdapter.OnMetodePembayaranListener {


    private static final String TAG = "METODE_PEMBAYARAN_DIALOG";

    RecyclerView recyclerView;

    public interface MetodePembayaran{
        void selectMetode(int id, String method);
    }

    public MetodePembayaran metodePembayaran;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            metodePembayaran = (MetodePembayaran) getActivity();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_metode_pembayaran, container, false);
        recyclerView = v.findViewById(R.id.metode_pembayaran_recycler);
        initData();
        return v;
    }

    private void initData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference),Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        if(!token.equals("")){
            PemesananService service = RetrofitClient.getRetrofitInstance().create(PemesananService.class);
            Call<List<MetodePembayaranModel>> call = service.getMetodePembayaran(token, "application/json","XMLHttpRequest");
            call.enqueue(new Callback<List<MetodePembayaranModel>>() {
                @Override
                public void onResponse(Call<List<MetodePembayaranModel>> call, Response<List<MetodePembayaranModel>> response) {
                    startRecycler(response.body());
                }

                @Override
                public void onFailure(Call<List<MetodePembayaranModel>> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(), "Harap login terlebih dahulu", Toast.LENGTH_LONG).show();
        }
    }

    private void startRecycler(List<MetodePembayaranModel> list) {
        MetodePembayaranAdapter metodePembayaranAdapter = new MetodePembayaranAdapter(list, this::OnMetodeClicked);
        recyclerView.setAdapter(metodePembayaranAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void OnMetodeClicked(int id, String nama_metode) {
        metodePembayaran.selectMetode(id, nama_metode);
        getDialog().dismiss();
    }
}