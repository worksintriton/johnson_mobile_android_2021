package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;
import com.triton.johnsonapp.responsepojo.GetServiceListResponse;

import java.util.List;

import butterknife.BindView;


public class FieldListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "FieldListAdapter";
    private List<GetFieldListResponse.DataBean> dataBeanList;
    private Context context;

    GetFieldListResponse.DataBean currentItem;

    private int size;

    public FieldListAdapter(Context context, List<GetFieldListResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fieldlist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        if(currentItem.getField_name() != null && !currentItem.getField_name().equals("")){
            holder.txt_field_title.setText(currentItem.getField_name());
        }else{
            holder.txt_field_title.setText("");

        }
        if(currentItem.getField_comments() != null && !currentItem.getField_comments().equals("")){
            holder.txt_field_comments.setText(currentItem.getField_comments());
        }else{
            holder.txt_field_comments.setText("");

        }

        if(currentItem.getField_type()!=null&&!currentItem.getField_type().equals("")){

            if(currentItem.getField_type().equals("String")){

                holder.edt_string.setVisibility(View.VISIBLE);

            }
            else if(currentItem.getField_type().equals("Textarea")){

                holder.edt_textarea.setVisibility(View.VISIBLE);
            }

            else if(currentItem.getField_type().equals("Number")){

                holder.edt_number.setVisibility(View.VISIBLE);
            }

            else if(currentItem.getField_type().equals("Dropdown")){

                holder.ll_dropdown.setVisibility(View.VISIBLE);
            }

            else if(currentItem.getField_type().equals("Date&time")){

                holder.edt_datetime.setVisibility(View.VISIBLE);
            }

            else if(currentItem.getField_type().equals("File upload")){

                holder.ll_file_upload.setVisibility(View.VISIBLE);
            }

            else if(currentItem.getField_type().equals("Signature")){

                holder.lldigitalsignature.setVisibility(View.VISIBLE);

                holder.llheaderdigitalsignature.setVisibility(View.VISIBLE);

                holder.ivdigitalsignature.setVisibility(View.VISIBLE);
            }
        }

        if(currentItem.getField_length() != null && !currentItem.getField_length().equals("")){
            holder.txt_field_length.setText("Field Length " +currentItem.getField_length());
        }else{
            holder.txt_field_length.setText("");

        }
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
        public TextView txt_field_title,txt_field_comments,txt_field_length;
        public EditText edt_string,edt_textarea,edt_number,edt_datetime;
        public LinearLayout ll_dropdown,ll_file_upload,lldigitalsignature,llheaderdigitalsignature;
        public Spinner spr_dropdown;
        public ImageView img_spinner,img_file_upload,img_close,ivdigitalsignature;
        public Button mClearButton,mSaveButton;
        SignaturePad mSignaturePad;




        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_field_title = itemView.findViewById(R.id.txt_field_title);
            txt_field_comments = itemView.findViewById(R.id.txt_field_comments);
            edt_string = itemView.findViewById(R.id.edt_string);
            edt_textarea = itemView.findViewById(R.id.edt_textarea);
            edt_number = itemView.findViewById(R.id.edt_number);
            ll_dropdown = itemView.findViewById(R.id.ll_dropdown);
            spr_dropdown = itemView.findViewById(R.id.spr_dropdown);
            img_spinner = itemView.findViewById(R.id.img_spinner);
            edt_datetime = itemView.findViewById(R.id.edt_datetime);
            ll_file_upload = itemView.findViewById(R.id.ll_file_upload);
            img_file_upload = itemView.findViewById(R.id.img_file_upload);
            img_close = itemView.findViewById(R.id.img_close);
            ivdigitalsignature = itemView.findViewById(R.id.ivdigitalsignature);
            mClearButton = itemView.findViewById(R.id.clear_button);
            mSaveButton = itemView.findViewById(R.id.save_button);
            lldigitalsignature = itemView.findViewById(R.id.lldigitalsignature);
            llheaderdigitalsignature = itemView.findViewById(R.id.llheaderdigitalsignature);
            mSignaturePad = itemView.findViewById(R.id.signaturePad);
            ivdigitalsignature = itemView.findViewById(R.id.ivdigitalsignature);
            txt_field_length = itemView.findViewById(R.id.txt_field_length);

        }




    }




}
