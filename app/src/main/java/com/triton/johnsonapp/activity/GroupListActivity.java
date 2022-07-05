package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.adapter.GroupListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.CheckDataStoreRequest;
import com.triton.johnsonapp.requestpojo.CheckLocationRequest;
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
import com.triton.johnsonapp.service.GPSTracker;
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

public class
GroupListActivity extends AppCompatActivity  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private String TAG ="GroupListActivity";

    String userid,username,userrole;
    int form_type;


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
    @BindView(R.id.txt_job_no)
    TextView txt_job_no;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_toolbar_title)
    TextView txt_toolbar_title;

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
    private String status;
    private String fromactivity;
    private String fromto;
    private String job_detail_no;


    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private GoogleApiClient googleApiClient;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double latitude;
    private double longitude;
    private Dialog alertDialog;


    @SuppressLint("SetTextI18n")
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

        userrole = user.get(SessionManager.KEY_USERROLE);


        Log.w(TAG,"userrole  : "+userrole);

        googleApiConnected();
        Log.e("jhonson","started");



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
            status = extras.getString("status");
            fromactivity = extras.getString("fromactivity");
            fromto = extras.getString("fromto");
            job_detail_no = extras.getString("job_detail_no");
            Log.w(TAG,"activity_id -->"+activity_id+"job_id : "+job_id+" status : "+status+" fromactivity : "+fromactivity+" job_detail_no : "+job_detail_no+" fromto : "+fromto);



        }

        if(job_id != null){
            txt_job_no.setVisibility(View.VISIBLE);
            txt_job_no.setText("Job No : "+job_id);
        }else{
            txt_job_no.setVisibility(View.GONE);
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
        if(fromactivity != null && fromactivity.equalsIgnoreCase("AllJobListActivity")){
            Intent intent = new Intent(GroupListActivity.this, AllJobListActivity.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("status",status);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);

        }  else if(fromactivity != null && fromactivity.equalsIgnoreCase("CustomerDetailsActivity")){
            Intent intent = new Intent(GroupListActivity.this, CustomerDetailsActivity.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("job_id",job_id);
            intent.putExtra("status",status);
            intent.putExtra("job_detail_no",job_detail_no);
            intent.putExtra("fromto",fromto);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);

        }else{
            Intent intent = new Intent(GroupListActivity.this, CustomerDetailsActivity.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("status",status);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);

        }



    }

    @SuppressLint("LogNotTimber")
    private void groupdetailmanagmentResponseCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        Log.e("jhonson","dialogopen");
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GroupDetailManagementResponse> call = apiInterface.groupdetailmanagmentResponseCall(RestUtils.getContentType(), GroupDetailManagementRequest());
        Log.w(TAG,"GroupDetailManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<GroupDetailManagementResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<GroupDetailManagementResponse> call, @NonNull Response<GroupDetailManagementResponse> response) {
                Log.e("jhonson",""+ new Gson().toJson(response.body()) );
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
        Log.e("userid_",userid);
        GroupDetailManagementRequest.setSearch_string(search_string);
        Log.e("userid_",search_string);
        GroupDetailManagementRequest.setJob_detail_id(job_id);
        Log.e("userid_",job_id);

        Log.w(TAG,"GroupDetailManagementRequest "+ new Gson().toJson(GroupDetailManagementRequest));
        return GroupDetailManagementRequest;
    }

    @SuppressLint("LogNotTimber")
    private void setView(List<GroupDetailManagementResponse.DataBean> dataBeanList) {


        rv_grouplist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_grouplist.setItemAnimator(new DefaultItemAnimator());
        GroupListAdapter groupListAdapter = new GroupListAdapter(this, dataBeanList,activity_id,job_id,status,fromactivity);
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
            alertdialog.setCancelable(false);
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


            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                    Intent intent = new Intent(GroupListActivity.this, AllJobListActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("status",status);
                    startActivity(intent);
                    overridePendingTransition(R.anim.new_right, R.anim.new_left);
                }
            });
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
                        if(latitude != 0 && longitude != 0){
                            startWorkRequestCall();

                          /*  if(userrole != null && userrole.equalsIgnoreCase("ESP")){
                                startWorkRequestCall();
                            } else if(userrole != null && userrole.equalsIgnoreCase("USER")){
                                checkLocationRequestCall();

                            }*/




                        }else{
                            googleApiConnected();
                        }


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
         *   job_lat;
            job_long;
        */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());



        StartWorkRequest startWorkRequest = new StartWorkRequest();
        startWorkRequest.setUser_id(userid);
        startWorkRequest.setActivity_id(activity_id);
        startWorkRequest.setJob_id(job_id);
        startWorkRequest.setStart_time(currentDateandTime);
        startWorkRequest.setDate_of_create("");
        startWorkRequest.setJob_lat(latitude);
        startWorkRequest.setJob_long(longitude);


        Log.w(TAG,"startWorkRequest "+ new Gson().toJson(startWorkRequest));
        return startWorkRequest;
    }

    @SuppressLint("LogNotTimber")
    private void checkLocationRequestCall() {
        dialog = new Dialog(GroupListActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.checkLocationRequestCall(RestUtils.getContentType(), checkLocationRequest());
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
                        startWorkRequestCall();


                    } else {
                        dialog.dismiss();
                        showErrorLoading(response.body().getMessage());

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
    private CheckLocationRequest checkLocationRequest() {

        /*
         * job_id : 61f222396667ac391fc85c55
         * job_long : 80.2235346
         * job_lat : 12.9831482
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        CheckLocationRequest checkLocationRequest = new CheckLocationRequest();
        checkLocationRequest.setJob_id(job_id);
        checkLocationRequest.setJob_lat(latitude);
        checkLocationRequest.setJob_long(longitude);


        Log.w(TAG,"checkLocationRequest "+ new Gson().toJson(checkLocationRequest));
        return checkLocationRequest;
    }

    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
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

    @SuppressLint("LogNotTimber")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS_GPS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                case Activity.RESULT_CANCELED:
                    getMyLocation();
                    break;
            }
        }



        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_schedule);
        if (fragment != null) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull String @org.jetbrains.annotations.NotNull [] permissions, @org.jetbrains.annotations.NotNull int @org.jetbrains.annotations.NotNull [] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getMyLocation();

                }
            } else {
                Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void getLatandLong() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GroupListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                GPSTracker gps = new GPSTracker(getApplicationContext());
                // Check if GPS enabled
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if(latitude != 0 && longitude != 0){
                        LatLng latLng = new LatLng(latitude,longitude);
                        Log.w(TAG,"getLatandLong latitude : "+latitude+" longitude : "+longitude);


                    }




                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void googleApiConnected() {
        googleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(getApplicationContext())).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();

    }
    private void checkLocation() {
        try {
            LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ignored) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ignored) {
            }

            if (!gps_enabled && !network_enabled) {

                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getMyLocation();
                }

            } else {
                getLatandLong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();







    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        permissionChecking();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(GoogleMap googleMap) {


    }
    private void permissionChecking() {
        if (getApplicationContext() != null) {
            if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(GroupListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 5);

            } else {

                checkLocation();
            }
        }
    }
    public void getMyLocation() {
        if (googleApiClient != null) {

            if (googleApiClient.isConnected()) {
                if(getApplicationContext() != null){
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                }

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(2000);
                locationRequest.setFastestInterval(2000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(result1 -> {
                    Status status = result1.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied.
                            // You can initialize location requests here.
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                            Handler handler = new Handler();
                            int delay = 1000; //milliseconds

                            handler.postDelayed(new Runnable() {
                                @SuppressLint({"LongLogTag", "LogNotTimber"})
                                public void run() {
                                    //do something
                                    if(getApplicationContext() != null) {
                                        if(latitude != 0 && longitude != 0) {
                                            LatLng latLng = new LatLng(latitude,longitude);

                                            Log.w(TAG,"getMyLocation latitude : "+latitude+" longitude : "+longitude);

                                        }
                                    }
                                }
                            }, delay);


                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(GroupListActivity.this, REQUEST_CHECK_SETTINGS_GPS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                });
            }


        }
    }





}