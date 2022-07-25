package com.ezcats.ezkapal.APIClient.Service;

import com.ezcats.ezkapal.Model.GolonganModel;
import com.ezcats.ezkapal.Model.JSONModel.JadwalKapalJSONModel;
import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.Model.TicketModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TicketService {

    @FormUrlEncoded
    @POST("schedule/search")
    Call<List<TicketModel>> getTicketData(@Header("Authorization") String authHeader,
                                          @Header("Accept") String type,
                                          @Header("X-Requested-With") String request,
                                          @Field("id_golongan") int id_golongan,
                                          @Field("asal_pelabuhan") int asal_pelabuhan,
                                          @Field("tujuan_pelabuhan") int tujuan_pelabuhan,
                                          @Field("date") String date,
                                          @Field("tipe_kapal") String tipe_kapal);


    @GET("golongan/all")
    Call<List<GolonganModel>> getGolongan(@Header("Authorization") String authHeader,
                                          @Header("Accept") String type,
                                          @Header("X-Requested-With") String request);

    @GET("golongan/speedboat")
    Call<List<GolonganModel>> getGolonganSpeedboat(@Header("Authorization") String authHeader,
                                                   @Header("Accept") String type,
                                                   @Header("X-Requested-With") String request);


    @POST("get/kapal/jadwal")
    Call<JadwalKapalJSONModel> getJadwalKapal(@Header("Authorization") String authHeader,
                                              @Header("Accept") String type,
                                              @Header("X-Requested-With") String request);
}
