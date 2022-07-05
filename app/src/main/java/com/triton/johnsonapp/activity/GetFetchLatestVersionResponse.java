package com.triton.johnsonapp.activity;

import java.util.List;

public class GetFetchLatestVersionResponse {
    /*"Status": "Success",
    "Message": "Version",
    "Data": {
        "version": "23.10.2022.1",
        "apk_link": ""http://smart.johnsonliftsltd.com:3000/api/uploads/app-debug.apk"",
    },
    "Code": 200*/
    private String Status;
    private String Message;
    private DataBean Data;
    private String Code;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
    public static class DataBean
    {
        private String version;
        private String apk_link;


        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getApk_link() {
            return apk_link;
        }

        public void setApk_link(String apk_link) {
            this.apk_link = apk_link;
        }
    }
}
