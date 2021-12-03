package com.triton.johnsonapp.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.Permission_Activity;

public class SplashActivity extends AppCompatActivity {

    /**
     * Session to check whether user is login or not.
     */
    SessionManager sessionManager;

    // user level
    String user_level;

    int haslocationpermission;

    String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

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


                            Intent intent = new Intent(SplashActivity.this, ServiceListActivity.class);
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

}