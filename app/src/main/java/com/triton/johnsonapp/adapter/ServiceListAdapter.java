package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.Forms.ImageBasedInputFormActivity;
import com.triton.johnsonapp.Forms.JointInspectorInputFormActivity;
import com.triton.johnsonapp.Forms.RowBasedInputFormActivity;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.Forms.InputValueFormListActivity;
import com.triton.johnsonapp.responsepojo.SubGroupDetailManagementResponse;

import java.util.List;


public class ServiceListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ServiceListAdapter";
    private List<SubGroupDetailManagementResponse.DataBean> dataBeanList;
    private Context context;

    SubGroupDetailManagementResponse.DataBean currentItem;

    private int size;

    String activity_id;
    String job_id;
    String group_id;
    String fromactivity;
    public ServiceListAdapter(Context context, List<SubGroupDetailManagementResponse.DataBean> dataBeanList, String activity_id, String job_id, String group_id,  String fromactivity) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.activity_id=activity_id;
        this.job_id=job_id;
        this.group_id=group_id;
        this.fromactivity=fromactivity;

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

        if(dataBeanList.get(position).getSub_group_detail_name() != null && !dataBeanList.get(position).getSub_group_detail_name().equals("")){
            holder.txt_serv_title.setText(dataBeanList.get(position).getSub_group_detail_name());
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

            if(dataBeanList.get(position).getForm_type()!=null){

                if(dataBeanList.get(position).getForm_type().equals("1")){
                    Intent intent = new Intent(context, InputValueFormListActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("job_id",job_id);
                    intent.putExtra("group_id",group_id);
                    intent.putExtra("subgroup_id",dataBeanList.get(position).get_id());
                    intent.putExtra("fromactivity",fromactivity);
                    context.startActivity(intent);
                }

                else if(dataBeanList.get(position).getForm_type().equals("2")){

                    Intent intent = new Intent(context, ImageBasedInputFormActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("job_id",job_id);
                    intent.putExtra("group_id",group_id);
                    intent.putExtra("subgroup_id",dataBeanList.get(position).get_id());
                    context.startActivity(intent);
                }
                else if(dataBeanList.get(position).getForm_type().equals("3")){

                    Intent intent = new Intent(context, RowBasedInputFormActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("job_id",job_id);
                    intent.putExtra("group_id",group_id);
                    intent.putExtra("subgroup_id",dataBeanList.get(position).get_id());
                    context.startActivity(intent);
                }

                else if(dataBeanList.get(position).getForm_type().equals("4")){

                    Intent intent = new Intent(context, JointInspectorInputFormActivity.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("job_id",job_id);
                    intent.putExtra("group_id",group_id);
                    intent.putExtra("subgroup_id",dataBeanList.get(position).get_id());
                    context.startActivity(intent);
                }
            }


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
