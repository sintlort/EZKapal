package com.ezcats.ezkapal.APIClient.Service;

import com.ezcats.ezkapal.Model.PemegangTicketModel;
import com.ezcats.ezkapal.Model.PenumpangModel;
import com.ezcats.ezkapal.Model.TicketModel;
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
                                   @Field("ticket_number") String ticket_number,
                                   @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("check/ticket/data")
    Call<ResponseBody> getTicketData(@Header("Authorization") String authHeader,
                                     @Header("Accept") String type,
                                     @Header("X-Requested-With") String request,
                                     @Field("ticket_number") String ticket_number);

    @FormUrlEncoded
    @POST("review/post")
    Call<ResponseBody> sendReview(@Header("Authorization") String authHeader,
                                  @Header("Accept") String type,
                                  @Header("X-Requested-With") String request,
                                  @Field("id_pembelian") int id_pembelian,
                                  @Field("isi_review") String isi_review,
                                  @Field("score") int score);

    @FormUrlEncoded
    @POST("review/get")
    Call<ResponseBody> getReview(@Header("Authorization") String authHeader,
                                 @Header("Accept") String type,
                                 @Header("X-Requested-With") String request,
                                 @Field("id_pembelian") int id_pembelian);

    @FormUrlEncoded
    @POST("review/delete")
    Call<ResponseBody> deleteReview(@Header("Authorization") String authHeader,
                                    @Header("Accept") String type,
                                    @Header("X-Requested-With") String request,
                                    @Field("id_pembelian") int id_pembelian);

    @FormUrlEncoded
    @POST("refresh/transaction")
    Call<TransaksiModel> refreshTransaction(@Header("Authorization") String authHeader,
                                         @Header("Accept") String type,
                                         @Header("X-Requested-With") String request,
                                         @Field("id_detail") int id_detail);
}
