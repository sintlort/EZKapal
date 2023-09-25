package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.Model.PemegangTicketModel;
import com.ezcats.ezkapal.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiFragment extends DialogFragment {

    private static final String TAG = "VERIFIKASI_FRAGMENT";

    TextView tanggal, nama, kode, status, asal, tujuan, waktu_asal, waktu_tujuan, kapal;
    String token, intentResults;
    MaterialButton verif,batal;

    PemegangTicketModel pemegangTicketModel;

    public interface HandleVerification{
        void verificationHandler();
    }

    HandleVerification handleVerification;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            handleVerification = (HandleVerification) getActivity();
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
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetDialogAnimationStyle;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ticket_2, container, false);
        tanggal = v.findViewById(R.id.tanggal_ticket_2);
        nama = v.findViewById(R.id.nama_verifikasi_tiket_2);
        kode = v.findViewById(R.id.kode_tiket_2);
        status = v.findViewById(R.id.status_tiket_2);
        verif = v.findViewById(R.id.button_verifikasi_2);
        batal = v.findViewById(R.id.button_batal_2);
        asal = v.findViewById(R.id.ticket_pelabuhan_asal);
        tujuan = v.findViewById(R.id.ticket_pelabuhan_tujuan);
        waktu_asal = v.findViewById(R.id.ticket_waktu_asal);
        waktu_tujuan = v.findViewById(R.id.ticket_waktu_tujuan);
        kapal = v.findViewById(R.id.ticket_nama_kapal);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");
        Bundle bundle = getArguments();
        pemegangTicketModel = (PemegangTicketModel) bundle.getSerializable("ticket_info");
        intentResults = bundle.getString("intentResults", "");

        initData();

        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
                Call<ResponseBody> call = transactionService.checkTicket(token,"application/json", "XMLHttpRequest", intentResults, pemegangTicketModel.getTanggal());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            if(response.code()==200){
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    String message = jsonObject.getString("message");
                                    if(message.equals("success")){
                                        Toast.makeText(getContext(), "Tiket telah diverifikasi", Toast.LENGTH_LONG).show();
                                        handleVerification.verificationHandler();
                                        getDialog().dismiss();
                                    } else {
                                        Toast.makeText(getContext(), "Tiket tidak ditemukan atau sudah digunakan", Toast.LENGTH_LONG).show();
                                        handleVerification.verificationHandler();
                                        getDialog().dismiss();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali \njika kesalahan terjadi berulang, harap menghubungi administrator", Toast.LENGTH_SHORT).show();
                                    Log.d("VERIF TICKET DATA", "onResponse: VERIF TICKET DATA API FAILED JSONEXCEPTION");
                                    e.printStackTrace();
                                    handleVerification.verificationHandler();
                                    getDialog().dismiss();
                                } catch (IOException e) {
                                    Toast.makeText(getContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali, \njika kesalahan terjadi berulang, harap menghubungi administrator", Toast.LENGTH_SHORT).show();
                                    Log.d("VERIF TICKET DATA", "onResponse: VERIF TICKET DATA API FAILED IOEXCEPTION");
                                    e.printStackTrace();
                                    handleVerification.verificationHandler();
                                    getDialog().dismiss();
                                }
                            } else {
                                Toast.makeText(getContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                                Log.d("VERIF TICKET DATA", "onResponse: VERIF TICKET DATA API NOT 200");
                                handleVerification.verificationHandler();
                                getDialog().dismiss();
                            }
                        } else {
                            Toast.makeText(getContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                            Log.d("VERIF TICKET DATA", "onResponse: VERIF TICKET DATA API NOT SUCCESSFUL");
                            handleVerification.verificationHandler();
                            getDialog().dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                        Log.d("VERIF TICKET DATA", "onResponse: VERIF TICKET DATA API ON FAILURE");
                        handleVerification.verificationHandler();
                        getDialog().dismiss();
                    }
                });
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleVerification.verificationHandler();
                getDialog().dismiss();
            }
        });
        return v;
    }

    private void initData() {
        tanggal.setText(pemegangTicketModel.getTanggal());
        nama.setText(pemegangTicketModel.getNama_pemegang_tiket());
        kode.setText(pemegangTicketModel.getKode_tiket());
        status.setText(pemegangTicketModel.getStatus());
        asal.setText(pemegangTicketModel.getPelabuhan_asal());
        waktu_asal.setText(pemegangTicketModel.getWaktu_asal());
        tujuan.setText(pemegangTicketModel.getPelabuhan_tujuan());
        waktu_tujuan.setText(pemegangTicketModel.getWaktu_tujuan());
        kapal.setText(pemegangTicketModel.getKapal());
        if(pemegangTicketModel.getStatus().equals("Used") || pemegangTicketModel.getStatus().equals("Expired")){
            verif.setClickable(false);
            status.setTextColor(getResources().getColor(R.color.red, null));
        } else {
            status.setTextColor(getResources().getColor(R.color.green, null));
        }
    }
}