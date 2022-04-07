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
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.APIClient.Service.TransactionService;
import com.ezcats.ezkapal.APIClient.Service.UploadImageService;
import com.ezcats.ezkapal.Adapter.PenumpangAdapter;
import com.ezcats.ezkapal.Fragment.BatalkanTransaksiFragment;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;
import com.ezcats.ezkapal.Fragment.ReviewFragment;
import com.ezcats.ezkapal.Model.PenumpangModel;
import com.ezcats.ezkapal.Model.ReviewModel;
import com.ezcats.ezkapal.Model.TicketModel;
import com.ezcats.ezkapal.Model.TransaksiModel;
import com.ezcats.ezkapal.Model.UserModel;
import com.ezcats.ezkapal.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransaksiActivity extends AppCompatActivity implements PenumpangAdapter.OnPenumpangView, BatalkanTransaksiFragment.StatusPembatalan, ReviewFragment.ReviewData {

    TextView kapal, tanggal, harga, pelabuhanAsal, pelabuhanTujuan, waktuAsal, waktuTujuan, namaPemesan, emailPemesan, teleponPemesan, metodePembayaran, noRekening;
    TextView status_transaksi;

    Button upload, batal, review, btnTicket;
    String token;
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
        review = findViewById(R.id.button_review);
        status_transaksi = findViewById(R.id.status_transaksi);
        btnTicket = findViewById(R.id.button_ticket_file);

        recyclerView = findViewById(R.id.recycler_penumpang_transaksi);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString(getString(R.string.token), "");

        transaksiModel = (TransaksiModel) getIntent().getSerializableExtra("Transaksi");
        initData(transaksiModel);

        if (transaksiModel.getStatus().equals("menunggu pembayaran") || transaksiModel.getStatus().equals("menunggu konfirmasi")) {
            btnTicket.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
            upload.setVisibility(View.VISIBLE);
            batal.setVisibility(View.VISIBLE);
            status_transaksi.setTextColor(getResources().getColor(R.color.light_blue, null));
            status_transaksi.setText(transaksiModel.getStatus().toUpperCase());
        } else if (transaksiModel.getStatus().equals("terkonfirmasi") || transaksiModel.getStatus().equals("digunakan")) {
            btnTicket.setVisibility(View.VISIBLE);
            review.setVisibility(View.VISIBLE);
            upload.setVisibility(View.GONE);
            batal.setVisibility(View.GONE);
            status_transaksi.setTextColor(getResources().getColor(R.color.green, null));
            status_transaksi.setText(transaksiModel.getStatus().toUpperCase());
        } else if (transaksiModel.getStatus().equals("expired") || transaksiModel.getStatus().equals("dibatalkan")) {
            btnTicket.setVisibility(View.GONE);
            upload.setVisibility(View.GONE);
            batal.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
            status_transaksi.setTextColor(getResources().getColor(R.color.red, null));
            status_transaksi.setText(transaksiModel.getStatus().toUpperCase());
        }

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReviewData();
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batalkanTransaksi();
            }
        });

        btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transaksiModel.getFile_tiket() != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2:8000/storage/ticket_pdf/"+transaksiModel.getFile_tiket()+".pdf"));
                    startActivity(browserIntent);
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
    }


    //PICKING IMAGE FUNCTION
    private void pickImage() {
        ImagePicker.Companion.with(DetailTransaksiActivity.this)
                .galleryOnly()    //User can only select image from Gallery
                .start(10);    //Default Request Code is ImagePicker.REQUEST_CODE
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            Uri file = data.getData();
            if (file != null) {
                uploadImage(file, transaksiModel.getId_detail());
            }
        }
    }


    //REFRESH DATA
    private void refreshDATA(int id_details) {
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<TransaksiModel> call = transactionService.refreshTransaction(token,"application/json", "XMLHttpRequest", id_details);
        call.enqueue(new Callback<TransaksiModel>() {
            @Override
            public void onResponse(Call<TransaksiModel> call, Response<TransaksiModel> response) {
                if(response.isSuccessful()){
                    if (response.code()==200){
                        initData(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiModel> call, Throwable t) {

            }
        });
    }


    //UPLOAD BUKTI FUNCTION
    private void uploadImage(Uri uris, int id_details) {
        File file = new File(uris.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        RequestBody id_detail = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_details));
        UploadImageService uploadImageService = RetrofitClient.getRetrofitInstance().create(UploadImageService.class);
        Call<ResponseBody> call = uploadImageService.uploadImage("application/json", "XMLHttpRequest", token, parts, id_detail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    upload.setText(getString(R.string.upload_ulang));
                    refreshDATA(id_details);
                } else {
                    upload.setText(getString(R.string.upload_transaksi));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    //INITATING DATA FOR USER ON LAYOUT
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

    //INITATING DATA FOR PENUMPANG ON LAYOUT
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

    //INITIATING RECYCLER FOR PENUMPANG
    private void initRecycler(List<PenumpangModel> response) {
        PenumpangAdapter penumpangAdapter = new PenumpangAdapter(response, this::onPenumpangClick);
        recyclerView.setAdapter(penumpangAdapter);
    }


    //INITIATING DATA TO LAYOUT
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
        if (transaksiModel.getBukti() != null) {
            upload.setText(getString(R.string.upload_ulang));
        } else {
            upload.setText(getString(R.string.upload_transaksi));
        }
        initUser();
        initPenumpang();
        chckButton(transaksiModel);
    }

    private void chckButton(TransaksiModel transaksiModel){
        if (transaksiModel.getStatus().equals("menunggu pembayaran") || transaksiModel.getStatus().equals("menunggu konfirmasi")) {
            btnTicket.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
            upload.setVisibility(View.VISIBLE);
            batal.setVisibility(View.VISIBLE);
            status_transaksi.setTextColor(getResources().getColor(R.color.light_blue, null));
            status_transaksi.setText(transaksiModel.getStatus().toUpperCase());
        } else if (transaksiModel.getStatus().equals("terkonfirmasi") || transaksiModel.getStatus().equals("digunakan")) {
            btnTicket.setVisibility(View.VISIBLE);
            review.setVisibility(View.VISIBLE);
            upload.setVisibility(View.GONE);
            batal.setVisibility(View.GONE);
            status_transaksi.setTextColor(getResources().getColor(R.color.green, null));
            status_transaksi.setText(transaksiModel.getStatus().toUpperCase());
        } else if (transaksiModel.getStatus().equals("expired") || transaksiModel.getStatus().equals("dibatalkan")) {
            btnTicket.setVisibility(View.GONE);
            upload.setVisibility(View.GONE);
            batal.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
            status_transaksi.setTextColor(getResources().getColor(R.color.red, null));
            status_transaksi.setText(transaksiModel.getStatus().toUpperCase());
        }
    }


    //INTERFACE PENUMPANG CLICK RECYCLER VIEW
    @Override
    public void onPenumpangClick(int position, String name, String ktp_number) {

    }

    //BATALKAN TRANSAKSI FUNCTION
    private void batalkanTransaksi() {
        BatalkanTransaksiFragment batalkanTransaksiFragment = new BatalkanTransaksiFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id_detail", transaksiModel.getId_detail());
        batalkanTransaksiFragment.setArguments(bundle);
        batalkanTransaksiFragment.show(getSupportFragmentManager(), "BATALKAN_TRANSAKSI_FRAGMENT");
    }

    //PEMBATALAN INTERFACE FROM PEMBATALAN FRAGMENT
    @Override
    public void checkStatus(String status) {
        Intent intent = new Intent(DetailTransaksiActivity.this, TransaksiTerkiniActivity.class);
        startActivity(intent);
    }


    //GETTING REVIEW DATA FOR INITIATING DATA IN REVIEW DIALOG FRAGMENT
    //FUNCTION CALLED BY REVIEW BUTTON ON CLICK
    private void getReviewData() {
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<ResponseBody> call = transactionService.getReview(token, "application/json", "XMLHttpRequest", transaksiModel.getId_detail());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONObject dataReview = jsonObject.getJSONObject("review");
                            ReviewModel reviewModel = new ReviewModel(Integer.parseInt(dataReview.getString("id_pembelian")), dataReview.getString("review"), Integer.parseInt(dataReview.getString("score")));
                            openReview(reviewModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        Log.d("REVIEWAPI", "onResponse: REVIEW API IS NOT 200");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                    Log.d("REVIEWAPI", "onResponse: REVIEW API UNSUCCESFUL");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                Log.d("REVIEWAPI", "onResponse: REVIEW API ON FAILURE");
            }
        });
    }

    //REVIEW FUNCTION
    private void openReview(ReviewModel reviewModel) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id_detail", reviewModel.getId_pembelian());
        bundle.putString("review", reviewModel.getReview());
        bundle.putInt("score", reviewModel.getScore());
        reviewFragment.setArguments(bundle);
        reviewFragment.show(getSupportFragmentManager(), "REVIEW_LAYANAN");
    }

    @Override
    public void sendReview(int id_detail, String review, int score) {
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<ResponseBody> call = transactionService.sendReview(token, "application/json", "XMLHttpRequest", id_detail, review, score);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("message").equals("success")) {
                                Toast.makeText(getApplicationContext(), "Review berhasil dikirim!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                        Log.d("SEND REVIEW API", "onResponse: REVIEW API NOT 200");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                    Log.d("SEND REVIEW API", "onResponse: REVIEW API NOT SUCCESSFUL");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                Log.d("SEND REVIEW API", "onResponse: REVIEW API ON FAILURE");
            }
        });
    }

    @Override
    public void deleteData(int id_detail) {
        TransactionService transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        Call<ResponseBody> call = transactionService.deleteReview(token, "application/json", "XMLHttpRequest", id_detail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("message").equals("success")) {
                                Toast.makeText(getApplicationContext(), "Review berhasil dihapus!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Review tidak berhasil dihapus!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                        Log.d("DELETE REVIEW API", "onResponse: REVIEW API NOT 200");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                    Log.d("DELETE REVIEW API", "onResponse: REVIEW API NOT SUCCESSFUL");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Sepertinya terjadi kesalahan, harap ulangi kembali", Toast.LENGTH_SHORT).show();
                Log.d("DELETE REVIEW API", "onResponse: REVIEW API ON FAILURE");
            }
        });
    }
}