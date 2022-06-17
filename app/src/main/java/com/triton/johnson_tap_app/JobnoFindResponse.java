package com.triton.johnson_tap_app;

import java.util.List;

public class JobnoFindResponse {

    private String Status;
    private String Message;
   // private DataBean Data;

    private List<DataBean> Data;

    private int Code;

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

//    public DataBean getData() {
//        return Data;
//    }
//
//    public void setData(DataBean Data) {
//        this.Data = Data;
//
//    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;

    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;

    }

    public static class DataBean {
        /**
         * email_id : mohammedimthi2395@gmail.com
         * otp : 912364
         */

        private String JOBNO;
        private String CUSNAME;
        private String CONTNO;

        public String getJOBNO() {
            return JOBNO;
        }

        public void setJOBNO(String JOBNO) {
            this.JOBNO = JOBNO;

        }

        public String getCUSNAME() {
            return CUSNAME;
        }

        public void setCUSNAME(String CUSNAME) {
            this.CUSNAME = CUSNAME;

        }

        public String getCONTNO() {
            return CONTNO;
        }

        public void setCONTNO(String CONTNO) {
            this.CONTNO = CONTNO;

        }

    }
}
