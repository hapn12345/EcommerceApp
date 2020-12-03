package com.example.EcommerceApp.Service;

import com.example.EcommerceApp.Model.LoginResponse;
import com.example.EcommerceApp.Model.RegisterResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestMethods {
    @Headers("Content-Type: application/json")
    @POST("customer/auth/Login")
    //LoginResponse thay = Responsebody
    Call<LoginResponse> login(@Body LoginResponse loginResponse);

    @Headers("Content-Type: application/json")
    @POST("customer/auth/register")
    @FormUrlEncoded
    Call<RegisterResponse> register(@Body RegisterResponse registerResponse);


}
