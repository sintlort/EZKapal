package com.ezcats.ezkapal.APIClient.Service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadImageService {

    @Multipart
    @POST("image/upload")
    Call<ResponseBody> uploadImage(@Header("Accept") String type,
                                   @Header("X-Requested-With") String request,
                                   @Header("Authorization") String authHeader,
                                   @Part MultipartBody.Part part,
                                   @Part("id_detail") RequestBody requestBody);
}
