package com.example.expressapp.config;

public class DeliveryExpDetailInfo {
    private int code;
    private String error;
    private String exp_code;
    private String in_time;
    private int status;
    private String status_desc;//状态描述同投递记录的status
    private String out_time;
    private String consignee_phone;
    private String end_time;
    private String expire_time;
    private String address;
    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getExpire_time() {
        return expire_time;
    }
    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }
    public String getConsignee_phone() {
        return consignee_phone;
    }
    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStatus_desc() {
        return status_desc;
    }
    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
    public String getExp_code() {
        return exp_code;
    }
    public void setExp_code(String exp_code) {
        this.exp_code = exp_code;
    }
    public String getIn_time() {
        return in_time;
    }
    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getOut_time() {
        return out_time;
    }
    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
