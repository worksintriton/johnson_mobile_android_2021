package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.activity.GroupListActivity;
import com.triton.johnsonapp.model.RowDataFormModel;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;

import java.util.ArrayList;
import java.util.List;


public class RowBasedArrayListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "RowBasedArrayListAdapter";

    private Context context;

    ArrayList<RowDataFormModel> dataBeanLists;

    private int size;

    public RowBasedArrayListAdapter(Context context, ArrayList<RowDataFormModel> dataBeanList) {
        this.context = context;
        this.dataBeanLists = dataBeanList;


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



        }




    }




}
