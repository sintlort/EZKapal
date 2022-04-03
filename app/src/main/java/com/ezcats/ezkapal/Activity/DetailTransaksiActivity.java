package com.ezcats.ezkapal.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.APIClient.Service.UploadImageService;
import com.ezcats.ezkapal.Adapter.PenumpangAdapter;
import com.ezcats.ezkapal.Fragment.BatalkanTransaksiFragment;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;
import com.ezcats.ezkapal.Model.PenumpangModel;
import com.ezcats.ezkapal.Model.TicketModel;
import com.ezcats.ezkapal.Model.TransaksiModel;
import com.ezcats.ezkapal.Model.UserModel;
import com.ezcats.ezkapal.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransaksiActivity extends AppCompatActivity implements PenumpangAdapter.OnPenumpangView, BatalkanTransaksiFragment.StatusPembatalan {

    TextView kapal, tanggal, harga, pelabuhanAsal, pelabuhanTujuan, waktuAsal, waktuTujuan, namaPemesan, emailPemesan, teleponPemesan, metodePembayaran, noRekening;

    Button upload, batal;
    TransaksiModel transaksiModel;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);
        kapal = findViewById(R.id.nama_kapal_transaksi);
        tanggal = findViewById(R.id.mTanggal_keberangkatan_transaksi);
        harga = findViewById(R.id.mHarga_transaksi);
        pelabuhanAsal = findViewById(R.id.mStart_Pelabuhan_transaksi);
        pelabuhanTujuan = findViewById(R.id.mEnd_Pelabuhan_transaksi);
        waktuAsal = findViewById(R.id.mStart_Clock_transaksi);
        waktuTujuan = findViewById(R.id.mEnd_Clock_transaksi);
        namaPemesan = findViewById(R.id.nama_pemesan_transaksi);
        emailPemesan = findViewById(R.id.email_pemesan_transaksi);
        teleponPemesan = findViewById(R.id.telepon_pemesan_transaksi);
        upload = findViewById(R.id.upload_bukti_pembayaran);
        batal = findViewById(R.id.batalkan_transaksi);
        metodePembayaran = findViewById(R.id.metode_pembayaran_transaksi);
        noRekening = findViewById(R.id.no_rekening_transaksi);

        recyclerView = findViewById(R.id.recycler_penumpang_transaksi);

        transaksiModel = (TransaksiModel) getIntent().getSerializableExtra("Transaksi");
        initData(transaksiModel);
        initUser();
        initPenumpang();

        if(!transaksiModel.getStatus().equals("menunggu pembayaran")){
            batal.setVisibility(View.GONE);
        } else {
            batal.setVisibility(View.VISIBLE);
        }

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batalkanTransaksi();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
    }

    private void batalkanTransaksi() {
        BatalkanTransaksiFragment batalkanTransaksiFragment = new BatalkanTransaksiFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id_detail",transaksiModel.getId_detail());
        batalkanTransaksiFragment.setArguments(bundle);
        batalkanTransaksiFragment.show(getSupportFragmentManager(), "BATALKAN_TRANSAKSI_FRAGMENT");
    }

    private void pickImage(){
        ImagePicker.Companion.with(DetailTransaksiActivity.this)
                .galleryOnly()	//User can only select image from Gallery
                .start(10);	//Default Request Code is ImagePicker.REQUEST_CODE
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            Uri file = data.getData();
            uploadImage(file, transaksiModel.getId_detail());
        }
    }

    private void uploadImage(Uri uris, int id_details) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        File file = new File(uris.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        RequestBody id_detail = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(id_details));
        UploadImageService uploadImageService = RetrofitClient.getRetrofitInstance().create(UploadImageService.class);
        Call<ResponseBody> call = uploadImageService.uploadImage("application/json", "XMLHttpRequest", token, parts, id_detail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    upload.setText(getString(R.string.upload_ulang));
                } else {
                    upload.setText(getString(R.string.upload_transaksi));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void initUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
        Call<UserModel> userModelCall = accountService.getUser("application/json", "XMLHttpRequest", token);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    namaPemesan.setText(response.body().getNama_user());
                    emailPemesan.setText(response.body().getEmail());
                    teleponPemesan.setText(response.body().getNohp());
                } else {
                    namaPemesan.setText("");
                    emailPemesan.setText("");
                    teleponPemesan.setText("");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("FAILURE", "onFailure: " + t);
            }
        });
    }

    private void initPenumpang() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<List<PenumpangModel>> call = transactionService.getPenumpang(token, "application/json", "XMLHttpRequest", transaksiModel.getId_detail());
        call.enqueue(new Callback<List<PenumpangModel>>() {
            @Override
            public void onResponse(Call<List<PenumpangModel>> call, Response<List<PenumpangModel>> response) {
                if (response.isSuccessful()) {
                    initRecycler(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PenumpangModel>> call, Throwable t) {

            }
        });
    }

    private void initRecycler(List<PenumpangModel> response) {
        PenumpangAdapter penumpangAdapter = new PenumpangAdapter(response, this::onPenumpangClick);
        recyclerView.setAdapter(penumpangAdapter);
    }

    private void initData(TransaksiModel transaksiModel) {
        kapal.setText(transaksiModel.getNama_kapal());
        tanggal.setText(transaksiModel.getTanggal());
        harga.setText(transaksiModel.getHarga());
        pelabuhanAsal.setText(transaksiModel.getNama_asal());
        pelabuhanTujuan.setText(transaksiModel.getNama_tujuan());
        waktuAsal.setText(transaksiModel.getWaktu_berangkat_asal());
        waktuTujuan.setText(transaksiModel.getWaktu_berangkat_tujuan());
        metodePembayaran.setText(transaksiModel.getMetode_pembayaran().replace("_", " ").toUpperCase());
        noRekening.setText(transaksiModel.getNomor_rekening());
        if(transaksiModel.getBukti() != null){
            upload.setText(getString(R.string.upload_ulang));
        } else {
            upload.setText(getString(R.string.upload_transaksi));
        }
    }

    @Override
    public void onPenumpangClick(int position, String name, String ktp_number) {

    }

    @Override
    public void checkStatus(String status) {
        transaksiModel.setStatus("dibatalkan");
        if(!transaksiModel.getStatus().equals("menunggu pembayaran")){
            batal.setVisibility(View.GONE);
            upload.setVisibility(View.GONE);
        } else {
            batal.setVisibility(View.VISIBLE);
            upload.setVisibility(View.VISIBLE);
        }
    }
}