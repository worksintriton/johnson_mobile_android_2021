package com.triton.johnsonapp.responsepojo;

public class JoinInspectionCheckStatusResponse {
    /** "Status": "Success",
     "Message": "Updated Success",
     "Data": {},
     "Code": 200
     */

    private String Status;
    private String Message;
    private int Code;
    private JoinInspectionResponse.DataBean Data;


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


    public JoinInspectionResponse.DataBean getData() {
        return Data;
    }

    public void setData(JoinInspectionResponse.DataBean Data) {
        this.Data = Data;

    }

    public static class DataBean {

        private String job_no;
        private String ukey;
        private String user_number;


        public String getJob_no() {
            return job_no;
        }

        public void setJob_no(String job_no) {
            this.job_no = job_no;

        }
        public String getUkey() {
            return ukey;
        }

        public void setUkey(String ukey) {
            this.ukey = ukey;

        }
        public String getUser_number() {
            return user_number;
        }

        public void setUser_number(String user_number) {
            this.user_number = user_number;

        }
    }
}


