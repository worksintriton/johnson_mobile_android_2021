package com.triton.johnsonapp.requestpojo;

import java.util.List;

public class GetFieldListRequest {

    private String group_id;
    private String sub_group_id;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getSub_group_id() {
        return sub_group_id;
    }

    public void setSub_group_id(String sub_group_id) {
        this.sub_group_id = sub_group_id;
    }
}
