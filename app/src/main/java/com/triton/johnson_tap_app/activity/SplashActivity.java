package com.triton.johnson_tap_app.activity;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.BuildConfig;

import com.triton.johnson_tap_app.Dashbaord_MainActivity;
import com.triton.johnson_tap_app.DownloadapkfileActivity;
import com.triton.johnson_tap_app.GetFetchLatestVersionResponse;
import com.triton.johnson_tap_app.Getlatestversionrequest;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.session.SessionManager;
import com.triton.johnsonapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    /**
     * Session to check whether user is login or not.
     */
    SessionManager sessionManager;

    // user level
    String user_level;

    int haslocationpermission;

    String TAG = "SplashActivity";
    private String VersionUpdate,VersionUpdate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SimpleDateFormat currentDate = new SimpleDateFormat("dd.MM.yy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        TextView txt_version = (TextView)findViewById(R.id.txt_version);
        TextView txt_version1 = (TextView)findViewById(R.id.txt_version1);
//        String Version1 = txt_version1.getText().toString();
//        Log.w(TAG,"VERSION1--"+Version1);
//        //txt_version.setText("Version "+thisDate+".1");
//        String Version = txt_version.getText().toString();
//        int versionCode = BuildConfig.VERSION_CODE;
//        VersionUpdate = Version+"."+versionCode;
//        VersionUpdate1 = Version1+"."+versionCode;
//        txt_version.setText("Version "+VersionUpdate);
//        Log.w(TAG,"Version Code----"+VersionUpdate);
        String versionName = BuildConfig.VERSION_NAME;

        sessionManager = new SessionManager(this);

        getlatestversion();


    }

    private void getlatestversion() {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetFetchLatestVersionResponse> call = apiInterface.getlatestversionrequestcall();
        Log.w(TAG, "url  :%s" + call.request().url().toString());
        call.enqueue(new Callback<GetFetchLatestVersionResponse>() {
            @Override
            public void onResponse(Call<GetFetchLatestVersionResponse> call, Response<GetFetchLatestVersionResponse> response) {
                Log.w(TAG, "Submitted_status ---");
                if(response.body() !=null)
                {
                    Log.w(TAG, "Submitted_status ---" +response.body().getMessage());
                    String Submitted_status = response.body().getStatus();
                    Log.w(TAG,"dATARE-000000--"+response.body().getData().getVersion());

                    if(Submitted_status !=null && Submitted_status.equalsIgnoreCase("Success"))
                    {
                        Log.w(TAG,"dATA"+response.body().getData().getVersion());

                        if(response.body().getData().getVersion().equals("16.05.2022.1")){
                            Thread timerThread = new Thread() {
                                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                                public void run() {
                                    try {
                                        sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } finally {


                                        Log.w(TAG,"ELSE"+sessionManager.isLoggedIn());


                                        // check whether user is logged in or not
                                        if (sessionManager.isLoggedIn()) {

                                            Intent intent = new Intent(SplashActivity.this, Dashbaord_MainActivity.class);
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

                    }else
                    {

                    }
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