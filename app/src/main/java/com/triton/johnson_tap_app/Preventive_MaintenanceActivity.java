package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.triton.johnsonapp.R;

public class Preventive_MaintenanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_preventive_maintenance);
    }
}