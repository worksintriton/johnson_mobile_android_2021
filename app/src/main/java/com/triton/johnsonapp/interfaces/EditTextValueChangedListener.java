package com.triton.johnsonapp.interfaces;

import com.triton.johnsonapp.responsepojo.GetFieldListResponse;

import java.util.List;

public interface EditTextValueChangedListener {
    void editTextValueListener(int startItem, String s, String size, int position, List<GetFieldListResponse.DataBean.LiftListBean> list);
}
