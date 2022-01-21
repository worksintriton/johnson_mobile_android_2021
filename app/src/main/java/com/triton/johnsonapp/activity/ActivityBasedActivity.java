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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.ActivityBasedListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.ActivityListManagementRequest;
import com.triton.johnsonapp.responsepojo.ActivityListManagementResponse;
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

public class ActivityBasedActivity extends AppCompatActivity {



    private String TAG ="ActivityBasedActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_activitybasedlist)
    RecyclerView rv_activitybasedlist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    Dialog dialog;

    String networkStatus = "",message;

    int number=0;
    SessionManager session;

    private String search_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_based);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);


        username = user.get(SessionManager.KEY_USERNAME);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {
            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {
            activityListResponseCall();


        }


        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   search_string = textView.getText().toString();
                    activityListResponseCall();
                    return true;
                }
                return false;
            }
        });





    }


    // default back button action
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityBasedActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);


    }



    @SuppressLint("LogNotTimber")
    private void activityListResponseCall() {
        dialog = new Dialog(ActivityBasedActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<ActivityListManagementResponse> call = apiInterface.activedetailmanagementResponseCall(RestUtils.getContentType(), ActivityListManagementRequest());
        Log.w(TAG,"ActivityListManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ActivityListManagementResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<ActivityListManagementResponse> call, @NonNull Response<ActivityListManagementResponse> response) {

                Log.w(TAG,"ActivityListManagementResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            dialog.dismiss();

                            List<ActivityListManagementResponse.DataBean> dataBeanList = response.body().getData();

                            if(dataBeanList != null && dataBeanList.size()>0){
                                rv_activitybasedlist.setVisibility(View.VISIBLE);
                                txt_no_records.setVisibility(View.GONE);
                                setView(dataBeanList);
                            }

                            else {
                                rv_activitybasedlist.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);
                                txt_no_records.setText("No Activity Available");
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
            public void onFailure(@NonNull Call<ActivityListManagementResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("ActivityListManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ActivityListManagementRequest ActivityListManagementRequest() {
        /*
         * User_id : 1234456789
         * search_string:""
         */


        ActivityListManagementRequest ActivityListManagementRequest = new ActivityListManagementRequest();
        ActivityListManagementRequest.setUser_id(userid);
        ActivityListManagementRequest.setSearch_string(search_string);

        Log.w(TAG,"ActivityListManagementRequest "+ new Gson().toJson(ActivityListManagementRequest));
        return ActivityListManagementRequest;
    }
    @SuppressLint("LogNotTimber")
    private void setView(List<ActivityListManagementResponse.DataBean> dataBeanList) {


        rv_activitybasedlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_activitybasedlist.setItemAnimator(new DefaultItemAnimator());
        ActivityBasedListAdapter activityBasedListAdapter = new ActivityBasedListAdapter(this, dataBeanList);
        rv_activitybasedlist.setAdapter(activityBasedListAdapter);
    }

    public void logOutUser(View view) {
        session.logoutUser();
    }
}