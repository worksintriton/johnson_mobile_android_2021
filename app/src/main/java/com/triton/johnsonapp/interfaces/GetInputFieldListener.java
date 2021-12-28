package com.triton.johnsonapp.interfaces;

import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.responsepojo.GetFieldListResponse;

import java.util.List;

public interface GetInputFieldListener {
    void getInputFieldListener(RecyclerView rv_liftinputlist, int startItem, int field_length, List<GetFieldListResponse.DataBean.LiftListBean> lift_list);
}
