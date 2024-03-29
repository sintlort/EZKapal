package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.APIClient.Service.PemesananService;
import com.ezcats.ezkapal.Adapter.PelabuhanAdapter;
import com.ezcats.ezkapal.Adapter.PenumpangAdapter;
import com.ezcats.ezkapal.Fragment.MetodePembayaranFragment;
import com.ezcats.ezkapal.Fragment.PelabuhanFragment;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;
import com.ezcats.ezkapal.Fragment.PenumpangSelector;
import com.ezcats.ezkapal.Fragment.VerifikasiDetailPesananFragment;
import com.ezcats.ezkapal.MainActivity;
import com.ezcats.ezkapal.Model.JSONModel.TransaksiJSONModel;
import com.ezcats.ezkapal.Model.PenumpangModel;
import com.ezcats.ezkapal.Model.TicketModel;
import com.ezcats.ezkapal.Model.UserModel;
import com.ezcats.ezkapal.Model.VirtualAccountModel;
import com.ezcats.ezkapal.R;
import com.ezcats.ezkapal.ticket_kapal;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesanan extends AppCompatActivity implements PenumpangAdapter.OnPenumpangView, PenumpangFragment.SendDataPenumpang, MetodePembayaranFragment.MetodePembayaran, VerifikasiDetailPesananFragment.Acceptance {

    RecyclerView recyclerView;
    TextView nama_kapal, harga, tanggal, asal, tujuan, berangkat, sampai;
    TextView nama_pemesan, email_pemesan, telepon_pemesan, metode_pembayaran_pemesanan;
    CardView metode_pembayaran_card;
    private int id_metode = 0;
    private int id_detail = 0;
    private String tanggal_input, nomor_polisi;
    int jumlah_penumpang;

    List<String> namaList = new ArrayList<>();
    List<PenumpangModel> penumpangModels;
    PenumpangAdapter penumpangAdapter;

    MaterialButton pesanTiket;

    int i = 1;
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
        recyclerView = findViewById(R.id.recycler_penumpang);
        nama_kapal = findViewById(R.id.nama_kapal);
        harga = findViewById(R.id.mHarga);
        tanggal = findViewById(R.id.mTanggal_keberangkatan);
        asal = findViewById(R.id.mStart_Pelabuhan);
        tujuan = findViewById(R.id.mEnd_Pelabuhan);
        berangkat = findViewById(R.id.mStart_Clock);
        sampai = findViewById(R.id.mEnd_Clock);
        nama_pemesan = findViewById(R.id.nama_pemesan);
        email_pemesan = findViewById(R.id.email_pemesan);
        telepon_pemesan = findViewById(R.id.telepon_pemesan);
        pesanTiket = findViewById(R.id.pesan_tiket);
        metode_pembayaran_pemesanan = findViewById(R.id.metode_pembayaran_pemesanan);
        metode_pembayaran_card = findViewById(R.id.card_view_metode_pembayaran);

        TicketModel ticketModel = (TicketModel) getIntent().getSerializableExtra("Ticket");
        this.jumlah_penumpang = getIntent().getIntExtra("jumlah_penumpang", 0);
        this.nomor_polisi = getIntent().getStringExtra("nomor_polisi");
        this.id_detail = ticketModel.getId_detail();
        this.tanggal_input = ticketModel.getTanggal();
        initData(jumlah_penumpang);
        initAllView(ticketModel);
        initRecView();

        metode_pembayaran_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MetodePembayaranFragment metodePembayaranFragment = new MetodePembayaranFragment();
                metodePembayaranFragment.show(getSupportFragmentManager(), "METODE_PEMBAYARAN_DIALOG");
            }
        });

        pesanTiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifikasiDetailPesananFragment verifikasiDetailPesananFragment = new VerifikasiDetailPesananFragment();
                verifikasiDetailPesananFragment.show(getSupportFragmentManager(), "VERIFIKASI_DETAIL_PESANAN_FRAGMENT");
            }
        });
    }

    private void commitPemesanan(int jumlah_penumpang) {
        if (checkPenumpangData(jumlah_penumpang)) {
            if (id_metode != 0) {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(getString(R.string.token), "");

                //GET PENUMPANG DATA
                JSONArray jarryName = new JSONArray();
                JSONArray jarryCard = new JSONArray();
                for(int is = 0; is<penumpangModels.size();is++){
                    jarryName.put(penumpangModels.get(is).getNamaPenumpang());
                    jarryCard.put(penumpangModels.get(is).getKtpPenumpang());
                }
                PemesananService pemesananService = RetrofitClient.getRetrofitInstance().create(PemesananService.class);
                Call<TransaksiJSONModel> call = pemesananService.transactionCommited(token, "application/json", "XMLHttpRequest", id_detail, jumlah_penumpang, id_metode, nomor_polisi, tanggal_input,jarryName.toString(), jarryCard.toString());
                call.enqueue(new Callback<TransaksiJSONModel>() {
                    @Override
                    public void onResponse(Call<TransaksiJSONModel> call, Response<TransaksiJSONModel> response) {
                        Log.d("PEMESANANAPI", "onResponse: success");
                        Log.d("PEMESANANAPI3", "onResponse2: "+response.body().getMessage());
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                String message = response.body().getMessage();
                                if (message.equals("success")) {
                                    completion(response.body().getMidtransModel().getVirtualAccountModel(), response.body().getExpiry());
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembalis1", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembalis2", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TransaksiJSONModel> call, Throwable t) {
                        Log.d("PEMESANANONFAILURE", "onFailure: "+t.getMessage());
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembalis3", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "Harap memilih metode pembayaran", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void completion(List<VirtualAccountModel> virtualAccountModels, String expiry) {
        Intent intent = new Intent(getApplicationContext(), PesananSukses.class);
        intent.putExtra("va_number", virtualAccountModels.get(0));
        intent.putExtra("expiry_time", expiry);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean checkPenumpangData(int jumlah) {
        int i2 = 0;
        int check = 0;
        while (i2 < penumpangModels.size()) {
            PenumpangModel penumpangModel = penumpangModels.get(i2);
            if (!penumpangModel.getNamaPenumpang().equals("") && !penumpangModel.getKtpPenumpang().equals("")) {
                penumpangModel.setCard_color("#ffffff");
                penumpangModels.set(i2, penumpangModel);
                penumpangAdapter.notifyDataSetChanged();
                try{
                    double nops = Long.parseLong(penumpangModel.getKtpPenumpang());
                } catch (NumberFormatException e) {
                    penumpangModel.setCard_color("#ffffe0");
                    penumpangModels.set(i2, penumpangModel);
                    penumpangAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Nomor handphone tidak boleh huruf", Toast.LENGTH_LONG).show();
                    check++;
                } catch (Exception exception){
                    Toast.makeText(getApplicationContext(), "Nomor handphone tidak boleh huruf", Toast.LENGTH_LONG).show();
                    check++;
                }
                i2++;
            } else {
                penumpangModel.setCard_color("#ffffe0");
                penumpangModels.set(i2, penumpangModel);
                penumpangAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Harap mengisi semua data penumpang", Toast.LENGTH_LONG).show();
            }
            i2++;
        }
        if(check>0){
            return false;
        }
        return true;
    }

    private void initAllView(TicketModel ticketModel) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.token), "");
        AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
        Call<UserModel> userModelCall = accountService.getUser("application/json", "XMLHttpRequest", token);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                nama_pemesan.setText(response.body().getNama_user());
                email_pemesan.setText(response.body().getEmail());
                telepon_pemesan.setText(response.body().getNohp());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        nama_kapal.setText(ticketModel.getNama_kapal());
        harga.setText(ticketModel.getHarga());
        tanggal.setText(ticketModel.getTanggal());
        asal.setText(ticketModel.getNama_asal());
        tujuan.setText(ticketModel.getNama_tujuan());
        berangkat.setText(ticketModel.getWaktu_berangkat_asal());
        sampai.setText(ticketModel.getWaktu_berangkat_tujuan());
    }

    private void initRecView() {
        penumpangAdapter = new PenumpangAdapter(penumpangModels, this::onPenumpangClick);
        recyclerView.setAdapter(penumpangAdapter);
    }

    private void initData(int jumlah) {
        penumpangModels = new ArrayList<>();
        while (i <= jumlah) {
            penumpangModels.add(new PenumpangModel("", "", "#ffffff"));
            i++;
        }
    }

    @Override
    public void onPenumpangClick(int position, String name, String ktp_number) {
        PenumpangFragment penumpangFragment = new PenumpangFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("name", name);
        bundle.putString("ktp_number", ktp_number);
        penumpangFragment.setArguments(bundle);
        penumpangFragment.show(getSupportFragmentManager(), "PENUMPANG_FRAGMENT");
    }

    @Override
    public void sendData(int position, String nama_penumpang, String ktp_penumpang) {
        penumpangModels.set(position, new PenumpangModel(nama_penumpang, ktp_penumpang, "#ffffff"));
        penumpangAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectMetode(int id, String method) {
        this.id_metode = id;
        metode_pembayaran_pemesanan.setText(method.replace("_", " ").toUpperCase());
    }

    @Override
    public void acceptanceOfOrders(boolean value) {
        if (value) {
            commitPemesanan(jumlah_penumpang);
        }
    }
}