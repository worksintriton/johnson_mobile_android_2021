package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.ActivityStatusActivity;
import com.triton.johnsonapp.responsepojo.ActivityListManagementResponse;

import java.util.List;


public class ActivityBasedListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ActivityBasedListAdapter";
    private List<ActivityListManagementResponse.DataBean> dataBeanList;
    private Context context;

    ActivityListManagementResponse.DataBean currentItem;

    private int size;

    public ActivityBasedListAdapter(Context context, List<ActivityListManagementResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_staticdatalist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        if(dataBeanList.get(position).getActivedetail_name()!= null && !dataBeanList.get(position).getActivedetail_name().equals("")){
            holder.txt_serv_title.setText(dataBeanList.get(position).getActivedetail_name());
        }else{
            holder.txt_serv_title.setText("");

        }

      /*  if(currentItem.getService_update_at() != null && !currentItem.getService_update_at().equals("")){
            holder.txt_serv_updatedat.setText("Updated at :"+ currentItem.getService_update_at());
        }else{
            holder.txt_serv_updatedat.setText("Updated at : ");

        }

        if(currentItem.getService_created_at() != null && !currentItem.getService_created_at().equals("")){
            holder.txt_serv_createdat.setText("Created at : "+currentItem.getService_created_at());
        }else{
            holder.txt_serv_createdat.setText("Created at : ");

        }*/



        holder.cv_root.setOnClickListener(v -> {

            Intent intent = new Intent(context, ActivityStatusActivity.class);

            intent.putExtra("activity_id",dataBeanList.get(position).get_id());

            Log.w(TAG,"id -->"+dataBeanList.get(position).get_id());

            context.startActivity(intent);



        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView txt_serv_title,txt_serv_updatedat,txt_serv_createdat;
        ImageView img_next;
        CardView cv_root;

        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_serv_title = itemView.findViewById(R.id.txt_serv_title);
            txt_serv_updatedat = itemView.findViewById(R.id.txt_serv_updatedat);
            txt_serv_createdat = itemView.findViewById(R.id.txt_serv_createdat);
            img_next = itemView.findViewById(R.id.img_next);
            cv_root = itemView.findViewById(R.id.cv_root);


        }




    }




}
