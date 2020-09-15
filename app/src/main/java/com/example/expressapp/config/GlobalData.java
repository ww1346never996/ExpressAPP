package com.example.expressapp.config;

public class GlobalData {
    public static String sid;
    public static String uid;
    public static String cabNum;
    public static final String basic_path = "http://101.200.89.170:";

    public static String getCabNum() {
        return cabNum;
    }

    public static void setCabNum(String cabNum) {
        GlobalData.cabNum = cabNum;
    }

    public static String getSid() {
        return sid;
    }

    public static void setSid(String sid) {
        GlobalData.sid = sid;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        GlobalData.uid = uid;
    }
}
