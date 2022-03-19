package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezcats.ezkapal.R;

public class PenumpangFragment extends DialogFragment {

    EditText mNamaPenumpang, mKtpPenumpang;
    Button mInputPenumpang;

    private static final String TAG = "PENUMPANG_DIALOG";

    public interface SendDataPenumpang{
        void sendData(int position, String nama_penumpang, String ktp_penumpang);
    }

    public SendDataPenumpang mSendData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mSendData = (SendDataPenumpang) getActivity();
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
            float width = getResources().getDimension(R.dimen.penumpang_width);
            float height = getResources().getDimension(R.dimen.penumpang_height);
            dialog.getWindow().setLayout(Math.round(width), Math.round(height));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_penumpang, container, false);
        mNamaPenumpang = v.findViewById(R.id.input_nama_penumpang);
        mKtpPenumpang = v.findViewById(R.id.input_ktp_penumpang);
        mInputPenumpang = v.findViewById(R.id.button_input_penumpang);

        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        mNamaPenumpang.setText(bundle.getString("name"));
        mKtpPenumpang.setText(bundle.getString("ktp_number"));
        mInputPenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSendData.sendData(position,mNamaPenumpang.getText().toString(), mKtpPenumpang.getText().toString());
                getDialog().dismiss();
            }
        });
        return v;
    }
}