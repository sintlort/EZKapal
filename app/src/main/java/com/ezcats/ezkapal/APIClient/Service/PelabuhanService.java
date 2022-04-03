package com.ezcats.ezkapal.APIClient.Service;

import com.ezcats.ezkapal.Model.PelabuhanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PelabuhanService {
    @GET("pelabuhan/all")
    Call<List<PelabuhanModel>> getListPelabuhan(@Header("Accept") String type,
                                                @Header("X-Requested-With") String request,
                                                @Header("Content-type") String content_type,
                                                @Header("Authorization") String authHeader);
}
