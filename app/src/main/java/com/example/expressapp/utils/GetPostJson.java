package com.example.expressapp.utils;

import com.example.expressapp.R;
import com.example.expressapp.config.GetPost;
import com.example.expressapp.config.RecordList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetPostJson {
    public static String setGetPost(String uid,String sid) {
        String content = null;
        try {
            content = "uid=" + URLEncoder.encode(uid,"UTF-8") + "&status=" + URLEncoder.encode("1","UTF-8") + "&sid=" + URLEncoder.encode(sid,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static List<GetPost> parseGetPostList (String jsonstr) {
        List<GetPost> lists = new ArrayList<GetPost>();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);

            int code = jsonObject.getInt("code");
            if (code == 0){
                JSONObject jsonBody = jsonObject.getJSONObject("body");
                JSONArray jsonArray = jsonBody.getJSONArray("order_list");
                for (int i=0;i<jsonArray.length();i++){
                    GetPost list = new GetPost();
                    JSONObject recordJson = jsonArray.getJSONObject(i);
                    list.setId(recordJson.getString("id"));
                    list.setIn_time(recordJson.getString("in_time"));
                    //第二层解析
                    JSONObject jsonBody1 = recordJson.getJSONObject("addr_info");
                    list.setAddr(jsonBody1.getString("name"));
                    //第三层解析
                    list.setBox_size(jsonBody1.getString("desc"));

                    lists.add(list);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }
}
