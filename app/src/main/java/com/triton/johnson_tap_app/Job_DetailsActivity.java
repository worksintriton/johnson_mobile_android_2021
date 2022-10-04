package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.triton.johnsonapp.R;

public class Job_DetailsActivity extends AppCompatActivity {

    ImageView iv_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_job_details);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        MyListData1[] myListData = new MyListData1[] {
                new MyListData1("L9910","SAMUVEL NADAR JOHN ASHIRVATHAM"),
                new MyListData1("L-C1607","JAINS SUNDERBANS FLAT OWNERS ASSN")
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter1 adapter = new MyListAdapter1(myListData,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Job_DetailsActivity.this,Preventive_ServiceActivity.class);
                startActivity(send);

            }
        });

    }
}