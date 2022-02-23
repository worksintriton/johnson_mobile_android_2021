package com.triton.johnsonapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.session.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    private String TAG ="MainActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_job)
    Button btn_job;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_activity)
    Button btn_activity;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");
        session = new SessionManager(getApplicationContext());

        btn_activity.setVisibility(View.INVISIBLE);

        btn_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatusActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);

            }
        });

        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityBasedActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showWarningLogout();

    }


    private void showWarningLogout() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> session.logoutUser())
                .setNegativeButton("No", null)
                .show();
    }
}