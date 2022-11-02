package com.triton.johnsonapp.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.BuildConfig;

import com.google.gson.Gson;
import com.triton.johnsonapp.DownloadapkfileActivity;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.db.CommonUtil;
import com.triton.johnsonapp.db.DbHelper;
import com.triton.johnsonapp.db.DbUtil;
import com.triton.johnsonapp.requestpojo.Getlatestversionrequest;
import com.triton.johnsonapp.session.SessionManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String user_level;
    int haslocationpermission;
    String TAG = "SplashActivity";
    private String VersionUpdate,VersionUpdate1;
    Context context;
    String thisDate;
    String mydate;


    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

//        SimpleDateFormat currentDate = new SimpleDateFormat("dd.MM.yy ");
//        Date todayDate = new Date();
//        thisDate = currentDate.format(todayDate);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        thisDate = sdf.format(new Date());
        Log.e("Nish" , "" + thisDate);


        TextView txt_version = (TextView)findViewById(R.id.txt_version);
        TextView txt_version1 = (TextView)findViewById(R.id.txt_version1);
        TextView device_id = (TextView)findViewById(R.id.device_id);

        CommonUtil.dbUtil = new DbUtil(context);
        CommonUtil.dbUtil.open();
        CommonUtil.dbHelper = new DbHelper(context);

        getDate();

        String Version1 = txt_version1.getText().toString();
        Log.w(TAG,"VERSION1--"+Version1);
        //txt_version.setText("Version "+thisDate+".1");
        String Version = txt_version.getText().toString();
        int versionCode = BuildConfig.VERSION_CODE;
        VersionUpdate = Version+"."+versionCode;
        VersionUpdate1 = Version1+"."+versionCode;
        txt_version.setText("Version "+VersionUpdate);
        Log.e(TAG,"Version Code----"+VersionUpdate);
        @SuppressLint("HardwareIds")
//        String ID = Settings.Secure.getString(getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        device_id.setText(deviceId);
        Log.e("deviceid",deviceId);
        String versionName = BuildConfig.VERSION_NAME;


        sessionManager = new SessionManager(this);

        getlatestversion();


    }

    @SuppressLint("Range")
    private void getDate() {

        Cursor cursor = CommonUtil.dbUtil.getDate();

        Log.e("Nish Data"," " +  cursor.getCount());

        if (cursor.moveToLast()){

            mydate = cursor.getString(cursor.getColumnIndex(DbHelper.CURRENT_DATE));
            Log.e("My Date Nish",""+mydate);
        }
    }

    private void getlatestversion() {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetFetchLatestVersionResponse> call = apiInterface.getlatestversionrequestcall();
        Log.w(TAG, "url  :%s " + call.request().url().toString());
        call.enqueue(new Callback<GetFetchLatestVersionResponse>() {
            @Override
            public void onResponse(Call<GetFetchLatestVersionResponse> call, Response<GetFetchLatestVersionResponse> response) {
                Log.w(TAG, "Submitted_status ---" + new Gson().toJson(response.body()));
                if(response.body() !=null)
                {
                    Log.w(TAG, "Submitted_status ---" +response.body().getMessage());
                    String Submitted_status = response.body().getStatus();
                    Log.w(TAG,"dATARE-000000--"+response.body().getData().getVersion());

                    if(Submitted_status !=null && Submitted_status.equalsIgnoreCase("Success"))
                    {
                        Log.w(TAG,"dATA"+response.body().getData().getVersion());

                        if(response.body().getData().getVersion().equals("21.10.2022")){
                            Thread timerThread = new Thread() {
                                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                                public void run() {
                                    try {
                                        sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } finally {

                                       // Log.e(TAG,"ELSE"+sessionManager.isLoggedIn());

//                                        // check whether user is logged in or not
//                                        if (sessionManager.isLoggedIn()) {
//                                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                                            startActivity(intent);
//                                            overridePendingTransition(R.anim.new_right, R.anim.new_left);
//                                        }

                                        Log.e("This Date",""+thisDate);
                                        Log.e("MyDate","" + mydate);
                                        if (thisDate.equals(mydate) && sessionManager.isLoggedIn()){
                                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                        }
                                        else {
                                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                        }

                                    }
                                }
                            };
                            timerThread.start();
                        }
                        else{
                            Log.w(TAG,"dATA-0000--"+response.body().getData().getVersion());
                            String apk_link = response.body().getData().getApk_link();
                            String apk_version = response.body().getData().getVersion();
                            Intent intent = new Intent(SplashActivity.this, DownloadapkfileActivity.class);
                            intent.putExtra("apk_link",apk_link);
                            intent.putExtra("apk_version",apk_version);
                            startActivity(intent);
                        }
                }

//                    Thread timerThread = new Thread() {
//                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//                        public void run() {
//                            try {
//                                sleep(3000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            } finally {
//
//
//                                Log.w(TAG,"ELSE"+sessionManager.isLoggedIn());
//
//
//                                // check whether user is logged in or not
//                                if (sessionManager.isLoggedIn()) {
//
//                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.new_right, R.anim.new_left);
//                                }
//
//                                else {
//
//                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.new_right, R.anim.new_left);
//                                }
//
//                            }
//                        }
//                    };
//                    timerThread.start();

                    }else {


                    }
                }

            @Override
            public void onFailure(Call<GetFetchLatestVersionResponse> call, Throwable t) {
                Log.w(TAG, "Submitted_status 1111---"+t.getLocalizedMessage());
            }
        });
    }

    private Getlatestversionrequest getlatestversionrequest() {
        Getlatestversionrequest getlatestversionrequest = new Getlatestversionrequest();
        getlatestversionrequest.setVersion("");
        return getlatestversionrequest;
    }


}