package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Preventive_ServiceActivity extends AppCompatActivity {

    ImageView iv_back;
    CardView cv_new_job, cv_pasused_job;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_preventive_service);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        cv_new_job = (CardView) findViewById(R.id.cv_new_job);
        cv_pasused_job = (CardView) findViewById(R.id.cv_pasused_job);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Preventive_ServiceActivity.this,ServicesActivity.class);
                startActivity(send);

            }
        });

        cv_new_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Preventive_ServiceActivity.this,Job_DetailsActivity.class);
                startActivity(send);

            }
        });

        cv_pasused_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Preventive_ServiceActivity.this,Paused_ServicesActivity.class);
                send.putExtra("value","pasused");
                startActivity(send);

            }
        });
    }
}