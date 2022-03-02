package com.triton.johnsonapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.session.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerDetailsActivity extends AppCompatActivity {

    private String TAG ="CustomerDetailsActivity";

    String userid,username;

    String networkStatus = "",message,activity_id,job_id;
    private String status;
    private String fromactivity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_customer_name)
    TextView txt_customer_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_customer_id)
    TextView txt_customer_id;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_customer_emailid)
    TextView txt_customer_emailid;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_continue)
    Button btn_continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        String useremailid = user.get(SessionManager.KEY_EMAILID);

        username = user.get(SessionManager.KEY_USERNAME);
        txt_customer_name.setText(username);
        txt_customer_id.setText(userid);
        if(useremailid != null) {
            txt_customer_emailid.setText(useremailid);
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupListActivity.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("job_id",job_id);
                intent.putExtra("status",status);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity_id = extras.getString("activity_id");
            job_id = extras.getString("job_id");
            status = extras.getString("status");
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"activity_id -->"+activity_id+"job_id : "+job_id+" status : "+status+" fromactivity : "+fromactivity);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("AllJobListActivity")){
            Intent intent = new Intent(CustomerDetailsActivity.this, AllJobListActivity.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("status",status);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);

        }else{
            Intent intent = new Intent(CustomerDetailsActivity.this, AllJobListActivity.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("status",status);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);
        }
    }
}