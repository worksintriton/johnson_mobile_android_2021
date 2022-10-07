package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.triton.johnsonapp.R;

public class JointInspectorInputFormActivity extends AppCompatActivity {

    private String TAG ="JointInspectorInputFormActivity";

    String string_value,message,service_id,activity_id,job_id,group_id,subgroup_id;
    private String _id;
    private String job_detail_id;
    private String group_detail_name;
    private String fromactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joint_inspector_input_form);
        Log.e("Hi Nish","Joint Inspection");
        Log.w(TAG,"Oncreate -->");



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getString("_id");
            activity_id = extras.getString("activity_id");
            job_detail_id= extras.getString("job_detail_id");
            group_id = extras.getString("group_id");
            group_detail_name = extras.getString("group_detail_name");
            job_id = extras.getString("job_id");
            subgroup_id= extras.getString("subgroup_id");
            fromactivity= extras.getString("fromactivity");



            Log.w(TAG,"activity_id -->"+activity_id+" fromactivity : "+fromactivity);

            Log.w(TAG,"group_id -->"+group_id);

            Log.w(TAG,"service_id" + service_id);

        }
    }
}