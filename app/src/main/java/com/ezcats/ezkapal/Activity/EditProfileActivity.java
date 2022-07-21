package com.ezcats.ezkapal.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.AccountService;
import com.ezcats.ezkapal.APIClient.Service.UploadImageService;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;
import com.ezcats.ezkapal.Fragment.UploadProfileImage;
import com.ezcats.ezkapal.Model.JSONModel.UserJSONModel;
import com.ezcats.ezkapal.Model.UserModel;
import com.ezcats.ezkapal.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements UploadProfileImage.UploadImage {

    CircleImageView circleImageView;

    EditText name, address, phone_address;
    Button btn_edit_password, btn_update;
    TextView profile_names;

    String s_name, s_address, s_phone_address;
    SharedPreferences sharedPreferences;
    String token;

    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initialization();
    }

    private void initialization() {
        this.sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString(getString(R.string.token), "");
        this.circleImageView = findViewById(R.id.profile_image);
        this.name = findViewById(R.id.nama_profile);
        this.address = findViewById(R.id.alamat_profile);
        this.phone_address = findViewById(R.id.nohp_profile);
        this.profile_names = findViewById(R.id.profile_names);
        this.btn_edit_password = findViewById(R.id.btn_edit_password);
        this.btn_update = findViewById(R.id.btn_update);
        initData();
        initOnClick();
    }

    private void initData() {
        AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
        Call<UserModel> call = accountService.getUser("application/json", "XMLHttpRequest", this.token);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.code() == 200){
                    setProfile(response.body());
                } else {
                    showMessage("Mohon periksa koneksi anda !");
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                showMessage("Mohon periksa koneksi anda !");
                onBackPressed();
            }
        });
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void setProfile(UserModel body) {
        String url = "https://android.e-tiket.ngaeapp.com/storage/images/profile/"+body.getFoto();
        Picasso.get().load(url).placeholder(R.drawable.home_fragment_profile).into(circleImageView);
        profile_names.setText(body.getNama_user());
        name.setText(body.getNama_user());
        address.setText(body.getAlamat());
        phone_address.setText(body.getNohp());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.name_shared_preference), body.getNama_user());
        editor.putString(getString(R.string.email_shared_preference), body.getEmail());
        editor.putString(getString(R.string.number_shared_preference), body.getNohp());
        editor.putString(getString(R.string.address_shared_preference), body.getAlamat());
        editor.apply();
    }

    private void initOnClick() {
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageUpload();
            }
        });

        btn_edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditPasswordActivity.class);
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        getEditTextValue();
        AccountService accountService = RetrofitClient.getRetrofitInstance().create(AccountService.class);
        Call<UserJSONModel> call = accountService.updateProfile("application/json", "XMLHttpRequest", this.token, this.s_name, this.s_address, this.s_phone_address);
        call.enqueue(new Callback<UserJSONModel>() {
            @Override
            public void onResponse(Call<UserJSONModel> call, Response<UserJSONModel> response) {
                if (response.code()==200){
                    setProfile(response.body().getData());
                    showMessage("Edit profile berhasil !");
                    onBackPressed();
                } else {
                    showMessage("Mohon periksa koneksi anda !");
                }
            }

            @Override
            public void onFailure(Call<UserJSONModel> call, Throwable t) {
                showMessage("Mohon periksa koneksi anda !");
            }
        });
    }

    private void getEditTextValue() {
        this.s_name = name.getText().toString();
        this.s_address = address.getText().toString();
        this.s_phone_address = phone_address.getText().toString();
    }

    private void openImageUpload() {
        pickImage();
    }

    public void pickImage(){
        ImagePicker.Companion.with(this)
                .galleryOnly()	//User can only select image from Gallery
                .start(10);	//Default Request Code is ImagePicker.REQUEST_CODE
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.file = data.getData();
        uploadImage(file);
    }

    @Override
    public void UploadStatus(boolean status, String foto) {

    }

    private void uploadImage(Uri uris) {
        if (uris!= null){
            File file = new File(uris.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
            MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            UploadImageService uploadImageService = RetrofitClient.getRetrofitInstance().create(UploadImageService.class);
            Call<UserJSONModel> call = uploadImageService.uploadProfileImage("application/json", "XMLHttpRequest", this.token,parts);
            call.enqueue(new Callback<UserJSONModel>() {
                @Override
                public void onResponse(Call<UserJSONModel> call, Response<UserJSONModel> response) {
                    if(response.code() == 200){
                        takeData(response.body().getData());
                    } else {
                        showMessage("Mohon periksa internet anda !");
                    }
                }

                @Override
                public void onFailure(Call<UserJSONModel> call, Throwable t) {
                    showMessage("Mohon periksa internet anda !");
                }
            });
        } else {
            showMessage("Pilih gambar terlebih dahulu !");
        }
    }

    private void takeData(UserModel data) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.picture_shared_preference), data.getFoto());
        editor.apply();
        String url = "http://10.0.2.2:8000/storage/images/profile/"+data.getFoto();
        Picasso.get().load(url).placeholder(R.drawable.home_fragment_profile).into(circleImageView);
    }
}