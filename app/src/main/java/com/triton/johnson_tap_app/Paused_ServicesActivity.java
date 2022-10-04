package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.triton.johnsonapp.R;

public class Paused_ServicesActivity extends AppCompatActivity {

    ImageView iv_back;
    String value="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("value");

        }


        setContentView(R.layout.activity_paused_services);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        MyListData[] myListData = new MyListData[] {
                new MyListData("L-B5759"),
                new MyListData("L-A2120"),
                new MyListData("L-C1605"),
                new MyListData("L-K8462")
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter2 adapter = new MyListAdapter2(myListData,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(value.equals("pasused")){

                    Intent send = new Intent(Paused_ServicesActivity.this, Preventive_ServiceActivity.class);
                    startActivity(send);
                }
                else {
                    Intent send = new Intent(Paused_ServicesActivity.this, Customer_DetailsActivity.class);
                    startActivity(send);
                }

            }
        });
    }
}