package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.triton.johnsonapp.R;

public class ImageBasedInputFormActivity extends AppCompatActivity {

    private String TAG ="ImageBasedInputFormActivity";

    String string_value,message,service_id,activity_id,job_id,group_id,subgroup_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_based_input_form);
        Log.w(TAG,"Oncreate -->");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            service_id = extras.getString("service_id");

            group_id = extras.getString("group_id");

            activity_id = extras.getString("activity_id");

            job_id = extras.getString("job_id");

            subgroup_id= extras.getString("subgroup_id");

            Log.w(TAG,"activity_id -->"+activity_id);

            Log.w(TAG,"group_id -->"+group_id);

            Log.w(TAG,"service_id" + service_id);

        }
    }
}