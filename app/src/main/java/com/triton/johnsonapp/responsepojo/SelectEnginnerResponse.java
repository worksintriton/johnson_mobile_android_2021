package com.triton.johnsonapp.responsepojo;

import java.util.List;

public class SelectEnginnerResponse {
    /*"Status": "Success",
       "Message": "Updated",
       "Code": 200*/

    private String Status;
    private String Message;
    private int Code;

    private  DataBean Data;

    public SelectEnginnerResponse() {
    }

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

    public void setData( DataBean Data) {
        this.Data = Data;
    }



    /* {   "EMPNO": "E9814",
           "EMPNAME": "L MECNROW",
           "MOBILE": "7338725111"
       },
       {
           "EMPNO": "E0107",
           "EMPNAME": "B SIVAKUMAR",
           "MOBILE": "7358386462"
       },
       {
           "EMPNO": "E0273 ",
           "EMPNAME": "S SUNIL SAMRAJ",
           "MOBILE": "8136802912"
       }

}*/


    public static class DataBean {
        private String EMPNO;
        private String EMPNAME;
        private String MOBILE;

        public String getEMPNO() {
            return EMPNO;
        }

        public void setEMPNO(String EMPNO) {
            this.EMPNO = EMPNO;
        }

        public String getEMPNAME() {
            return EMPNAME;
        }

        public void setEMPNAME(String EMPNAME) {
            this.EMPNAME = EMPNAME;
        }

        public String getMOBILE() {
            return MOBILE;
        }

        public void setMOBILE(String MOBILE) {
            this.MOBILE = MOBILE;
        }
    }
}

