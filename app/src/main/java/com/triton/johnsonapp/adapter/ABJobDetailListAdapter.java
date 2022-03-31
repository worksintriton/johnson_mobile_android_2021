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

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.CustomerDetailsActivity;
import com.triton.johnsonapp.activitybased.ABCustomerDetailsActivity;
import com.triton.johnsonapp.responsepojo.GetJobDetailByActivityResponse;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;

import java.util.List;


public class ABJobDetailListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ABJobDetailListAdapter";
    private List<GetJobDetailByActivityResponse.DataBean> dataBeanList;
    private Context context;

    GetJobDetailByActivityResponse.DataBean currentItem;

    private int size;
    String status;
    String fromactivity;
    private int form_type;
    private String activity_id;
    private String UKEY;
    private String UKEY_DESC;
    private int new_count;
    private int pause_count;

    public ABJobDetailListAdapter(Context context, List<GetJobDetailByActivityResponse.DataBean> dataBeanList, String status,
                                  String fromactivity,int form_type,String activity_id,String UKEY,
                                  int  new_count, int pause_count,String UKEY_DESC) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.fromactivity = fromactivity ;
        this.status = status ;
        this.form_type = form_type ;
        this.activity_id = activity_id ;
        this.UKEY = UKEY ;
        this.new_count = new_count ;
        this.pause_count = pause_count ;
        this.UKEY_DESC = UKEY_DESC ;


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

        if(dataBeanList.get(position).getJob_detail_no() != null && !dataBeanList.get(position).getJob_detail_no().equals("")){
            holder.txt_serv_title.setText(dataBeanList.get(position).getJob_detail_no());
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
            //Intent intent = new Intent(context, GroupListActivity.class);
            Intent intent = new Intent(context, ABCustomerDetailsActivity.class);
            intent.putExtra("activity_id",dataBeanList.get(position).getActivedetail__id());
            intent.putExtra("group_id",activity_id);
            intent.putExtra("job_id",dataBeanList.get(position).get_id());
            intent.putExtra("status",status);
            intent.putExtra("fromactivity",fromactivity);
            intent.putExtra("job_detail_no",dataBeanList.get(position).getJob_detail_no());
            intent.putExtra("form_type",form_type);
            intent.putExtra("UKEY",UKEY);
            intent.putExtra("UKEY_DESC",UKEY_DESC);
            intent.putExtra("new_count",new_count);
            intent.putExtra("pause_count",pause_count);

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
