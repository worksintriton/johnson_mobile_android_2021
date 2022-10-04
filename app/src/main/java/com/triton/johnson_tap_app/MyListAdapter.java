package com.triton.johnson_tap_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

    private MyListData[] listdata;
    private Context context;

    // RecyclerView recyclerView;
    public MyListAdapter(MyListData[] listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.services_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListData myListData = listdata[position];
        holder.textView.setText(listdata[position].getDescription());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(context, Preventive_ServiceActivity.class);
                context.startActivity(send);
            }
        });

        holder.lin_services_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(context, Preventive_ServiceActivity.class);
                context.startActivity(send);
            }
        });

        holder.cv_services_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(context, Preventive_ServiceActivity.class);
                context.startActivity(send);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        LinearLayout lin_services_item;
        CardView cv_services_item;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text);
            this.lin_services_item = (LinearLayout) itemView.findViewById(R.id.lin_services_item);
            this.cv_services_item = (CardView) itemView.findViewById(R.id.cv_services_item);

        }
    }
}
