package com.ezcats.ezkapal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TicketService;
import com.ezcats.ezkapal.Activity.PelabuhanActivity;
import com.ezcats.ezkapal.Activity.SearchTicketActivity;
import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Fragment.PelabuhanFragment;
import com.ezcats.ezkapal.Fragment.PelabuhanFragmentTujuan;
import com.ezcats.ezkapal.Fragment.PenumpangSelector;
import com.ezcats.ezkapal.Model.GolonganModel;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ticket_kapal extends Fragment implements PelabuhanFragment.SendData, PelabuhanFragmentTujuan.SendDataTujuan, AdapterView.OnItemSelectedListener, PenumpangSelector.SendValue {

    EditText pelabuhanAsal, pelabuhanTujuan, golonganKendaraan, nomorPolisi, mTanggalKeberangkatan, mPenumpang;
    String tanggalKeberangkatan, jumlahPenumpang, noPol, dateValue, token;
    ConstraintLayout constraintLayout;

    DatePickerDialog.OnDateSetListener onDateSetListener;

    int idAsal, idTujuan, idGolongan;
    public boolean isExpand = false;

    Button mBtn_cari;

    Spinner mSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ticket_kapal, container, false);

        nomorPolisi = v.findViewById(R.id.nomor_kendaraan);
        mPenumpang = v.findViewById(R.id.penumpang);
        constraintLayout = v.findViewById(R.id.expandableGolongan);
        constraintLayout.setVisibility(isExpand ? View.VISIBLE : View.GONE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");

        setDateClick(v);

        setSpinner(v);

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
                penumpangSelector.setTargetFragment(ticket_kapal.this, 2);
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

        mTanggalKeberangkatan = v.findViewById(R.id.tanggal_keberangkatan);

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
        mBtn_cari = v.findViewById(R.id.btn_cari);
        mBtn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idAsal == 0 && idTujuan == 0 && dateValue == null && jumlahPenumpang == null) {
                    Toast.makeText(getContext(), "Harap mengisi semua data yang diperlukan!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getContext(), SearchTicketActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("id_golongan", idGolongan);
                    mBundle.putString("tipe_kapal", "feri");
                    mBundle.putInt("asal_pelabuhan", idAsal);
                    mBundle.putInt("tujuan_pelabuhan", idTujuan);
                    mBundle.putString("date", dateValue);
                    mBundle.putString("nomor_polisi", nomorPolisi.getText().toString());
                    mBundle.putInt("jumlah_penumpang", Integer.parseInt(mPenumpang.getText().toString()));
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void setPelabuhanClick(View v) {
        pelabuhanAsal = v.findViewById(R.id.pelabuhan_asal);
        pelabuhanAsal.setFocusable(false);
        pelabuhanAsal.setClickable(true);
        pelabuhanAsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PelabuhanFragment pelabuhanFragment = new PelabuhanFragment();
                pelabuhanFragment.setTargetFragment(ticket_kapal.this, 1);
                pelabuhanFragment.show(getParentFragment().getChildFragmentManager(), "PELABUHAN_DIALOG_ASAL");
            }
        });

        pelabuhanTujuan = v.findViewById(R.id.pelabuhan_tujuan);
        pelabuhanTujuan.setFocusable(false);
        pelabuhanTujuan.setClickable(true);
        pelabuhanTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PelabuhanFragmentTujuan pelabuhanFragmentTujuan = new PelabuhanFragmentTujuan();
                pelabuhanFragmentTujuan.setTargetFragment(ticket_kapal.this, 2);
                pelabuhanFragmentTujuan.show(getParentFragment().getChildFragmentManager(), "PELABUHAN_DIALOG_TUJUAN");
            }
        });
    }

    private void setSpinner(View v) {
        mSpinner = v.findViewById(R.id.tipe_jasa);
        TicketService ticketService = RetrofitClient.getRetrofitInstance().create(TicketService.class);
        Call<List<GolonganModel>> call = ticketService.getGolongan(token, "application/json", "XMLHttpRequest");
        call.enqueue(new Callback<List<GolonganModel>>() {
            @Override
            public void onResponse(Call<List<GolonganModel>> call, Response<List<GolonganModel>> response) {
                assingDataSpinner(response.body());
            }

            @Override
            public void onFailure(Call<List<GolonganModel>> call, Throwable t) {

            }
        });
    }

    private void assingDataSpinner(List<GolonganModel> golonganModels) {
        ArrayAdapter<GolonganModel> mAdapter = new ArrayAdapter<GolonganModel>(getContext(), android.R.layout.simple_spinner_item, golonganModels);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(this);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        GolonganModel golonganModel = (GolonganModel) adapterView.getSelectedItem();
        if(golonganModel.getGolongan().equals("Penumpang")){
            isExpand = false;
            constraintLayout.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        } else {
            isExpand = true;
            constraintLayout.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        }
        this.idGolongan = golonganModel.getId_golongan();
        Log.d(TAG, "onItemSelected: "+idGolongan);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void sendJumlahPenumpang(int number) {
        mPenumpang.setText(String.valueOf(number));
    }
}