package com.example.expressapp.utils;

import com.example.expressapp.R;
import com.example.expressapp.config.RecordList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordListJson {
    public static String setRecordListPost(String uid) {
        String content = null;
        try {
            content = "uid=" + URLEncoder.encode(uid,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static List<RecordList> parseRecordList (String jsonstr) {
        List<RecordList> lists = new ArrayList<RecordList>();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            int code = jsonObject.getInt("code");
            if (code == 0){
                JSONObject jsonBody = jsonObject.getJSONObject("body");
                JSONArray jsonArray = jsonBody.getJSONArray("list");
                for (int i=0;i<jsonArray.length();i++){
                    RecordList list = new RecordList();
                    JSONObject recordJson = jsonArray.getJSONObject(i);
                    list.setSendId(recordJson.getString("id"));
                    list.setExp_code(recordJson.getString("exp_code"));
                    list.setExp_time(recordJson.getString("in_time"));
                    list.setGet_status(recordJson.getString("status_desc"));
                    list.setGet_time(recordJson.getString("out_time"));
                    list.setCode(code);
                    lists.add(list);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }
}
