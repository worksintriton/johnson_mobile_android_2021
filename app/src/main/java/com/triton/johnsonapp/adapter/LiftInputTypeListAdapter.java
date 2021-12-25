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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.interfaces.EditTextValueChangedListener;
import com.triton.johnsonapp.model.EditModel;

import java.util.ArrayList;


public class LiftInputTypeListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "LiftInputTypeListAdapter";

    private Context context;

    private int size;int startItem;

    EditTextValueChangedListener editTextValueChangedListener;

    public static ArrayList<EditModel> list;

    public LiftInputTypeListAdapter(Context context, int size, int startItem, EditTextValueChangedListener editTextValueChangedListene, ArrayList<EditModel> list) {
        this.context = context;
        this.size = size;
        this.startItem=startItem;
        this.editTextValueChangedListener=editTextValueChangedListene;
        this.list=list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_liftinputlist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {


        Log.w(TAG,"id -->"+size);

        if(list.get(position).getEditTextValue()!=null&&!list.get(position).getEditTextValue().equals("")){

            holder.edt_inputValue.setText(list.get(position).getEditTextValue());

        }


    }

    @Override
    public int getItemCount() {
        return size;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {

        public EditText edt_inputValue;

        CardView cv_root;

        public ViewHolderOne(View itemView) {
            super(itemView);

            edt_inputValue = itemView.findViewById(R.id.edt_inputValue);

            cv_root = itemView.findViewById(R.id.cv_root);

            edt_inputValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(getAdapterPosition()).setEditTextValue(edt_inputValue.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });



        }




    }




}
