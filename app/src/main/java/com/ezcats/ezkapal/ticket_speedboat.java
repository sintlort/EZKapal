package com.ezcats.ezkapal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TicketService;
import com.ezcats.ezkapal.Activity.SearchTicketActivity;
import com.ezcats.ezkapal.Fragment.PelabuhanFragment;
import com.ezcats.ezkapal.Fragment.PelabuhanFragmentTujuan;
import com.ezcats.ezkapal.Fragment.PenumpangSelector;
import com.ezcats.ezkapal.Model.GolonganModel;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ticket_speedboat extends Fragment implements PelabuhanFragment.SendData, PelabuhanFragmentTujuan.SendDataTujuan,  PenumpangSelector.SendValue {

    EditText pelabuhanAsal, pelabuhanTujuan, mTanggalKeberangkatan, mPenumpang;
    String tanggalKeberangkatan, jumlahPenumpang, dateValue, token;

    DatePickerDialog.OnDateSetListener onDateSetListener;

    int idAsal, idTujuan;

    Button mBtn_cari;

    Spinner mSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ticket_speedboat, container, false);

        mPenumpang = v.findViewById(R.id.penumpang_s);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");

        setDateClick(v);

        setPelabuhanClick(v);

        setPenumpangClick();

        setBtnCari(v);

        return v;
    }

    private void setPenumpangClick() {
        mPenumpang.setFocusable(false);
        mPenumpang.setClickable(true);
        mPenumpang.setText("1");
        PenumpangSelector penumpangSelector = new PenumpangSelector();
        Bundle bundle = new Bundle();
        mPenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                penumpangSelector.setTargetFragment(ticket_speedboat.this, 2);
                if (!(mPenumpang.getText().toString() == "")) {
                    bundle.putInt("penumpangValue", Integer.parseInt(mPenumpang.getText().toString()));
                } else {
                    bundle.putInt("penumpangValue", 0);

                }
                penumpangSelector.setArguments(bundle);
                penumpangSelector.show(getParentFragment().getChildFragmentManager(), "PENUMPANG_SELECTOR");
            }
        });
    }

    private void setDateClick(View v) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        mTanggalKeberangkatan = v.findViewById(R.id.tanggal_keberangkatan_s);

        mTanggalKeberangkatan.setFocusable(false);
        mTanggalKeberangkatan.setClickable(true);
        mTanggalKeberangkatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), onDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int month = i1 + 1;
                String date = i2 + " / " + month + " / " + i;
                dateValue = i + "-" + month + "-" + i2;
                Log.d(TAG, "onDateSet: " + dateValue);
                mTanggalKeberangkatan.setText(date);
            }
        };
    }

    private void setBtnCari(View v) {
        mBtn_cari = v.findViewById(R.id.btn_cari_s);
        mBtn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idAsal == 0 && idTujuan == 0 && dateValue == null && jumlahPenumpang == null) {
                    Toast.makeText(getContext(), "Harap mengisi semua data yang diperlukan!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getContext(), SearchTicketActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("tipe_kapal", "speedboat");
                    mBundle.putInt("asal_pelabuhan", idAsal);
                    mBundle.putInt("tujuan_pelabuhan", idTujuan);
                    mBundle.putString("date", dateValue);
                    mBundle.putString("nomor_polisi", "");
                    mBundle.putInt("jumlah_penumpang", Integer.parseInt(mPenumpang.getText().toString()));
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void setPelabuhanClick(View v) {
        pelabuhanAsal = v.findViewById(R.id.pelabuhan_asal_s);
        pelabuhanAsal.setFocusable(false);
        pelabuhanAsal.setClickable(true);
        pelabuhanAsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PelabuhanFragment pelabuhanFragment = new PelabuhanFragment();
                pelabuhanFragment.setTargetFragment(ticket_speedboat.this, 1);
                pelabuhanFragment.show(getParentFragment().getChildFragmentManager(), "PELABUHAN_DIALOG_ASAL");
            }
        });

        pelabuhanTujuan = v.findViewById(R.id.pelabuhan_tujuan_s);
        pelabuhanTujuan.setFocusable(false);
        pelabuhanTujuan.setClickable(true);
        pelabuhanTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PelabuhanFragmentTujuan pelabuhanFragmentTujuan = new PelabuhanFragmentTujuan();
                pelabuhanFragmentTujuan.setTargetFragment(ticket_speedboat.this, 2);
                pelabuhanFragmentTujuan.show(getParentFragment().getChildFragmentManager(), "PELABUHAN_DIALOG_TUJUAN");
            }
        });
    }

    @Override
    public void sendPelabuhanData(int id, String nama_pelabuhan) {
        Log.d(TAG, "sendPelabuhanData: " + id + " ; " + nama_pelabuhan);
        pelabuhanAsal.setText(nama_pelabuhan);
        this.idAsal = id;
    }

    @Override
    public void sendPelabuhanDataTujuan(int id, String nama_pelabuhan) {
        Log.d(TAG, "sendPelabuhanDataTujuan: " + id + " ; " + nama_pelabuhan);
        pelabuhanTujuan.setText(nama_pelabuhan);
        this.idTujuan = id;
    }

    @Override
    public void sendJumlahPenumpang(int number) {
        mPenumpang.setText(String.valueOf(number));
    }
}