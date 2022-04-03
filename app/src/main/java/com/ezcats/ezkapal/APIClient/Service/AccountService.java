package com.ezcats.ezkapal.APIClient.Service;

import com.ezcats.ezkapal.Model.LoginModelPage;
import com.ezcats.ezkapal.Model.RegisterModel;
import com.ezcats.ezkapal.Model.UserModel;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
}
