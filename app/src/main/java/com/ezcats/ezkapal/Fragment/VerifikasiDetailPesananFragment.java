package com.ezcats.ezkapal.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezcats.ezkapal.R;

public class VerifikasiDetailPesananFragment extends DialogFragment {

    private static final String TAG = "VERIFIKASI_DETAIL_PESANAN_FRAGMENT";

    public interface Acceptance {
        void acceptanceOfOrders(boolean value);
    }

    public Acceptance acceptance;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            acceptance = (Acceptance) getActivity();
        } catch (ClassCastException e) {
            Log.d(TAG, "onAttach: " + e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Apakah anda yakin melanjutkan pemesanan ini ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        acceptance.acceptanceOfOrders(true);
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                acceptance.acceptanceOfOrders(false);
            }
        });
        return builder.create();
    }
}