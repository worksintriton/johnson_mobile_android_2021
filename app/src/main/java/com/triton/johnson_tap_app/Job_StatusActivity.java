package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Job_StatusActivity extends AppCompatActivity {

    ImageView iv_back;
    LinearLayout lin_layout,lin_layout1,lin_layout2,layout,layout1,layout2;
    TextView text,text1,text2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_job_status);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        lin_layout = (LinearLayout) findViewById(R.id.lin_layout);
        lin_layout1 = (LinearLayout) findViewById(R.id.lin_layout1);
        lin_layout2 = (LinearLayout) findViewById(R.id.lin_layout2);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        text = (TextView) findViewById(R.id.text);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Job_StatusActivity.this, Main_Menu_ServicesActivity.class);
                startActivity(send);

            }
        });

        lin_layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               layout.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);

                lin_layout2.setBackgroundResource(R.drawable.background1);
                lin_layout1.setBackgroundResource(R.drawable.background1);
                lin_layout.setBackgroundColor(Color.parseColor("#B00303"));
                text.setTextColor(Color.WHITE);
                text1.setTextColor(Color.BLACK);
                text2.setTextColor(Color.BLACK);

            }
        });

        lin_layout1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                layout.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
                lin_layout.setBackgroundResource(R.drawable.background1);
                lin_layout2.setBackgroundResource(R.drawable.background1);
                lin_layout1.setBackgroundColor(Color.parseColor("#B00303"));

                text1.setTextColor(Color.WHITE);
                text.setTextColor(Color.BLACK);
                text2.setTextColor(Color.BLACK);

            }
        });

        lin_layout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                layout.setVisibility(View.GONE);
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);

                lin_layout.setBackgroundResource(R.drawable.background1);
                lin_layout1.setBackgroundResource(R.drawable.background1);
                lin_layout2.setBackgroundColor(Color.parseColor("#B00303"));


                text2.setTextColor(Color.WHITE);
                text.setTextColor(Color.BLACK);
                text1.setTextColor(Color.BLACK);

            }
        });
    }
}