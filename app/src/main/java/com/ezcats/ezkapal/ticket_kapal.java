package com.ezcats.ezkapal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ezcats.ezkapal.Activity.PelabuhanActivity;
import com.ezcats.ezkapal.Activity.SearchTicketActivity;
import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Fragment.PelabuhanFragment;
import com.ezcats.ezkapal.Fragment.PelabuhanFragmentTujuan;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;


public class ticket_kapal extends Fragment implements PelabuhanFragment.SendData, PelabuhanFragmentTujuan.SendDataTujuan, AdapterView.OnItemSelectedListener {

    EditText pelabuhanAsal, pelabuhanTujuan, golonganKendaraan, nomorPolisi;

    Button mBtn_cari;

    Spinner mSpinner;
/*    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    String[] list;
    ArrayAdapter arrayAdapter;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ticket_kapal, container, false);

        golonganKendaraan = v.findViewById(R.id.golongan);
        nomorPolisi = v.findViewById(R.id.nomor_kendaraan);
/*        list = getResources().getStringArray(R.array.service_type);
        arrayAdapter = new ArrayAdapter(getContext(), R.layout.service_type_dropdown, list);
        autoCompleteTextView = v.findViewById(R.id.tipe_jasa_text_view);
        autoCompleteTextView.setAdapter(arrayAdapter);*/

        mSpinner = v.findViewById(R.id.tipe_jasa);
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(getContext(), R.array.service_type, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(this);

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

        mBtn_cari = v.findViewById(R.id.btn_cari);
        mBtn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchTicketActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void sendPelabuhanData(int id, String nama_pelabuhan) {
        Log.d(TAG, "sendPelabuhanData: " + id + " ; "+nama_pelabuhan);
        pelabuhanAsal.setText(nama_pelabuhan);
    }

    @Override
    public void sendPelabuhanDataTujuan(int id, String nama_pelabuhan) {
        Log.d(TAG, "sendPelabuhanDataTujuan: " + id + " ; "+nama_pelabuhan);
        pelabuhanTujuan.setText(nama_pelabuhan);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(i){
            case 1:
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                String texts = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), texts, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}