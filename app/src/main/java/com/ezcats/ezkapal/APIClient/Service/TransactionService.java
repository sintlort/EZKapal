package com.ezcats.ezkapal.APIClient.Service;

import com.ezcats.ezkapal.Model.PenumpangModel;
import com.ezcats.ezkapal.Model.TransaksiModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TransactionService {

    @GET("transaction/recently")
    Call<List<TransaksiModel>> getTransactionRecently(@Header("Authorization") String authHeader,
                                                      @Header("Accept") String type,
                                                      @Header("X-Requested-With") String request);

    @GET("transaction/history")
    Call<List<TransaksiModel>> getHistoryTransaction(@Header("Authorization") String authHeader,
                                                     @Header("Accept") String type,
                                                     @Header("X-Requested-With") String request);

    @FormUrlEncoded
    @POST("transaction/my/penumpang")
    Call<List<PenumpangModel>> getPenumpang(@Header("Authorization") String authHeader,
                                            @Header("Accept") String type,
                                            @Header("X-Requested-With") String request,
                                            @Field("id") int id_pembelian);

    @FormUrlEncoded
    @POST("transaction/cancel")
    Call<ResponseBody> cancelPemesanan(@Header("Authorization") String authHeader,
                                    @Header("Accept") String type,
                                    @Header("X-Requested-With") String request,
                                    @Field("id") int id_pembelian);

    @FormUrlEncoded
    @POST("check/ticket")
    Call<ResponseBody> checkTicket(@Header("Authorization") String authHeader,
                                       @Header("Accept") String type,
                                       @Header("X-Requested-With") String request,
                                       @Field("ticket_number") String ticket_number);
}
