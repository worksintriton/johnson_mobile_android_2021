package com.triton.johnson_tap_app;

public class StatusCartonsRequest {

    private String smno;
    private String jobno;
    private String cusname;
    private String status;
    private String contno;
    private String purpose;
    private String sertype;
    private String frdt;
    private String todt;
    private String contamt;
    private String paidamt;
    private String balamt;
    private String stat;
    private String brcode;

    public StatusCartonsRequest(String smno, String jobno, String cusname, String status, String contno, String purpose, String sertype, String frdt, String todt, String contamt, String paidamt, String balamt, String stat, String brcode) {
        this.smno = smno;
        this.jobno = jobno;
        this.cusname = cusname;
        this.status = status;
        this.contno = contno;
        this.purpose = purpose;
        this.sertype = sertype;
        this.frdt = frdt;
        this.todt = todt;
        this.contamt = contamt;
        this.paidamt = paidamt;
        this.balamt = balamt;
        this.stat = stat;
        this.brcode = brcode;
    }

    public String getSmno() {
        return smno;
    }

    public void setSmno(String smno) {
        this.smno = smno;
    }

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContno() {
        return contno;
    }

    public void setContno(String contno) {
        this.contno = contno;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSertype() {
        return sertype;
    }

    public void setSertype(String sertype) {
        this.sertype = sertype;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public String getContamt() {
        return contamt;
    }

    public void setContamt(String contamt) {
        this.contamt = contamt;
    }

    public String getPaidamt() {
        return paidamt;
    }

    public void setPaidamt(String paidamt) {
        this.paidamt = paidamt;
    }

    public String getBalamt() {
        return balamt;
    }

    public void setBalamt(String balamt) {
        this.balamt = balamt;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getBrcode() {
        return brcode;
    }

    public void setBrcode(String brcode) {
        this.brcode = brcode;
    }




}