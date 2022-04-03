package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatalkanTransaksiFragment extends DialogFragment {

    Button batal_btn;
    int id_detail;

    private static final String TAG = "BATALKAN_TRANSAKSI_FRAGMENT";

    public interface StatusPembatalan{
        void checkStatus(String status);
    }

    public StatusPembatalan statusPembatalan;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            statusPembatalan = (StatusPembatalan) getActivity();
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
            float width = getResources().getDimension(R.dimen.batal_transaksi_width);
            float height = getResources().getDimension(R.dimen.batal_transaksi_height);
            dialog.getWindow().setLayout(Math.round(width), Math.round(height));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_batalkan_transaksi, container, false);
        batal_btn = v.findViewById(R.id.continue_batalkan);
        Bundle bundle = getArguments();
        id_detail = bundle.getInt("id_detail",0);
        batal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(getString(R.string.token), "");
                TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
                Call<ResponseBody> call = transactionService.cancelPemesanan(token,"application/json", "XMLHttpRequest",id_detail);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            if(response.code() == 200){
                                statusPembatalan.checkStatus("dibatalkan");
                                getDialog().dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        return v;
    }
}