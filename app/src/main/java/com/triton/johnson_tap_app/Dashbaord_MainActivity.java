package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.triton.johnson_tap_app.activity.MainActivity;
import com.triton.johnson_tap_app.activity.UI_Serv_LeadsActivity;
import com.triton.johnson_tap_app.activity.UI_Servenq_RequestActivity;
import com.triton.johnson_tap_app.session.SessionManager;
import com.triton.johnsonapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashbaord_MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.general)
    Button general;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.services)
    Button services;
    private SessionManager session;
    private String TAG ="MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashbaord_main);

        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");
        session = new SessionManager(getApplicationContext());

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Dashbaord_MainActivity.this, MainActivity.class);
                startActivity(send);

            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Dashbaord_MainActivity.this, Main_Menu_ServicesActivity.class);
                startActivity(send);
            }
        });

    }
}