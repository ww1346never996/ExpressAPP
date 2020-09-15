package com.example.expressapp.config;

public class GetPost {

    private int code;
    private int count;
    private String id;
    private String addr;
    private String in_time;
    private String box_size;

    public String getBox_size() {
        return box_size;
    }

    public void setBox_size(String box_size) {
        this.box_size = box_size;
    }

    public String getStatus() {
        return status;
    }

    private String status = "2";

    public int getCode() {
        return code;
    }

    public int getCount() {
        return count;
    }

    public String getId() {
        return id;
    }

    public String getAddr() {
        return addr;
    }

    public String getIn_time() {
        return in_time;
    }

    public String getStatus(String status) {
        return status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
