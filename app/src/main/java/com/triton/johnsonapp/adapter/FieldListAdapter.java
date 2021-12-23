package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.interfaces.GetDateTimeListener;
import com.triton.johnsonapp.interfaces.GetDigitalSignUploadAddListener;
import com.triton.johnsonapp.interfaces.GetDigitalSignUploadClearListener;
import com.triton.johnsonapp.interfaces.GetDigitalSignUploadListener;
import com.triton.johnsonapp.interfaces.GetFileUploadListener;
import com.triton.johnsonapp.interfaces.GetNumberListener;
import com.triton.johnsonapp.interfaces.GetSpinnerListener;
import com.triton.johnsonapp.interfaces.GetStringListener;
import com.triton.johnsonapp.interfaces.GetTextAreaListener;
import com.triton.johnsonapp.interfaces.GetInputFieldListener;
import com.triton.johnsonapp.responsepojo.GetFieldListResponse;

import java.util.List;


public class FieldListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "FieldListAdapter";
    private List<GetFieldListResponse.DataBean> dataBeanList;
    private Context context;

    GetFieldListResponse.DataBean currentItem;

    private int size;

    int ITEMS_PER_PAGE;

    int TOTAL_NUM_ITEMS;

    GetStringListener getStringListener;

    GetTextAreaListener getTextAreaListener;

    GetSpinnerListener getSpinnerListener;

    GetNumberListener getNumberListener;

    GetDateTimeListener getDateTimeListener;

    GetFileUploadListener getFileUploadListener;

    GetDigitalSignUploadListener getDigitalSignUploadListener;

    GetDigitalSignUploadAddListener getDigitalSignUploadAddListener;

    GetDigitalSignUploadClearListener getDigitalSignUploadClearListener;

    GetInputFieldListener getInputFieldListener;

    String digitalSignatureServerUrlImagePath;

    int currentPage;

    int check = 0;

    public FieldListAdapter(Context context, List<GetFieldListResponse.DataBean> dataBeanList, int ITEMS_PER_PAGE, int TOTAL_NUM_ITEMS, GetStringListener getStringListener, GetTextAreaListener getTextAreaListener, GetSpinnerListener getSpinnerListener, GetNumberListener getNumberListener, GetDateTimeListener getDateTimeListener,
                            GetFileUploadListener getFileUploadListener, GetDigitalSignUploadListener getDigitalSignUploadListener, GetDigitalSignUploadAddListener getDigitalSignUploadAddListener, GetDigitalSignUploadClearListener getDigitalSignUploadClearListener,  GetInputFieldListener getInputFieldListener,int currentPage) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.ITEMS_PER_PAGE = ITEMS_PER_PAGE;
        this.TOTAL_NUM_ITEMS = TOTAL_NUM_ITEMS;
        this.getStringListener = getStringListener;
        this.getTextAreaListener = getTextAreaListener;
        this.getSpinnerListener = getSpinnerListener;
        this.getNumberListener = getNumberListener;
        this.getDateTimeListener = getDateTimeListener;
        this.getFileUploadListener = getFileUploadListener;
        this.getDigitalSignUploadListener = getDigitalSignUploadListener;
        this.getDigitalSignUploadAddListener = getDigitalSignUploadAddListener;
        this.getDigitalSignUploadClearListener = getDigitalSignUploadClearListener;
        this.currentPage=currentPage;
        this.getInputFieldListener = getInputFieldListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fieldlist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    /*   int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
        int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;

        if(currentPage==LAST_PAGE){
            position = startItem+ITEMS_REMAINING;
        }
        else {
            position = startItem+ITEMS_PER_PAGE;
        }*/
   /*     int startItem=currentPage*ITEMS_PER_PAGE+position;

        Log.w(TAG,"currentItem startItem "+startItem);
*/
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        int startItem=currentPage*ITEMS_PER_PAGE+position;

        Log.w(TAG,"currentItem startItem "+startItem);

        Log.w(TAG,"currentItem POS "+position);

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

                if(currentItem.getField_value()!=null&&!currentItem.getField_value().equals("")){

                    holder.edt_string.setText(currentItem.getField_value());
                }

                holder.edt_string.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        getStringListener.getStringListener(holder.edt_textarea,s.toString(),startItem,currentItem.getField_length());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }


            else if(currentItem.getField_type().equals("Textarea")){

                holder.edt_textarea.setVisibility(View.VISIBLE);

                if(currentItem.getField_value()!=null&&!currentItem.getField_value().equals("")){

                    holder.edt_textarea.setText(currentItem.getField_value());
                }

                holder.edt_textarea.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        getTextAreaListener.getTextAreaListener(holder.edt_textarea,s.toString(),startItem,currentItem.getField_length());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }


            else if(currentItem.getField_type().equals("Number")){

                holder.edt_number.setVisibility(View.VISIBLE);

                if(currentItem.getField_value()!=null&&!currentItem.getField_value().equals("")){

                    holder.edt_number.setText(currentItem.getField_value());
                }
                else {

                    holder.edt_number.setText("");
                }


                holder.edt_number.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        getNumberListener.getNumberListener(holder.edt_number,s.toString(),startItem,currentItem.getField_length());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }


            else if(currentItem.getField_type().equals("Dropdown")){

                holder.ll_dropdown.setVisibility(View.VISIBLE);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.array_name, android.R.layout.simple_spinner_item);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                if(currentItem.getField_value()!=null&&!currentItem.getField_value().equals("")){

                    String compareValue = currentItem.getField_value();

                    holder.spr_dropdown.setAdapter(adapter);

                    if (compareValue != null) {
                        int spinnerPosition = adapter.getPosition(compareValue);
                        holder.spr_dropdown.setSelection(spinnerPosition);

                    }
                    holder.spr_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                            if(++check > 1) {

                                Log.w(TAG,"currentItem POS "+startItem);

                                Log.w(TAG,"currentItem POS "+parent.getItemAtPosition(pos));

                                getSpinnerListener.getSpinnerListener(holder.spr_dropdown,startItem,parent.getItemAtPosition(pos).toString(),currentItem.getField_length());

                            }

                        }
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                    });

                }
                else {


                    holder.spr_dropdown.setAdapter(adapter);

                    holder.spr_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            if(++check > 1) {

                                Log.w(TAG,"currentItem POS "+startItem);

                                Log.w(TAG,"currentItem POS "+parent.getItemAtPosition(pos));

                                getSpinnerListener.getSpinnerListener(holder.spr_dropdown,startItem,parent.getItemAtPosition(pos).toString(),currentItem.getField_length());

                            }
                        }
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                    });


                }


            }


            else if(currentItem.getField_type().equals("Date&time")){

                holder.edt_datetime.setVisibility(View.VISIBLE);

                if(currentItem.getField_value()!=null&&!currentItem.getField_value().equals("")) {

                    holder.edt_datetime.setText(currentItem.getField_value());


                }

                    holder.edt_datetime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDateTimeListener.getDateTimeListener(holder.edt_datetime,startItem,currentItem.getField_length());
                    }
                });


            }


            else if(currentItem.getField_type().equals("File upload")){

                holder.ll_file_upload.setVisibility(View.VISIBLE);


                if(currentItem.getField_value()!=null&&!currentItem.getField_value().equals("")){

                    String uploadimagepath = currentItem.getField_value();

                    holder.cv_image.setVisibility(View.VISIBLE);

                    holder.img_file_upload.setVisibility(View.VISIBLE);


                    if (uploadimagepath!=null) {
                        Glide.with(context)
                                .load(uploadimagepath)
                                .into(holder.img_file_upload);

                    }

                }


                holder.img_close.setVisibility(View.VISIBLE);

                holder.img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      //  dataBeanList.get(position).setField_update_reason("");

                        notifyDataSetChanged();

                        holder.img_file_upload.setVisibility(View.GONE);
                    }
                });

                holder.ll_file_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getFileUploadListener.getFileUploadListener(holder.ll_file_upload,startItem,holder.img_file_upload, holder.img_close,currentItem.getField_length(),holder.cv_image);


                    }
                });

            }


            else if(currentItem.getField_type().equals("Signature")){

                holder.lldigitalsignature.setVisibility(View.VISIBLE);

              /*  holder.llheaderdigitalsignature.setVisibility(View.VISIBLE);

                holder.ivdigitalsignature.setVisibility(View.VISIBLE);*/

                if(currentItem.getField_value()!=null&&!currentItem.getField_value().equals("")){

                    //holder.llheaderdigitalsignature.setVisibility(View.VISIBLE);

                    holder.ivdigitalsignature.setVisibility(View.VISIBLE);

                    digitalSignatureServerUrlImagePath = currentItem.getField_value();

                    Log.w(TAG, "digitalSignatureServerUrlImagePath " + digitalSignatureServerUrlImagePath);

                    holder.ivdigitalsignature.setVisibility(View.VISIBLE);
                    if (digitalSignatureServerUrlImagePath != null && !digitalSignatureServerUrlImagePath.isEmpty()) {
                       // dataBeanList.get(position).setField_value("");

                        Log.w(TAG,"digitalSignatureServerUrlImagePath--->"+digitalSignatureServerUrlImagePath);

                        Glide
                                .with(context)
                                .load(digitalSignatureServerUrlImagePath)
                                .apply(new RequestOptions().override(600, 200))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.ivdigitalsignature);



                    }
                    else{
                        Glide.with(context)
                                .load(R.drawable.digital_signature)
                                .into(holder.ivdigitalsignature);

                    }




                }

                holder.lldigitalsignature.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.w(TAG,"currentItem POS DS"+startItem);

                        getDigitalSignUploadListener.getDigitalSignUploadListener(holder.llheaderdigitalsignature,holder.ivdigitalsignature,holder.mSignaturePad,holder.mSaveButton,holder.mClearButton,startItem,currentItem.getField_length());


                    }
                });


                holder.mSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.w(TAG,"currentItem POS DS"+startItem);


                        getDigitalSignUploadAddListener.getDigitalSignUploadAddListener(holder.llheaderdigitalsignature,holder.ivdigitalsignature,holder.mSignaturePad,holder.mSaveButton,holder.mClearButton,startItem,currentItem.getField_length());


                    }
                });

                holder.mClearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.w(TAG,"currentItem POS DS"+startItem);

                        getDigitalSignUploadClearListener.getDigitalSignUploadClearListener(holder.llheaderdigitalsignature,holder.ivdigitalsignature,holder.mSignaturePad,holder.mSaveButton,holder.mClearButton,startItem,currentItem.getField_length());


                    }
                });
            }

            else if(currentItem.getField_type().equals("Liftno")){

                if(currentItem.getField_length() != null && !currentItem.getField_length().equals("")){

                    Log.w(TAG,"currentItem POS Lift"+startItem);

                    int size = Integer.parseInt(currentItem.getField_length());

                    holder.rv_liftinputlist.setVisibility(View.VISIBLE);

                    getInputFieldListener.getInputFieldListener(holder.rv_liftinputlist,startItem,currentItem.getField_length());
                }
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
        return Math.min(dataBeanList.size(), ITEMS_PER_PAGE);
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
        RecyclerView rv_liftinputlist;
        CardView cv_image;



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
            cv_image = itemView.findViewById(R.id.cv_image);
            rv_liftinputlist = itemView.findViewById(R.id.rv_liftinputlist);
        }




    }




}
