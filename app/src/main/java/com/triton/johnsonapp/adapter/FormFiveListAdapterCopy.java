package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.interfaces.GetAcceptQtyListner;
import com.triton.johnsonapp.interfaces.GetDamageQtyListner;
import com.triton.johnsonapp.interfaces.GetExcessListner;
import com.triton.johnsonapp.interfaces.GetRemarksListner;
import com.triton.johnsonapp.interfaces.GetShortListner;
import com.triton.johnsonapp.responsepojo.FormFiveDataResponse;

import java.util.List;


public class FormFiveListAdapterCopy extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "FormFiveListAdapter";
    private List<FormFiveDataResponse.DataBean.MaterialDetailsBean> dataBeanList;
    private Context context;

    FormFiveDataResponse.DataBean.MaterialDetailsBean currentItem;

    private int size;
    String activity_id;
    String job_id;
    GetAcceptQtyListner getAcceptQtyListener;
    GetDamageQtyListner getDamageQtyListner;
    GetShortListner getShortListner;
    GetExcessListner getExcessListner;
    GetRemarksListner getRemarksListner;

    public FormFiveListAdapterCopy(Context context, List<FormFiveDataResponse.DataBean.MaterialDetailsBean> dataBeanList,
                                   String activity_id, String job_id,
                                   GetAcceptQtyListner getAcceptQtyListener,
                                   GetDamageQtyListner getDamageQtyListner,
                                   GetShortListner getShortListner,
                                   GetExcessListner getExcessListner,
                                   GetRemarksListner getRemarksListner) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.activity_id = activity_id;
        this.job_id = job_id;
        this.getAcceptQtyListener = getAcceptQtyListener;
        this.getDamageQtyListner = getDamageQtyListner;
        this.getShortListner = getShortListner;
        this.getExcessListner = getExcessListner;
        this.getRemarksListner = getRemarksListner;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_from_five, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        if(position == dataBeanList.size()-1){
            holder.ll_remarks.setVisibility(View.VISIBLE);
        }


        currentItem = dataBeanList.get(position);
      //  holder.txt_sno.setText( holder.getAdapterPosition()+1+"");

        if(dataBeanList.get(position).getPart_no() != null && !dataBeanList.get(position).getPart_no().isEmpty()){
            holder.txt_part_no.setText(" : "+dataBeanList.get(position).getPart_no());
        }else{
            holder.txt_part_no.setText("");

        }
        if(dataBeanList.get(position).getMaterial_desc() != null && !dataBeanList.get(position).getMaterial_desc().isEmpty()){
            holder.txt_material_desc.setText(" : "+dataBeanList.get(position).getMaterial_desc());
        }else{
            holder.txt_material_desc.setText("");

        }
        if(dataBeanList.get(position).getDesc_qty() != 0){
            holder.txt_desp_qty.setText(" : "+dataBeanList.get(position).getDesc_qty()+" ");
        }else{
            holder.txt_desp_qty.setText("");

        }
        if(dataBeanList.get(position).getAccepts() != 0){
            holder.edt_accept_qty.setText(dataBeanList.get(position).getAccepts()+" ");
        }else{
            holder.edt_accept_qty.setText("");

        }

        holder.edt_damageqty.setText(dataBeanList.get(position).getDemage()+" ");


        holder.edt_accept_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.w(TAG," s---> : "+s.toString());
                if(s.toString().isEmpty()){
                    holder.edt_short.setText(""+0);
                }
                double descqty = dataBeanList.get(position).getDesc_qty();
                double acceptqty = 0;


                try {
                    acceptqty = Integer.parseInt(s.toString().trim());
                    Log.w(TAG," descqty : "+descqty+" acceptqty : "+acceptqty);
                    if(descqty<acceptqty){
                        double qty = descqty - acceptqty;
                        double x = Math.abs(qty);

                        Log.w(TAG," qty if: "+x);
                        //holder.edt_excess.setText(""+x);
                      //  holder.edt_short.setText("0");
                        holder.edt_short.setText(""+x);

                    }else {
                        double qty = descqty - acceptqty;
                        double x = Math.abs(qty);
                        Log.w(TAG," qty else : "+x);
                        holder.edt_short.setText("-"+qty);
                      //  holder.edt_excess.setText("0");

                    }
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }






                getAcceptQtyListener.getAcceptQtyListner(holder.edt_accept_qty,s.toString(),position);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edt_damageqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getDamageQtyListner.getDamageQtyListner(holder.edt_damageqty,s.toString(),position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edt_short.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getShortListner.getShortListner(holder.edt_short,s.toString(),position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edt_excess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getExcessListner.getExcessListner(holder.edt_excess,s.toString(),position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edt_textarea_remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getRemarksListner.getRemarksListner(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        public TextView txt_part_no,txt_material_desc,txt_desp_qty;
        public EditText edt_accept_qty,edt_damageqty,edt_short,edt_excess,edt_textarea_remarks;

        CardView cv_root;
        public LinearLayout ll_remarks;

        public ViewHolderOne(View itemView) {
            super(itemView);
            //txt_sno = itemView.findViewById(R.id.txt_sno);
            txt_part_no = itemView.findViewById(R.id.txt_part_no);
            txt_material_desc = itemView.findViewById(R.id.txt_material_desc);
            txt_desp_qty = itemView.findViewById(R.id.txt_desp_qty);
            edt_accept_qty = itemView.findViewById(R.id.edt_accept_qty);
            edt_damageqty = itemView.findViewById(R.id.edt_damageqty);
            edt_short = itemView.findViewById(R.id.edt_short);
            edt_excess = itemView.findViewById(R.id.edt_excess);
            cv_root = itemView.findViewById(R.id.cv_root);
            ll_remarks = itemView.findViewById(R.id.ll_remarks);
            ll_remarks.setVisibility(View.GONE);
            edt_textarea_remarks = itemView.findViewById(R.id.edt_textarea_remarks);





        }




    }




}
