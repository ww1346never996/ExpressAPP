package com.example.expressapp.config;

public class RecordList {
    private int code;
    private String sendId;
    private String exp_code;
    private String exp_time;
    private String get_time;
    private String get_status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExp_code() {
        return exp_code;
    }

    public void setExp_code(String exp_code) {
        this.exp_code = exp_code;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    public String getGet_time() {
        return get_time;
    }

    public void setGet_time(String get_time) {
        this.get_time = get_time;
    }

    public String getGet_status() {
        return get_status;
    }

    public void setGet_status(String get_status) {
        this.get_status = get_status;
    }
}
