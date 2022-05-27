package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class ActivityPumpChartDropdown {
    /*{
        "Status": "Success",
            "Message": "Version",
            "Data": [
        {
            "name": "REAR SIDE CWT SLING",
                "img_url": "http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png"
        },
        {
            "name": "RH SIDE CWT WITH MRL BRACKET",
                "img_url": "http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png"
        },
        {
            "name": "LH SIDE CWT WITH MRL BRACKET",
                "img_url": "http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png"
        },
        {
            "name": "RH SIDE CWT WITH COMBINE BRACKET",
                "img_url": "http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png"
        },
        {
            "name": "LH SIDE CWT WITH COMBINE BRACKET",
                "img_url": "http://smart.johnsonliftsltd.com:3000/api/uploads/sign.png"
        }
    ],
        "Code": 200
    }*/
    private String Status;
    private String Message;
    private List<DataBean> Data;
    private int Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
    public static class DataBean
    {
        private String name;
        private String img_url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
