package com.triton.johnsonapp.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Iddinesh.
 */
public class RetrofitClient {


    private static Retrofit retrofit = null;
    private static OkHttpClient client;


    /*live*/
  /*public static String BASE_URL = "http://52.25.163.13:3000/api/";
  public static String IMAGE_BASE_URL = "http://52.25.163.13:3000/";*/

    /*dev*/
    public static String BASE_URL = "http://35.86.174.72:3000/api/";
    public static String IMAGE_BASE_URL = "http://35.86.174.72:3000/api/";

    /*Banner Image*/
    public static String BANNER_IMAGE_URL = BASE_URL+"uploads/bannerempty.jpg";

    /* Profile or other Image*/
    public static String PROFILE_IMAGE_URL = BASE_URL+"uploads/picempty.jpg";




    public static Retrofit getClient() {
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES )
                .cache(null)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
    public static Retrofit getImageClient() {
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES )
                .cache(null)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(IMAGE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}



