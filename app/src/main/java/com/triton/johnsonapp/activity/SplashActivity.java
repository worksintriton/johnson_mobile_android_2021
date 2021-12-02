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


        Thread timerThread = new Thread() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {


                    // check the android sdk version for runtime permission
                    if (Build.VERSION.SDK_INT >= 23) {
                /*        Log.w(TAG,"IF"+sessionManager.isLoggedIn());*/

                        insertmappermission();

                    } else {

                        Log.w(TAG,"ELSE"+sessionManager.isLoggedIn());

                        // check whether user is logged in or not
                        if (sessionManager.isLoggedIn()) {


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
            }
        };
        timerThread.start();
    }


    @SuppressLint("NewApi")
    private void insertmappermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            haslocationpermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) | checkSelfPermission(Manifest.permission.CAMERA) | checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (haslocationpermission != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG,"insertmappermission  if");

                Intent myIntent = new Intent(SplashActivity.this, Permission_Activity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myIntent);
                finish();

            } else {
                Log.w(TAG,"insertmappermission  else"+sessionManager.isLoggedIn());
                Log.w(TAG,"insertmappermission  else"+" user_level : "+user_level);

                if (sessionManager.isLoggedIn()) {

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

        }else{
            Log.w(TAG,"insertmappermission else");
            Intent myIntent = new Intent(SplashActivity.this, Permission_Activity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myIntent);
            finish();
        }

    }
}