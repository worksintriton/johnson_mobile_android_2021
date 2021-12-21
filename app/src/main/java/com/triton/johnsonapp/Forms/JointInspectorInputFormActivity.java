package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.triton.johnsonapp.R;

public class JointInspectorInputFormActivity extends AppCompatActivity {

    private String TAG ="JointInspectorInputFormActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joint_inspector_input_form);
        Log.w(TAG,"Oncreate -->");
    }
}