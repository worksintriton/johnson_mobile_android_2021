package com.triton.johnson_tap_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.triton.johnsonapp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Agent_ProfileActivity extends AppCompatActivity {

    ImageView iv_back;
    Button submit;
    TextView agent_code, agent_name, agent_status;
    String s_agent_code, s_agent_name, s_agent_status;
    AlertDialog alertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agent_profile);

        iv_back = (ImageView) findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Agent_ProfileActivity.this, Main_Menu_ServicesActivity.class);
                startActivity(send);

            }
        });
    }
}