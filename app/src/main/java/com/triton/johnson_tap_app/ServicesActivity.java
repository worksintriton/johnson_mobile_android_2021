package com.triton.johnson_tap_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ServicesActivity extends AppCompatActivity {

    ImageView iv_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_services);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        MyListData[] myListData = new MyListData[] {
                new MyListData("Breakdown Service"),
                new MyListData("LR Service"),
                new MyListData("Preventive Maintenance"),
                new MyListData("Parts Replacement ACK")
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(ServicesActivity.this,Main_Menu_ServicesActivity.class);
                startActivity(send);

            }
        });
    }
}