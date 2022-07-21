package com.ezcats.ezkapal.APIClient.Service;

import com.ezcats.ezkapal.Model.JSONModel.TransaksiJSONModel;
import com.ezcats.ezkapal.Model.MetodePembayaranModel;
import com.ezcats.ezkapal.Model.PenumpangModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PemesananService {

    @GET("metode/all")
    Call<List<MetodePembayaranModel>> getMetodePembayaran(@Header("Authorization") String authHeader,
                                                          @Header("Accept") String type,
                                                          @Header("X-Requested-With") String request);

    @FormUrlEncoded
    @POST("transaction/commited")
    Call<TransaksiJSONModel> transactionCommited(@Header("Authorization") String authHeader,
                                                 @Header("Accept") String type,
                                                 @Header("X-Requested-With") String request,
                                                 @Field("id_detail") int id_detail,
                                                 @Field("jumlah_penumpang") int jumlah,
                                                 @Field("id_metode_pembayaran") int id_metode,
                                                 @Field("nomor_polisi") String nomor_polisi,
                                                 @Field("tanggal") String tanggal_input,
                                                 @Field("nama_penumpang") String nama_penumpang,
                                                 @Field("nomor_penumpang") String nomor_penumpang);

    @FormUrlEncoded
    @POST("transaction/commited/penumpang")
    Call<ResponseBody> transactionForPenumpang(@Header("Authorization") String authHeader,
                                               @Header("Accept") String type,
                                               @Header("X-Requested-With") String request,
                                               @Field("id_detail_pemesanan") int idDetailPemesanan,
                                               @Field("nama_pemegang_tiket") String namaPemegang,
                                               @Field("telepon") String telepon);

    @FormUrlEncoded
    @POST("transaction/failed/penumpang")
    Call<ResponseBody> transactionFailedForPenumpang(@Header("Authorization") String authHeader,
                                                     @Header("Accept") String type,
                                                     @Header("X-Requested-With") String request,
                                                     @Field("id_detail_pemesanan") int idDetailPemesanan);
}
