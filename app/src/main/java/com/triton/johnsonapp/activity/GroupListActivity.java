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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.GroupListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.CheckDataStoreRequest;
import com.triton.johnsonapp.requestpojo.FormDataStoreRequest;
import com.triton.johnsonapp.requestpojo.GroupDetailManagementRequest;
import com.triton.johnsonapp.requestpojo.PauseJobRequest;
import com.triton.johnsonapp.requestpojo.ResumeJobRequest;
import com.triton.johnsonapp.requestpojo.StartWorkRequest;
import com.triton.johnsonapp.requestpojo.StopJobRequest;
import com.triton.johnsonapp.responsepojo.CheckDataStoreResponse;
import com.triton.johnsonapp.responsepojo.FormDataStoreResponse;
import com.triton.johnsonapp.responsepojo.GroupDetailManagementResponse;
import com.triton.johnsonapp.responsepojo.SuccessResponse;
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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_menu)
    RelativeLayout rl_menu;

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
    private boolean isStarted = false;
    private boolean isPaused = false;
    private boolean isResumed = false;
    private boolean isStopped = false;


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



        rl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataStoreResponseCall1();

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity_id = extras.getString("activity_id");
            job_id = extras.getString("job_id");
            Log.w(TAG,"activity_id -->"+activity_id+"job_id : "+job_id);

        }





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
            checkDataStoreResponseCall();




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
                                if(!isStarted){
                                    showPopupStartJoB();
                                }

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
            TextView txt_jobstatus = alertdialog.findViewById(R.id.txt_jobstatus);
            TextView txt_job_content = alertdialog.findViewById(R.id.txt_job_content);
            LinearLayout ll_start = alertdialog.findViewById(R.id.ll_start);
            LinearLayout ll_pause = alertdialog.findViewById(R.id.ll_pause);
            LinearLayout ll_stop = alertdialog.findViewById(R.id.ll_stop);
            LinearLayout ll_resume = alertdialog.findViewById(R.id.ll_resume);
            ImageView img_close = alertdialog.findViewById(R.id.img_close);
            Button btn_back = alertdialog.findViewById(R.id.btn_back);
            btn_back.setVisibility(View.GONE);

           if(isStarted){
               ll_start.setVisibility(View.GONE);
               ll_pause.setVisibility(View.VISIBLE);
               ll_stop.setVisibility(View.VISIBLE);
               ll_resume.setVisibility(View.GONE);
           }else if(isPaused){
               ll_start.setVisibility(View.GONE);
               ll_pause.setVisibility(View.GONE);
               ll_stop.setVisibility(View.VISIBLE);
               ll_resume.setVisibility(View.VISIBLE);

            }else if(isResumed){
                ll_start.setVisibility(View.GONE);
                ll_pause.setVisibility(View.VISIBLE);
                ll_stop.setVisibility(View.VISIBLE);
                ll_resume.setVisibility(View.GONE);

           }else if(isStopped){
                ll_start.setVisibility(View.GONE);
                ll_pause.setVisibility(View.GONE);
                ll_stop.setVisibility(View.GONE);
                ll_resume.setVisibility(View.GONE);
                img_close.setVisibility(View.GONE);
               btn_back.setVisibility(View.VISIBLE);
               txt_jobstatus.setText("Stop");
               txt_job_content.setText("Your job stopped");

           }else{
               ll_start.setVisibility(View.VISIBLE);
               ll_pause.setVisibility(View.GONE);
               ll_stop.setVisibility(View.GONE);
               ll_resume.setVisibility(View.GONE);

           }


            ll_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmStartJobDialog();
                }
            });

            ll_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmPauseJobDialog();
                }
            });

            ll_resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmResumeJobDialog();
                }
            });

            ll_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmStopJobDialog();
                }
            });
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertdialog.dismiss();
                    onBackPressed();
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
                        startWorkRequestCall();

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
    private void confirmPauseJobDialog(){
         alertDialogBuilder = new AlertDialog.Builder(GroupListActivity.this);
        alertDialogBuilder.setMessage("Would you like to Pause job tracking ?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        pausejobRequestCall();

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
    private void confirmResumeJobDialog(){
         alertDialogBuilder = new AlertDialog.Builder(GroupListActivity.this);
        alertDialogBuilder.setMessage("Would you like to Resume job tracking ?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        resumejobRequestCall();

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
    private void confirmStopJobDialog(){
         alertDialogBuilder = new AlertDialog.Builder(GroupListActivity.this);
        alertDialogBuilder.setMessage("Would you like to Stop job tracking ?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        pausejobRequestCall1();


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
    private void startWorkRequestCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.startWorkRequestCall(RestUtils.getContentType(), startWorkRequest());
        Log.w(TAG,"startWorkRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"startWorkRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        dialog.dismiss();
                        alertdialog.dismiss();


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private StartWorkRequest startWorkRequest() {

        /*
         * user_id : 123456
         * activity_id : 123456
         * job_id : 123456
         * start_time : 23-10-2021 11:00 AM
         * date_of_create : 23-10-2021 11:00 AM
        */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());



        StartWorkRequest startWorkRequest = new StartWorkRequest();
        startWorkRequest.setUser_id(userid);
        startWorkRequest.setActivity_id(activity_id);
        startWorkRequest.setJob_id(job_id);
        startWorkRequest.setStart_time(currentDateandTime);
        startWorkRequest.setDate_of_create("");


        Log.w(TAG,"startWorkRequest "+ new Gson().toJson(startWorkRequest));
        return startWorkRequest;
    }


    @SuppressLint("LogNotTimber")
    private void pausejobRequestCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.pausejobRequestCall(RestUtils.getContentType(), pauseJobRequest());
        Log.w(TAG,"pausejobRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"pausejobRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            dialog.dismiss();
                            alertdialog.dismiss();


                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private PauseJobRequest pauseJobRequest() {

        /*
         * user_id : 123456
         * activity_id : 123456
         * job_id : 123456
         * pause_time : 23-10-2021 11:00 AM
        */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());



        PauseJobRequest pauseJobRequest = new PauseJobRequest();
        pauseJobRequest.setUser_id(userid);
        pauseJobRequest.setActivity_id(activity_id);
        pauseJobRequest.setJob_id(job_id);
        pauseJobRequest.setPause_time(currentDateandTime);



        Log.w(TAG,"pauseJobRequest "+ new Gson().toJson(pauseJobRequest));
        return pauseJobRequest;
    }

    @SuppressLint("LogNotTimber")
    private void pausejobRequestCall1() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.pausejobRequestCall(RestUtils.getContentType(), pauseJobRequest());
        Log.w(TAG,"pausejobRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"pausejobRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        dialog.dismiss();
                        alertdialog.dismiss();
                        stopjobRequestCall();





                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @SuppressLint("LogNotTimber")
    private void resumejobRequestCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.resumejobRequestCall(RestUtils.getContentType(), resumeJobRequest());
        Log.w(TAG,"resumejobRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"resumejobRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        dialog.dismiss();
                        alertdialog.dismiss();

                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ResumeJobRequest resumeJobRequest() {

        /*
         * user_id : 123456
         * activity_id : 123456
         * job_id : 123456
         * resume_time : 23-10-2021 11:00 AM
        */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());



        ResumeJobRequest resumeJobRequest = new ResumeJobRequest();
        resumeJobRequest.setUser_id(userid);
        resumeJobRequest.setActivity_id(activity_id);
        resumeJobRequest.setJob_id(job_id);
        resumeJobRequest.setResume_time(currentDateandTime);



        Log.w(TAG,"resumeJobRequest "+ new Gson().toJson(resumeJobRequest));
        return resumeJobRequest;
    }


    @SuppressLint("LogNotTimber")
    private void stopjobRequestCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.stopjobRequestCall(RestUtils.getContentType(), stopJobRequest());
        Log.w(TAG,"stopjobRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"stopjobRequestCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        dialog.dismiss();
                        alertdialog.dismiss();
                        onBackPressed();



                    } else {
                        dialog.dismiss();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private StopJobRequest stopJobRequest() {

        /*
         * user_id : 123456
         * activity_id : 123456
         * job_id : 123456
         * stop_time : 23-10-2021 11:00 AM
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());



        StopJobRequest stopJobRequest = new StopJobRequest();
        stopJobRequest.setUser_id(userid);
        stopJobRequest.setActivity_id(activity_id);
        stopJobRequest.setJob_id(job_id);
        stopJobRequest.setStop_time(currentDateandTime);



        Log.w(TAG,"stopJobRequest "+ new Gson().toJson(stopJobRequest));
        return stopJobRequest;
    }




    @SuppressLint("LogNotTimber")
    private void checkDataStoreResponseCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<CheckDataStoreResponse> call = apiInterface.checkDataStoreResponseCall(RestUtils.getContentType(), checkDataStoreRequest());
        Log.w(TAG,"checkDataStoreResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CheckDataStoreResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<CheckDataStoreResponse> call, @NonNull Response<CheckDataStoreResponse> response) {

                Log.w(TAG,"checkDataStoreResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        dialog.dismiss();
                        groupdetailmanagmentResponseCall();
                        if(response.body().getData() != null){
                            if(response.body().getData().getWork_status() != null && !response.body().getData().getWork_status().isEmpty()){
                                if(response.body().getData().getWork_status().equalsIgnoreCase("Started")){
                                    isStarted = true;
                                }else if(response.body().getData().getWork_status().equalsIgnoreCase("Paused")){
                                    isPaused = true;
                                } else  if(response.body().getData().getWork_status().equalsIgnoreCase("Resumed")){
                                    isResumed = true;
                                }else  if(response.body().getData().getWork_status().equalsIgnoreCase("Stopped")){
                                    isStopped = true;
                                }
                            }


                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<CheckDataStoreResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void checkDataStoreResponseCall1() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<CheckDataStoreResponse> call = apiInterface.checkDataStoreResponseCall(RestUtils.getContentType(), checkDataStoreRequest());
        Log.w(TAG,"checkDataStoreResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CheckDataStoreResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<CheckDataStoreResponse> call, @NonNull Response<CheckDataStoreResponse> response) {

                Log.w(TAG,"checkDataStoreResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        dialog.dismiss();
                        if(response.body().getData() != null){
                            if(response.body().getData().getWork_status() != null && !response.body().getData().getWork_status().isEmpty()){
                                if(response.body().getData().getWork_status().equalsIgnoreCase("Started")){
                                    isStarted = true;
                                }else if(response.body().getData().getWork_status().equalsIgnoreCase("Paused")){
                                    isPaused = true;
                                } else  if(response.body().getData().getWork_status().equalsIgnoreCase("Resumed")){
                                    isResumed = true;
                                }else  if(response.body().getData().getWork_status().equalsIgnoreCase("Stopped")){
                                    isStopped = true;
                                }
                            }
                            showPopupStartJoB();


                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<CheckDataStoreResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private CheckDataStoreRequest checkDataStoreRequest() {

        /*
         * user_id : 123456
         * activity_id : 123456
         * job_id : 123456
         * start_time : 23-10-2021 11:00 AM
         * date_of_create : 23-10-2021 11:00 AM
        */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());



        CheckDataStoreRequest checkDataStoreRequest = new CheckDataStoreRequest();
        checkDataStoreRequest.setUser_id(userid);
        checkDataStoreRequest.setActivity_id(activity_id);
        checkDataStoreRequest.setJob_id(job_id);
        Log.w(TAG,"checkDataStoreRequest "+ new Gson().toJson(checkDataStoreRequest));
        return checkDataStoreRequest;
    }





}