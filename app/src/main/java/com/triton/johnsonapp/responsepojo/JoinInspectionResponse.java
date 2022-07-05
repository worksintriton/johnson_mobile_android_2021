package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class JoinInspectionResponse {
    /** "Status": "Success",
     "Message": "Updated Success",

     "Code": 200
     */

    private String Status;
    private String Message;
    private int Code;

    private  DataBean Data;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;

    }


    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;

    }



    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;

    }

    public  DataBean getData() {
        return Data;
    }

    public void setData( DataBean data) {
        Data = data;
    }
    public static class DataBean {



    }
}
