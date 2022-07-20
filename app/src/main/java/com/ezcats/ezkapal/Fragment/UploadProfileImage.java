package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.UploadImageService;
import com.ezcats.ezkapal.Model.JSONModel.UserJSONModel;
import com.ezcats.ezkapal.Model.UserModel;
import com.ezcats.ezkapal.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadProfileImage extends DialogFragment {

    private static final String TAG = "UPLOAD_IMAGE_FRAGMENT";

    Button pick_image, upload_image;
    Uri file;
    String token;
    SharedPreferences sharedPreferences;

    public interface UploadImage{
        void UploadStatus(boolean status, String foto);
    }

    public UploadImage sendStatus;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            sendStatus = (UploadImage) getActivity();
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
            float width = getResources().getDimension(R.dimen.upload_transaksi_width);
            float height = getResources().getDimension(R.dimen.upload_transaksi_height);
            dialog.getWindow().setLayout(Math.round(width), Math.round(height));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_profile_image, container, false);
        initialization(v);
        return v;
    }

    private void initialization(View v) {
        this.sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString(getString(R.string.token), "");
        pick_image = v.findViewById(R.id.pick_image_profile);
        upload_image = v.findViewById(R.id.upload_image_profile);
        initOnClick();
    }

    private void initOnClick() {
        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(file);
            }
        });
    }

    public void pickImage(){
        ImagePicker.Companion.with(getActivity())
                .galleryOnly()	//User can only select image from Gallery
                .start(10);	//Default Request Code is ImagePicker.REQUEST_CODE
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.file = data.getData();
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
                        getDialog().dismiss();
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
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.picture_shared_preference), data.getFoto());
        editor.apply();
        updateImage(data.getFoto());
    }

    private void updateImage(String foto) {
        sendStatus.UploadStatus(true, foto);
    }

    private void showMessage(String ss) {
        Toast.makeText(getContext(),ss, Toast.LENGTH_LONG).show();
    }

}