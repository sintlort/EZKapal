package com.ezcats.ezkapal.Fragment;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.TicketService;
import com.ezcats.ezkapal.Model.GolonganModel;
import com.ezcats.ezkapal.R;
import com.ezcats.ezkapal.ticket_kapal;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PencarianFragment extends DialogFragment implements PelabuhanFragment.SendData, PelabuhanFragmentTujuan.SendDataTujuan, AdapterView.OnItemSelectedListener {

    private static final String TAG = "PENCARIAN_DIALOG_FRAGMENT";

    EditText pelabuhan1, pelabuhan2, tanggal;
    ImageView hideDialog;
    Button cariButton;
    Spinner spinner;
    SharedPreferences sharedPreferences;
    String token;

    int id_asal, id_tujuan, id_golongan;

    String dateValue;

    public interface PencarianData{
        void sendData(int asal, int tujuan, int golongan, String date);
    }

    public PencarianData pencarianData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            pencarianData = (PencarianData) getActivity();
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
        View v = inflater.inflate(R.layout.fragment_pencarian, container, false);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference),Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");
        setView(v);

        return v;
    }

    private void setView(View v) {
        pelabuhan1 = v.findViewById(R.id.pelabuhan_asal);
        pelabuhan2 = v.findViewById(R.id.pelabuhan_tujuan);
        tanggal = v.findViewById(R.id.tanggal_keberangkatan);
        spinner = v.findViewById(R.id.tipe_jasa);
        hideDialog = v.findViewById(R.id.hide_button);
        cariButton = v.findViewById(R.id.btn_cari);

        initClick();
    }

    private void initClick() {
        initPelabuhan();

        initTanggal();

        initHideDialog();

        initCari();

        initSpinner();
    }

    private void initPelabuhan() {
        pelabuhan1.setFocusable(false);
        pelabuhan1.setClickable(true);
        pelabuhan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PelabuhanFragment pelabuhanFragment = new PelabuhanFragment();
                pelabuhanFragment.setTargetFragment(PencarianFragment.this, 1);
                pelabuhanFragment.show(getActivity().getSupportFragmentManager(), "PELABUHAN_DIALOG_ASAL");
            }
        });

        pelabuhan2.setFocusable(false);
        pelabuhan2.setClickable(true);
        pelabuhan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PelabuhanFragmentTujuan pelabuhanFragmentTujuan = new PelabuhanFragmentTujuan();
                pelabuhanFragmentTujuan.setTargetFragment(PencarianFragment.this, 2);
                pelabuhanFragmentTujuan.show(getActivity().getSupportFragmentManager(), "PELABUHAN_DIALOG_TUJUAN");
            }
        });
    }

    private void initTanggal() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tanggal.setFocusable(false);
        tanggal.setClickable(true);
        tanggal.setOnClickListener(new View.OnClickListener() {
            private DatePickerDialog.OnDateSetListener onDateSetListener;

            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int month = i1 + 1;
                        String date = i2 + " / " + month + " / " + i;
                        dateValue = i + "-" + month + "-" + i2;
                        Log.d(TAG, "onDateSet: " + dateValue);
                        tanggal.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void initHideDialog() {
        hideDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }

    private void initCari() {
        cariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void initSpinner() {
        TicketService ticketService = RetrofitClient.getRetrofitInstance().create(TicketService.class);
        Call<List<GolonganModel>> call = ticketService.getGolongan(token, "application/json", "XMLHttpRequest");
        call.enqueue(new Callback<List<GolonganModel>>() {
            @Override
            public void onResponse(Call<List<GolonganModel>> call, Response<List<GolonganModel>> response) {
                assignDataSpinner(response.body());
                Log.d(TAG, "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<List<GolonganModel>> call, Throwable t) {

            }
        });
    }

    private void assignDataSpinner(List<GolonganModel> golonganModels) {
        ArrayAdapter<GolonganModel> mAdapter = new ArrayAdapter<GolonganModel>(getContext(), android.R.layout.simple_spinner_item, golonganModels);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        GolonganModel golonganModel = (GolonganModel) adapterView.getSelectedItem();
        this.id_golongan = golonganModel.getId_golongan();
        Log.d(TAG, "onItemSelected: "+id_golongan);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getData() {
        pencarianData.sendData(id_asal,id_tujuan,id_golongan,dateValue);
        getDialog().dismiss();
    }

    @Override
    public void sendPelabuhanData(int id, String nama_pelabuhan) {
        pelabuhan1.setText(nama_pelabuhan);
        this.id_asal = id;
    }

    @Override
    public void sendPelabuhanDataTujuan(int id, String nama_pelabuhan) {
        pelabuhan2.setText(nama_pelabuhan);
        this.id_tujuan = id;
    }
}