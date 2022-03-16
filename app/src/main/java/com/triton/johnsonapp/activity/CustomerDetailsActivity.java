package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.CheckLocationRequest;
import com.triton.johnsonapp.requestpojo.JobFetchAddressRequest;
import com.triton.johnsonapp.responsepojo.JobFetchAddressResponse;
import com.triton.johnsonapp.responsepojo.SuccessResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {

    private String TAG ="CustomerDetailsActivity";

    String userid,username;

    String networkStatus = "",message,activity_id,job_id;
    private String status;
    private String fromactivity;
    private String job_detail_no;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_customer_name)
    TextView txt_customer_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_customer_id)
    TextView txt_customer_id;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_customer_emailid)
    TextView txt_customer_emailid;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_customer_address)
    TextView txt_customer_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_continue)
    Button btn_continue;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        String useremailid = user.get(SessionManager.KEY_EMAILID);

        username = user.get(SessionManager.KEY_USERNAME);
        txt_customer_name.setText(username);
        txt_customer_id.setText(userid);
        if(useremailid != null) {
            txt_customer_emailid.setText(useremailid);
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity_id = extras.getString("activity_id");
            job_id = extras.getString("job_id");
            status = extras.getString("status");
            fromactivity = extras.getString("fromactivity");
            job_detail_no = extras.getString("job_detail_no");
            Log.w(TAG,"activity_id -->"+activity_id+"job_id : "+job_id+" status : "+status+" fromactivity : "+fromactivity+" job_detail_no : "+job_detail_no);

        }

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {
            JobFetchAddressRequestCall();




        }

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupListActivity.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("job_id",job_id);
                intent.putExtra("status",status);
                intent.putExtra("fromactivity",TAG);
                intent.putExtra("job_detail_no",job_detail_no);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("AllJobListActivity")){
            Intent intent = new Intent(CustomerDetailsActivity.this, AllJobListActivity.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("status",status);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);

        }else{
            Intent intent = new Intent(CustomerDetailsActivity.this, AllJobListActivity.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("status",status);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);
        }
    }

    @SuppressLint("LogNotTimber")
    private void JobFetchAddressRequestCall() {
        dialog = new Dialog(CustomerDetailsActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobFetchAddressResponse> call = apiInterface.JobFetchAddressRequestCall(RestUtils.getContentType(), jobFetchAddressRequest());
        Log.w(TAG,"JobFetchAddressRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<JobFetchAddressResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobFetchAddressResponse> call, @NonNull Response<JobFetchAddressResponse> response) {

                Log.w(TAG,"startWorkRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        dialog.dismiss();
                        if(response.body().getData().size()>0){
                            int arraySize = response.body().getData().size();
                            for(int i = 0; i < arraySize; i++) {
                                txt_customer_address.append(response.body().getData().get(i));
                                txt_customer_address.append(","+"\n");
                            }
                        }



                    } else {
                        dialog.dismiss();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobFetchAddressResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private JobFetchAddressRequest jobFetchAddressRequest() {

        /*
         * job_no : 61f222396667ac391fc85c55

         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        JobFetchAddressRequest jobFetchAddressRequest = new JobFetchAddressRequest();
        jobFetchAddressRequest.setJob_no(job_id);



        Log.w(TAG,"checkLocationRequest "+ new Gson().toJson(jobFetchAddressRequest));
        return jobFetchAddressRequest;
    }
}