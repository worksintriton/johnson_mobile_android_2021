package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.GroupListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.FormDataStoreRequest;
import com.triton.johnsonapp.requestpojo.GroupDetailManagementRequest;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.responsepojo.GroupDetailManagementResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListActivity extends AppCompatActivity {


    private String TAG ="GroupListActivity";

    String userid,username;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_grouplist)
    RecyclerView rv_grouplist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    Dialog dialog;
    Dialog alertdialog;

    String networkStatus = "",message,activity_id,job_id;

    int number=0;
    private String search_string = "";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;
    private String group_id = "";
    private String subgroup_id = "";
    private AlertDialog.Builder alertDialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity_id = extras.getString("activity_id");
            job_id = extras.getString("job_id");
            Log.w(TAG,"activity_id -->"+activity_id+"job_id : "+job_id);

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

                groupdetailmanagmentResponseCall();

       /*     List<GetServiceListResponse.DataBean> dataBeanList = new ArrayList<>();


            for(int i=0;i<=3;i++){

                number++;
                GetServiceListResponse.DataBean dataBean = new  GetServiceListResponse.DataBean();

                Log.w(TAG,"number "+ number);

                dataBean.setService_name("Group" +number);

                dataBeanList.add(dataBean);
            }


            if(dataBeanList != null && dataBeanList.size()>0){
                setView(dataBeanList);
            }*/
        }

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search_string = textView.getText().toString();
                    groupdetailmanagmentResponseCall();
                    return true;
                }
                return false;
            }
        });




    }


    // default back button action
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GroupListActivity.this, JobDetailActivity.class);
        intent.putExtra("activity_id",activity_id);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);


    }

    @SuppressLint("LogNotTimber")
    private void groupdetailmanagmentResponseCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GroupDetailManagementResponse> call = apiInterface.groupdetailmanagmentResponseCall(RestUtils.getContentType(), GroupDetailManagementRequest());
        Log.w(TAG,"GroupDetailManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<GroupDetailManagementResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<GroupDetailManagementResponse> call, @NonNull Response<GroupDetailManagementResponse> response) {

                Log.w(TAG,"GroupDetailManagementResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            dialog.dismiss();
                            List<GroupDetailManagementResponse.DataBean> dataBeanList = response.body().getData();


                            if(dataBeanList != null && dataBeanList.size()>0){
                               // showPopupStartJoB();
                                rv_grouplist.setVisibility(View.VISIBLE);
                                setView(dataBeanList);
                                txt_no_records.setVisibility(View.GONE);
                            }
                            else {
                                rv_grouplist.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);
                                txt_no_records.setText("No Groups Available");
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
            public void onFailure(@NonNull Call<GroupDetailManagementResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("GroupDetailManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private GroupDetailManagementRequest GroupDetailManagementRequest() {
        /*
         * user_id : 61c1e5e09934282617679543
         * search_string
         * group_id
         * sub_group_id
         */

        GroupDetailManagementRequest GroupDetailManagementRequest = new GroupDetailManagementRequest();
        GroupDetailManagementRequest.setUser_id(userid);
        GroupDetailManagementRequest.setSearch_string(search_string);
        GroupDetailManagementRequest.setJob_detail_id(job_id);

        Log.w(TAG,"GroupDetailManagementRequest "+ new Gson().toJson(GroupDetailManagementRequest));
        return GroupDetailManagementRequest;
    }

    @SuppressLint("LogNotTimber")
    private void setView(List<GroupDetailManagementResponse.DataBean> dataBeanList) {


        rv_grouplist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_grouplist.setItemAnimator(new DefaultItemAnimator());
        GroupListAdapter groupListAdapter = new GroupListAdapter(this, dataBeanList,activity_id,job_id);
        rv_grouplist.setAdapter(groupListAdapter);
    }

    private void showPopupStartJoB() {

        try {
            alertdialog = new Dialog(GroupListActivity.this);
            alertdialog.setContentView(R.layout.startjob_popup_layout);
            LinearLayout ll_start = alertdialog.findViewById(R.id.ll_start);
            LinearLayout ll_pause = alertdialog.findViewById(R.id.ll_pause);
            LinearLayout ll_stop = alertdialog.findViewById(R.id.ll_stop);
            ImageView img_close = alertdialog.findViewById(R.id.img_close);

            ll_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmStartJobDialog();
                }
            });
            img_close.setOnClickListener(v -> alertdialog.dismiss());
            Objects.requireNonNull(alertdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertdialog.show();


        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    private void confirmStartJobDialog(){
         alertDialogBuilder = new AlertDialog.Builder(GroupListActivity.this);
        alertDialogBuilder.setMessage("Would you like to start job tracking ?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        getformdataListResponseCall();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

       AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    @SuppressLint("LogNotTimber")
    private void getformdataListResponseCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<FormDataStoreResponse> call = apiInterface.getformdataListResponseCall(RestUtils.getContentType(), FormDataStoreRequest());
        Log.w(TAG,"getformdataListResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FormDataStoreResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FormDataStoreResponse> call, @NonNull Response<FormDataStoreResponse> response) {

                Log.w(TAG,"getformdataListResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            dialog.dismiss();
                            alertdialog.dismiss();
                            Toasty.success(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<FormDataStoreResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private FormDataStoreRequest FormDataStoreRequest() {

        /*
         * user_id : 123456
         * activity_id : 123456
         * job_id : 123456
         * group_id : 123456
         * sub_group_id : 123456
         * data_store : [{"__v":0,"_id":"61aa033385104f58378b3b1d","cat_id":"61a8b8752d9a15335c1e511f","created_by":"Admin","date_of_create":"12/3/2021, 5:14:51 PM","date_of_update":"12/3/2021, 5:14:51 PM","field_comments":"filed Length should be 10 digit","field_length":"10","field_name":"Field 1","field_type":"String","field_update_reason":"","field_value":"","updated_by":"Admin"}]
         * start_time : 23-10-2021 11:00 AM
         * pause_time : 23-10-2021 11:00 AM
         * stop_time : 23-10-2021 11:00 AM
         * storage_status : Temp
         * date_of_create : 23-10-2021 11:00 AM
         * date_of_update :
         * created_by : 123456
         * updated_by :
         * update_reason :
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        List<FormDataStoreRequest.DataStoreBean> dataStoreBeanList = new ArrayList<>();


        FormDataStoreRequest FormDataStoreRequest = new FormDataStoreRequest();
        FormDataStoreRequest.setUser_id(userid);
        FormDataStoreRequest.setActivity_id(activity_id);
        FormDataStoreRequest.setJob_id(job_id);
        FormDataStoreRequest.setGroup_id(group_id);
        FormDataStoreRequest.setSub_group_id(subgroup_id);
        FormDataStoreRequest.setData_store(dataStoreBeanList);
        FormDataStoreRequest.setStart_time(currentDateandTime);
        FormDataStoreRequest.setPause_time("");
        FormDataStoreRequest.setStop_time("");
        FormDataStoreRequest.setStorage_status("Pause");
        FormDataStoreRequest.setDate_of_create("");
        FormDataStoreRequest.setDate_of_update("");
        FormDataStoreRequest.setCreated_by("");
        FormDataStoreRequest.setUpdated_by("");
        FormDataStoreRequest.setUpdate_reason("");

        Log.w(TAG,"FormDataStoreRequest "+ new Gson().toJson(FormDataStoreRequest));
        return FormDataStoreRequest;
    }



}