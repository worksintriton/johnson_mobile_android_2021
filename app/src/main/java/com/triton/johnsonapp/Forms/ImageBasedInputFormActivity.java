package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.GroupListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageBasedInputFormActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_toolbar_title)
    TextView txt_toolbar_title;

    private String TAG ="ImageBasedInputFormActivity";

    String string_value,message,service_id,activity_id,job_id,group_id,subgroup_id,group_detail_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_exit)
    TextView txt_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_based_input_form);
        Log.w(TAG,"Oncreate -->");
        ButterKnife.bind(this);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            service_id = extras.getString("service_id");

            group_id = extras.getString("group_id");
            group_detail_name = extras.getString("group_detail_name");

            activity_id = extras.getString("activity_id");

            job_id = extras.getString("job_id");

            subgroup_id= extras.getString("subgroup_id");

            Log.w(TAG,"activity_id -->"+activity_id);

            Log.w(TAG,"group_id -->"+group_id);

            Log.w(TAG,"service_id" + service_id);

            if(group_detail_name != null){
                txt_toolbar_title.setText(group_detail_name);
            }

        }

        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarningExit();
            }
        });
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        showWarningExit();
    }

    private void showWarningExit() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ImageBasedInputFormActivity.this, GroupListActivity.class);
                        intent.putExtra("activity_id", activity_id);
                        intent.putExtra("job_id", job_id);
                        startActivity(intent);
                        overridePendingTransition(R.anim.new_right, R.anim.new_left);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

}