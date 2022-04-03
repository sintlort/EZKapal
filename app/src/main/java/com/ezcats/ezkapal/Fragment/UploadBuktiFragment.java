package com.ezcats.ezkapal.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ezcats.ezkapal.APIClient.RetrofitClient;
import com.ezcats.ezkapal.APIClient.Service.UploadImageService;
import com.ezcats.ezkapal.Activity.DetailTransaksiActivity;
import com.ezcats.ezkapal.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadBuktiFragment extends DialogFragment {

    private static final String TAG = "UPLOAD_BUKTI_FRAGMENT";

    public interface UploadBukti{
        void sendImage(String status);
    }

    int id_detail;
    public UploadBukti uploadBukti;
    Button btn_up, btn_select;
    Uri file;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            uploadBukti = (UploadBukti) getActivity();
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
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_bukti, container, false);
        btn_up = v.findViewById(R.id.upload_image);
        btn_select = v.findViewById(R.id.pick_image);
        Bundle bundle = getArguments();
        this.id_detail = bundle.getInt("id_detail",0);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                uploadImage(file, id_detail);
            }
        });
        return v;
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

    private void uploadImage(Uri uris, int id_details) {
        if (uris!= null){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
            String token = sharedPreferences.getString(getString(R.string.token), "");
            File file = new File(uris.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
            MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

            RequestBody id_detail = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_details));
            UploadImageService uploadImageService = RetrofitClient.getRetrofitInstance().create(UploadImageService.class);
            Call<ResponseBody> call = uploadImageService.uploadImage("application/json", "XMLHttpRequest", token,parts, id_detail);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("UPLOADAPI", "onResponse: success");
                    getDialog().dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        Toast.makeText(getContext(),"URIKOSONG", Toast.LENGTH_LONG).show();
    }
}