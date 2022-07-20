package com.ezcats.ezkapal.APIClient.Service;

import com.ezcats.ezkapal.Model.JSONModel.NotificationJSONModel;
import com.ezcats.ezkapal.Model.JSONModel.NotificationUpdateJSONModel;
import com.ezcats.ezkapal.Model.JSONModel.UserJSONModel;
import com.ezcats.ezkapal.Model.LoginModelPage;
import com.ezcats.ezkapal.Model.RegisterModel;
import com.ezcats.ezkapal.Model.UserModel;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AccountService {

    @POST("register")
    Call<ResponseBody> registerAccount(@Header("Accept") String type,
                                       @Header("X-Requested-With") String request,
                                       @Header("Content-type") String content_type,
                                       @Body RegisterModel registerModel);

    @POST("login")
    Call<ResponseBody> loginAccount(@Header("Accept") String type,
                                    @Header("X-Requested-With") String request,
                                    @Body LoginModelPage loginModelPage);

    @GET("user")
    Call<UserModel> getUser(@Header("Accept") String type,
                            @Header("X-Requested-With") String request,
                            @Header("Authorization") String authHeader);

    @POST("logout")
    Call<ResponseBody> logoutUser(@Header("Accept") String type,
                                  @Header("X-Requested-With") String request,
                                  @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("edit")
    Call<UserJSONModel> updateProfile(@Header("Accept") String type,
                                      @Header("X-Requested-With") String request,
                                      @Header("Authorization") String authHeader,
                                      @Field("nama") String name,
                                      @Field("alamat") String alamat,
                                      @Field("nohp") String nohp);

    @FormUrlEncoded
    @POST("edit/password")
    Call<UserJSONModel> updatePassword(@Header("Accept") String type,
                                       @Header("X-Requested-With") String request,
                                       @Header("Authorization") String authHeader,
                                       @Field("old_password") String old_password,
                                       @Field("password") String password);

    @GET("notification/current")
    Call<NotificationJSONModel> getCurrentNotification(@Header("Accept") String type,
                                                       @Header("X-Requested-With") String request,
                                                       @Header("Authorization") String authHeader);

    @GET("notification/archived")
    Call<NotificationJSONModel> getArchivedNotification(@Header("Accept") String type,
                                                        @Header("X-Requested-With") String request,
                                                        @Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("notification/update")
    Call<NotificationUpdateJSONModel> updateNotification(@Header("Accept") String type,
                                                         @Header("X-Requested-With") String request,
                                                         @Header("Authorization") String authHeader,
                                                         @Field("id") int id,
                                                         @Field("action") boolean action);

    @FormUrlEncoded
    @POST("receive/fcm")
    Call<UserJSONModel> receiveFCM(@Header("Accept") String type,
                                   @Header("X-Requested-With") String request,
                                   @Header("Authorization") String authHeader,
                                   @Field("fcm_token") String fcm_token);

}
