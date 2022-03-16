package com.triton.johnsonapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.triton.johnsonapp.R;

public class TestOrientationActivity extends AppCompatActivity {

    String TAG = "TestOrientationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_orientation);
        Log.w(TAG,"onCreate-->");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG,"onStart-->");
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG,"onResume-->");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG,"onPause-->");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG,"onStop-->");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG,"onRestart-->");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG,"onDestroy-->");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(TAG,"onSaveInstanceState-->");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w(TAG,"onRestoreInstanceState-->");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w(TAG,"onConfigurationChanged-->");
    }
}