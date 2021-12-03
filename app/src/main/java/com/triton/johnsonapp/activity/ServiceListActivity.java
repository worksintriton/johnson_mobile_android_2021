package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.ServiceListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceListActivity extends AppCompatActivity {



    private String TAG ="ServiceListActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_servicelist)
    RecyclerView rv_servicelist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    Dialog  dialog;

    String networkStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);
/*
        if(username!=null){

            txt_logout.setText(username+" Logout");
        }

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.logoutUser();
                startActivity(new Intent(ServiceListActivity.this,LoginActivity.class));
            }
        });*/

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {

            getServiceListResponseCall();
        }



        }


    // default back button action
    public void onBackPressed() {

      /*  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);*/

        //super.onBackPressed();
    }


    @SuppressLint("LogNotTimber")
    public void getServiceListResponseCall(){
        dialog = new Dialog(ServiceListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        //Creating an object of our api interface
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetServiceListResponse> call = apiInterface.getServiceListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<GetServiceListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<GetServiceListResponse> call, @NonNull Response<GetServiceListResponse> response) {
                dialog.dismiss();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"GetServiceListResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData()!= null){

                            List<GetServiceListResponse.DataBean> dataBeanList = response.body().getData();


                            if(dataBeanList != null && dataBeanList.size()>0){
                                setView(dataBeanList);
                            }
                        }


                    }



                }








            }


            @Override
            public void onFailure(@NonNull Call<GetServiceListResponse> call, @NonNull  Throwable t) {
                dialog.dismiss();
                Log.w(TAG,"GetServiceListResponse flr"+t.getMessage());
            }
        });

    }

    @SuppressLint("LogNotTimber")
    private void setView(List<GetServiceListResponse.DataBean> dataBeanList) {


        rv_servicelist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_servicelist.setItemAnimator(new DefaultItemAnimator());
        ServiceListAdapter serviceListAdapter = new ServiceListAdapter(this, dataBeanList);
        rv_servicelist.setAdapter(serviceListAdapter);
    }

}