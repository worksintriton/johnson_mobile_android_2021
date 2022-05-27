package com.triton.johnsonapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activitybased.ActivityBasedActivity;
import com.triton.johnsonapp.activitybased.ActivityJobListActivity;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.FetchRecordByUserIDRequest;
import com.triton.johnsonapp.responsepojo.FetchRecordByUserIDResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityStatusActivity extends AppCompatActivity {
    private String TAG ="ActivityStatusActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_new)
    Button btn_new;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_pause)
    Button btn_pause;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;

    private Dialog dialog;
    private String userid;
    private String networkStatus = "";
    private String activity_id;
    private String status;
    private int  new_count;
    private int  pause_count;
    private String UKEY;
    private String UKEY_DESC;
    private int form_type;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        SessionManager  session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UKEY = extras.getString("UKEY");
            UKEY_DESC = extras.getString("UKEY_DESC");
            form_type = extras.getInt("form_type");
            activity_id = extras.getString("activity_id");
            new_count = extras.getInt("new_count");
            pause_count = extras.getInt("pause_count");
            Log.w(TAG,"activity_id -->"+activity_id+" status : "+status);


        }
        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
        if(new_count != 0){
            btn_new.setText("New  ( "+new_count+" ) ");
        }
        if(pause_count != 0){
            btn_pause.setText("Pause  ( "+pause_count+" ) ");
        }

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityStatusActivity.this, ActivityJobListActivity.class);
                intent.putExtra("status","New");
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("form_type",form_type);
                intent.putExtra("UKEY",UKEY);
                intent.putExtra("UKEY_DESC",UKEY_DESC);
                intent.putExtra("new_count",new_count);
                intent.putExtra("pause_count",pause_count);
                startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);

            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityStatusActivity.this, ActivityJobListActivity.class);
                intent.putExtra("status","Pause");
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("form_type",form_type);
                intent.putExtra("UKEY",UKEY);
                intent.putExtra("UKEY_DESC",UKEY_DESC);
                intent.putExtra("new_count",new_count);
                intent.putExtra("pause_count",pause_count);
                startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());

        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {
            //fetch_record_byuseridResponseCall();
        }
    }

    @SuppressLint("LogNotTimber")
    private void fetch_record_byuseridResponseCall() {
        dialog = new Dialog(ActivityStatusActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<FetchRecordByUserIDResponse> call = apiInterface.fetch_record_byuseridResponseCall(RestUtils.getContentType(), fetchRecordByUserIDRequest());
        Log.w(TAG,"fetch_record_byuseridResponseCall url  :%s"+" "+ call.request().url().toString());
        call.enqueue(new Callback<FetchRecordByUserIDResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<FetchRecordByUserIDResponse> call, @NonNull Response<FetchRecordByUserIDResponse> response) {

                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            dialog.dismiss();
                            if(response.body().getData().getCount() != 0){
                                btn_pause.setText("Paused  ( "+response.body().getData().getCount()+" ) ");
                            }
                        }


                    } else {
                        dialog.dismiss();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<FetchRecordByUserIDResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private FetchRecordByUserIDRequest fetchRecordByUserIDRequest() {
        /*
         * user_id : 12345
         * activity_id
         */

        FetchRecordByUserIDRequest fetchRecordByUserIDRequest = new FetchRecordByUserIDRequest();
        fetchRecordByUserIDRequest.setUser_id(userid);
        fetchRecordByUserIDRequest.setActivity_id(activity_id);
        Log.w(TAG,"loginRequest "+ new Gson().toJson(fetchRecordByUserIDRequest));
        return fetchRecordByUserIDRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ActivityBasedActivity.class));
        finish();
    }
}