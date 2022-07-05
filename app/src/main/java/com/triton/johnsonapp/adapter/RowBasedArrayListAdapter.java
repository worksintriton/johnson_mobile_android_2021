package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.GroupListActivity;
import com.triton.johnsonapp.model.RowDataFormModel;
import com.triton.johnsonapp.requestpojo.RowBasedStroeDataRequest;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;

import java.util.ArrayList;
import java.util.List;


public class RowBasedArrayListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "RowBasedArrayListAdapter";

    private Context context;

    ArrayList<RowDataFormModel> dataBeanLists;
    List<RowBasedStroeDataRequest.DataBean> Data;

    private int size;

    public RowBasedArrayListAdapter(Context context, ArrayList<RowDataFormModel> dataBeanList, List<RowBasedStroeDataRequest.DataBean> Data) {
        this.context = context;
        this.dataBeanLists = dataBeanList;
        this.Data = Data;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_rowdatalist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {


      /*  if(dataBeanLists.get(0) != null && !currentItem.getService_name().equals("")){
            holder.txt_serv_title.setText(currentItem.getService_name());
        }else{
            holder.txt_serv_title.setText("");

        }*/

        if(position==0){
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView11.setVisibility(View.VISIBLE);
            holder.txt_dimx1.setVisibility(View.VISIBLE);
            holder.txt_dimx2.setVisibility(View.VISIBLE);
            holder.txt_dimx3.setVisibility(View.VISIBLE);
            holder.txt_dimx4.setVisibility(View.VISIBLE);
            holder.txt_dimy1.setVisibility(View.VISIBLE);
            holder.txt_dimy2.setVisibility(View.VISIBLE);
            holder.txt_rem.setVisibility(View.VISIBLE);

        }
        else {
            holder.textView.setVisibility(View.GONE);
            holder.textView11.setVisibility(View.GONE);
            holder.txt_dimx1.setVisibility(View.GONE);
            holder.txt_dimx2.setVisibility(View.GONE);
            holder.txt_dimx3.setVisibility(View.GONE);
            holder.txt_dimx4.setVisibility(View.GONE);
            holder.txt_dimy1.setVisibility(View.GONE);
            holder.txt_dimy2.setVisibility(View.GONE);
            holder.txt_rem.setVisibility(View.GONE);
        }
        if(dataBeanLists.get(position).getSno()!= null && !dataBeanLists.get(position).getSno().equals("")){
            holder.edt_sno.setText(""+dataBeanLists.get(position).getSno());
        }else{
            holder.edt_sno.setText("");

        }


        if(dataBeanLists.get(position).getDimx1()!= null && !dataBeanLists.get(position).getDimx1().equals("")){
            holder.edt_dimx1.setText(""+dataBeanLists.get(position).getDimx1());
        }else{
            holder.edt_dimx1.setText("");

        }


        if(dataBeanLists.get(position).getDimx2()!= null && !dataBeanLists.get(position).getDimx2().equals("")){
            holder.edt_dimx2.setText(""+dataBeanLists.get(position).getDimx2());
        }else{
            holder.edt_dimx2.setText("");

        }

        if(dataBeanLists.get(position).getDimx3()!= null && !dataBeanLists.get(position).getDimx3().equals("")){
            holder.edt_dimx3.setText(""+dataBeanLists.get(position).getDimx3());
        }else{
            holder.edt_dimx3.setText("");

        }


        if(dataBeanLists.get(position).getDimx4()!= null && !dataBeanLists.get(position).getDimx4().equals("")){
            holder.edt_dimx4.setText(""+dataBeanLists.get(position).getDimx4());
        }else{
            holder.edt_dimx4.setText("");

        }


        if(dataBeanLists.get(position).getDimy1()!= null && !dataBeanLists.get(position).getDimy1().equals("")){
            holder.edt_dimy1.setText(""+dataBeanLists.get(position).getDimy1());
        }else{
            holder.edt_dimy1.setText("");

        }


        if(dataBeanLists.get(position).getDimy2()!= null && !dataBeanLists.get(position).getDimy2().equals("")){
            holder.edt_dimy2.setText(""+dataBeanLists.get(position).getDimy2());
        }else{
            holder.edt_dimy2.setText("");

        }


        if(dataBeanLists.get(position).getRem()!= null && !dataBeanLists.get(position).getRem().equals("")){
            holder.edt_rem.setText(""+dataBeanLists.get(position).getRem());
        }else{
            holder.edt_rem.setText("");

        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                Log.w(TAG,"rowdatalist" + new Gson().toJson(Data));
                dataBeanLists.remove(holder.getAdapterPosition());
                Data.remove(holder.getAdapterPosition());
                Log.w(TAG,"Adapterposition---"+ holder.getAdapterPosition());
                Log.w(TAG,"rowdatalist---1" + new Gson().toJson(Data));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBeanLists.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        EditText edt_sno, edt_dimx1,edt_dimx2, edt_dimx3,edt_dimx4,edt_dimy1,edt_dimy2,edt_rem;
        TextView textView,txt_dimx1,txt_dimx2,txt_dimx3,txt_dimx4,txt_dimy1,txt_dimy2,txt_rem,textView11;
        ImageView img_delete;

        public ViewHolderOne(View itemView) {
            super(itemView);
            edt_sno=itemView.findViewById(R.id.edt_sno);
            edt_dimx1=itemView.findViewById(R.id.edt_dimx1);
            edt_dimx2=itemView.findViewById(R.id.edt_dimx2);
            edt_dimx3=itemView.findViewById(R.id.edt_dimx3);
            edt_dimx4=itemView.findViewById(R.id.edt_dimx4);
            edt_dimy1=itemView.findViewById(R.id.edt_dimy1);
            edt_dimy2=itemView.findViewById(R.id.edt_dimy2);
            edt_rem=itemView.findViewById(R.id.edt_rem);
            textView=itemView.findViewById(R.id.textView);
            textView11=itemView.findViewById(R.id.textView11);
            txt_dimx1=itemView.findViewById(R.id.txt_dimx1);
            txt_dimx2=itemView.findViewById(R.id.txt_dimx2);
            txt_dimx3=itemView.findViewById(R.id.txt_dimx3);
            txt_dimx4=itemView.findViewById(R.id.txt_dimx4);
            txt_dimy1=itemView.findViewById(R.id.txt_dimy1);
            txt_dimy2=itemView.findViewById(R.id.txt_dimy2);
            txt_rem=itemView.findViewById(R.id.txt_rem);
            img_delete=itemView.findViewById(R.id.img_delete);
        }




    }




}
