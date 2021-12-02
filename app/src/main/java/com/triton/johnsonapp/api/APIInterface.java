package com.triton.johnsonapp.api;

import com.triton.johnsonapp.requestpojo.LoginRequest;
import com.triton.johnsonapp.responsepojo.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface APIInterface {

    /*login*/
    @POST("user_management/mobile/login_page")
    Call<LoginResponse> LoginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);


}
