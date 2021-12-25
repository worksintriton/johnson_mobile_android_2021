package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.ActivityBasedListAdapter;
import com.triton.johnsonapp.adapter.JobDetailListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.JobNoManagementRequest;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailActivity extends AppCompatActivity {

    private String TAG ="JobDetailActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_jobdetaillist)
    RecyclerView rv_jobdetaillist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    Dialog dialog;

    String networkStatus = "",message,activity_id;

    int number=0;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity_id = extras.getString("activity_id");
            Log.w(TAG,"activity_id -->"+activity_id);

        }
/*
        if(username!=null){

            txt_logout.setText(username+" Logout");
        }

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.logoutUser();
                startActivity(new Intent(SubGroupListActivity.this,LoginActivity.class));
            }
        });*/

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {

                jobnomanagmentResponseCall();

           /* List<GetServiceListResponse.DataBean> dataBeanList = new ArrayList<>();


            for(int i=0;i<=3;i++){

                number++;
                GetServiceListResponse.DataBean dataBean = new  GetServiceListResponse.DataBean();

                Log.w(TAG,"number "+ number);

                dataBean.setService_name("Job Detail "+number);

                dataBeanList.add(dataBean);
            }


            if(dataBeanList != null && dataBeanList.size()>0){
                setView(dataBeanList);
            }*/
        }



    }


    // default back button action
    public void onBackPressed() {


        super.onBackPressed();
        Intent intent = new Intent(JobDetailActivity.this, ActivityBasedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);


    }


    @SuppressLint("LogNotTimber")
    private void jobnomanagmentResponseCall() {
        dialog = new Dialog(JobDetailActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobNoManagementResponse> call = apiInterface.jobnomanagmentResponseCall(RestUtils.getContentType(), JobNoManagementRequest());
        Log.w(TAG,"JobNoManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<JobNoManagementResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobNoManagementResponse> call, @NonNull Response<JobNoManagementResponse> response) {

                Log.w(TAG,"JobNoManagementResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            dialog.dismiss();
                            List<JobNoManagementResponse.DataBean> dataBeanList = response.body().getData();


                            if(dataBeanList != null && dataBeanList.size()>0){
                                setView(dataBeanList);

                                txt_no_records.setVisibility(View.GONE);
                            }

                            else {
                                txt_no_records.setVisibility(View.VISIBLE);

                                txt_no_records.setText("No Job Detail Available");
                            }

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<JobNoManagementResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("JobNoManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private JobNoManagementRequest JobNoManagementRequest() {



        /**
         * activedetail__id : 61c1e43497057923644090bd
         */


        JobNoManagementRequest JobNoManagementRequest = new JobNoManagementRequest();
        JobNoManagementRequest.setActivedetail__id(activity_id);

        Log.w(TAG,"JobNoManagementRequest "+ new Gson().toJson(JobNoManagementRequest));
        return JobNoManagementRequest;
    }

    @SuppressLint("LogNotTimber")
    private void setView(List<JobNoManagementResponse.DataBean> dataBeanList) {


        rv_jobdetaillist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_jobdetaillist.setItemAnimator(new DefaultItemAnimator());
        JobDetailListAdapter jobDetailListAdapter = new JobDetailListAdapter(this, dataBeanList,activity_id);
        rv_jobdetaillist.setAdapter(jobDetailListAdapter);
    }

}