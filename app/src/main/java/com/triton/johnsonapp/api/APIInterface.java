package com.triton.johnsonapp.api;

import com.google.android.gms.common.internal.GetServiceRequest;
import com.triton.johnsonapp.requestpojo.LoginRequest;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;
import com.triton.johnsonapp.responsepojo.LoginResponse;

import java.io.ObjectInputStream;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface APIInterface {

    /*login*/
    @POST("user_management/mobile/login_page")
    Call<LoginResponse> LoginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);


    /*service screen list*/
    @GET("service_management/getlist")
    Call<GetServiceListResponse> getServiceListResponseCall(@Header("Content-Type") String type);

    /*field screen list*/
    @GET("field_management/getlist")
    Call<GetFieldListResponse> getfieldListResponseCall(@Header("Content-Type") String type);

}
