package com.example.expressapp.utils;

import com.example.expressapp.config.RecordDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RecordDetailJson {
    public static String setDetailPost(String uid,String order_id) {
        String content = null;
        try {
            content = "uid=" + URLEncoder.encode(uid,"UTF-8") + "&order_id=" + URLEncoder.encode(order_id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static RecordDetail parseRecordDetailJson(String jsonstr) {
        RecordDetail recordDetail = new RecordDetail();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            int code = jsonObject.getInt("code");
            if (code == 0){
                recordDetail.setErr("success");
                JSONObject jsonBody = jsonObject.getJSONObject("body");
                recordDetail.setExp_code(jsonBody.getString("exp_code"));
                recordDetail.setAddress(jsonBody.getString("address"));
                recordDetail.setConsignee_phone(jsonBody.getString("consignee_phone"));
                recordDetail.setIn_time(jsonBody.getString("in_time"));
                recordDetail.setOut_time(jsonBody.getString("out_time"));
            }else {
                recordDetail.setErr("订单不存在");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recordDetail;
    }
}
