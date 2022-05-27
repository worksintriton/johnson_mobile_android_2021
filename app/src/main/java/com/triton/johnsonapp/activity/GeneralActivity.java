package com.triton.johnsonapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.triton.johnsonapp.Forms.GeneralActivityForm;
import com.triton.johnsonapp.Forms.InputValueFormListActivity;
import com.triton.johnsonapp.Forms.SubordActivityForm;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class GeneralActivity extends AppCompatActivity {


    CardView cv_root,cv_root1,cv_root2;
    private String group_id = "";
    private String subgroup_id = "";
    private String TAG ="GeneralActivity";

    String userid,username,userrole;
    private String fromactivity;
    String networkStatus = "",message,activity_id,job_id;
    private String status;
    private String fromto;
    private String job_detail_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);

        username = user.get(SessionManager.KEY_USERNAME);

        userrole = user.get(SessionManager.KEY_USERROLE);


        Log.w(TAG,"userrole  : "+userrole);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity_id = extras.getString("activity_id");
            job_id = extras.getString("job_id");
            status = extras.getString("status");
            fromactivity = extras.getString("fromactivity");
            fromto = extras.getString("fromto");
            job_detail_no = extras.getString("job_detail_no");
            Log.w(TAG,"activity_id -->"+activity_id+"job_id : "+job_id+" status : "+status+" fromactivity : "+fromactivity+" job_detail_no : "+job_detail_no+" fromto : "+fromto);



        }
        cv_root = (CardView) findViewById(R.id.cv_root);
        cv_root1 = (CardView) findViewById(R.id.cv_root1);
        cv_root2 = (CardView) findViewById(R.id.cv_root2);

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        cv_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralActivity.this, GeneralActivityForm.class);
                startActivity(intent);
            }
        });

        cv_root1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralActivity.this, SubordActivityForm.class);
                startActivity(intent);
            }
        });

        cv_root2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralActivity.this, InputValueFormListActivity.class);
                intent.putExtra("job_id","General");
                intent.putExtra("group_id","6246a3b824aa6055f7f263e8");
                intent.putExtra("subgroup_id","");
                startActivity(intent);
            }
        });

    }
}