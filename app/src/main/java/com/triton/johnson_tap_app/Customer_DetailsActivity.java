package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Customer_DetailsActivity extends AppCompatActivity {

    ImageView iv_back;
    Button btn_continue;
    Dialog dialog;
    Dialog alertdialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_details);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_continue = (Button) findViewById(R.id.btn_continue);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Customer_DetailsActivity.this, Job_DetailsActivity.class);
                startActivity(send);

            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Customer_DetailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.startjob_popup_layout, null);

                TextView txt_jobstatus = mView.findViewById(R.id.txt_jobstatus);
                TextView txt_job_content = mView.findViewById(R.id.txt_job_content);
                LinearLayout ll_start = mView.findViewById(R.id.ll_start);
                LinearLayout ll_pause = mView.findViewById(R.id.ll_pause);
                LinearLayout ll_stop = mView.findViewById(R.id.ll_stop);
                LinearLayout ll_resume = mView.findViewById(R.id.ll_resume);
                ImageView img_close = mView.findViewById(R.id.img_close);
                Button btn_back = mView.findViewById(R.id.btn_back);
                btn_back.setVisibility(View.GONE);
                txt_jobstatus.setVisibility(View.GONE);
                ll_resume.setVisibility(View.GONE);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                ll_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent send = new Intent(Customer_DetailsActivity.this,InputValueFormListActivity.class);
                        startActivity(send);
                        dialog.dismiss();
                    }
                });

                ll_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent send = new Intent(Customer_DetailsActivity.this, Paused_ServicesActivity.class);
                        startActivity(send);
                    }
                });

                ll_stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     //   confirmStopJobDialog();
                        dialog.dismiss();
                    }
                });
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //  onBackPressed();
                    }
                });


                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
//                        Intent intent = new Intent(GroupListActivity.this, AllJobListActivity.class);
//                        intent.putExtra("activity_id",activity_id);
//                        intent.putExtra("status",status);
//                        startActivity(intent);
                        overridePendingTransition(R.anim.new_right, R.anim.new_left);
                    }
                });


            }
        });

    }

}