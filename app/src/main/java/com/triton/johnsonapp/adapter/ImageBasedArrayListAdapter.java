package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.model.RowDataFormModel;
import com.triton.johnsonapp.requestpojo.ImageBasedStroeDataRequest;

import java.util.ArrayList;
import java.util.List;


public class ImageBasedArrayListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "RowBasedArrayListAdapter";

    private Context context;

    List<ImageBasedStroeDataRequest.DataBean> Data;

    private int size;

    public ImageBasedArrayListAdapter(Context context, List<ImageBasedStroeDataRequest.DataBean> Data) {
        this.context = context;
        this.Data = Data;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_image_rowdatalist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {



        if(Data.get(position).getTitle() != null && !Data.get(position).getTitle().isEmpty()){
            holder.txt_title.setText(""+Data.get(position).getTitle());
        }
        if(Data.get(position).getValue_a() != null && !Data.get(position).getValue_a().isEmpty()){
            holder.txt_value1.setText(""+Data.get(position).getValue_a());
        }
        if(Data.get(position).getValue_b() != null && !Data.get(position).getValue_b().isEmpty()){
            holder.txt_value2.setText(""+Data.get(position).getValue_b());
        }








    }

    @Override
    public int getItemCount() {
        return Data.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView txt_title,txt_value1,txt_value2;

        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_value1 = itemView.findViewById(R.id.txt_value1);
            txt_value2 = itemView.findViewById(R.id.txt_value2);

        }




    }





}
