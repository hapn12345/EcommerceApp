package com.example.EcommerceApp.Service;

import com.example.EcommerceApp.Model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestMethods {
    @Headers("Content-Type: application/json")
    @POST("customer/auth/Login")
    Call<ResponseBody> login(@Body LoginResponse loginResponse);

}
