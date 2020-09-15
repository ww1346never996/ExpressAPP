package com.example.expressapp.utils;

import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.LoginInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class JsonUtils {
    /*
    下面是工具类，用于设置Post请求内容及解析返回json
     */
    public static String setLoginPost(String name,String password) {
        String content = null;
        try {
            content = "phone="+ URLEncoder.encode(name,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return content;
    }
    public static String setSignUpCodePost(String phone) {
        String content = null;
        try {
            content = "phone="+URLEncoder.encode(phone,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static String setOpenPost(String uid,String order_id){
        String content = null;
        try {
            content = "uid=" + URLEncoder.encode(uid,"UTF-8") + "&order_id=" + URLEncoder.encode(order_id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static String setSignUpPost(String phone,String password,String pcode) {
        String content = null;
        try {
            content = "phone=" + URLEncoder.encode(phone,"UTF-8") + "&password=" + URLEncoder.encode(password,"UTF-8") + "&pcode=" + URLEncoder.encode(pcode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static String parseSignUp(String jsonstr) {
        String status = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            int code = 1;
            code = jsonObject.getInt("code");
            switch (code){
                case 0:
                    status = "yes";
                    break;
                case 20010:
                    status = "signed";
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }
    public static String parseVerifyCode(String jsonstr) {
        String verifyCode = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            int code = 0;
            code = jsonObject.getInt("code");
            switch (code) {
                case 0:
                    JSONObject jsonBody = jsonObject.getJSONObject("body");
                    if (jsonBody != null){
                        verifyCode = jsonBody.getString("pcode");
                    }
                    break;
                case 20020:
                    return "error";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return verifyCode;
    }
    public LoginInfo parseLoginInfo(String jsonStr) {
        LoginInfo loginInfo = new LoginInfo();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            loginInfo.setCode(jsonObject.getInt("code"));
            switch (loginInfo.getCode()){
                case 0 :
                    JSONObject jsonBody = jsonObject.getJSONObject("body");
                    if (jsonBody != null){
                        loginInfo.setId(jsonBody.getString("id"));
                        loginInfo.setSid(jsonBody.getJSONObject("session").getString("sid"));
                        GlobalData.setSid(jsonBody.getJSONObject("session").getString("sid"));
                        GlobalData.setUid(jsonBody.getString("id"));
                    }
                    break;
                case 20000 :
                    loginInfo.setError("错误码：20000，登录失败次数过多，登录被锁定");
                    break;
                case 20001 :
                    loginInfo.setError("错误码：20001，用户不存在");
                    break;
                case 20009 :
                    loginInfo.setError("错误码：20009，密码错误");
                    break;
                case 20016 :
                    loginInfo.setError("错误码20016，账户被禁用");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loginInfo;
    }
    public static String parseOpenJson(String jsonstr){
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            int code = jsonObject.getInt("code");
            switch (code){
                case 0:
                    return "成功";
                case 20001:
                    return "用户不存在";
                case 10001:
                    return "柜体不存在";
                case 10002:
                    return "格口无效";
                case 15101:
                    return "订单不存在";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "网络错误";
    }
    public static String parseOpenCheckJson(String jsonstr){
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            int code = jsonObject.getInt("code");
            if (code == 0){
                JSONObject jsonBody = jsonObject.getJSONObject("body");
                if (jsonBody.getBoolean("is_retrieve")){
                    return "true";
                }else {
                    return "false";
                }
            }else {
                return "订单不存在";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "网络错误";
    }
}
