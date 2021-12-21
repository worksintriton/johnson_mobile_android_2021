package com.triton.johnsonapp.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.triton.johnsonapp.R;

public class ImageBasedInputFormActivity extends AppCompatActivity {

    private String TAG ="ImageBasedInputFormActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_based_input_form);
        Log.w(TAG,"Oncreate -->");
    }
}