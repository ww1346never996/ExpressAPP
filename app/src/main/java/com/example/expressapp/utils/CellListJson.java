package com.example.expressapp.utils;

import com.example.expressapp.config.CellInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CellListJson {
    public static String setCellListJson(String cabCode) {
        String content = null;
        try {
            content = "cabinet_code=" + URLEncoder.encode(cabCode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static List<CellInfo> parseCellListJson(String jsonstr) {
        List<CellInfo> lists = new ArrayList<CellInfo>();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            int code = jsonObject.getInt("code");
            if (code == 0){
                JSONArray jsonArray = jsonObject.getJSONArray("body");
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonBody = jsonArray.getJSONObject(i);
                    CellInfo list = new CellInfo();
                    list.setConnCode(code);
                    list.setCode(jsonBody.getInt("code"));
                    list.setStatus(jsonBody.getInt("status"));
                    list.setType(jsonBody.getInt("type"));
                    lists.add(list);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }
    public static List<CellInfo> getBigCellList(List<CellInfo> lists){
        List<CellInfo> bigCellList = new ArrayList<CellInfo>();
        for (int i = 0;i<lists.size();i++){
            if (lists.get(i).getType() == 10901){
                bigCellList.add(lists.get(i));
            }
        }
        return bigCellList;
    }
    public static List<CellInfo> getFreeCellList(List<CellInfo> lists){
        List<CellInfo> freeCellList = new ArrayList<CellInfo>();
        for (int i = 0;i<lists.size();i++){
            if (lists.get(i).getStatus() == 11101){
                freeCellList.add(lists.get(i));
            }
        }
        return freeCellList;
    }
    public static List<CellInfo> getMiddleCellList(List<CellInfo> lists){
        List<CellInfo> middleCellList = new ArrayList<CellInfo>();
        for (int i = 0;i<lists.size();i++){
            if (lists.get(i).getType() == 10902){
                middleCellList.add(lists.get(i));
            }
        }
        return middleCellList;
    }
    public static List<CellInfo> getSmallCellList(List<CellInfo> lists){
        List<CellInfo> smallCellList = new ArrayList<CellInfo>();
        for (int i = 0;i<lists.size();i++){
            if (lists.get(i).getType() == 10903){
                smallCellList.add(lists.get(i));
            }
        }
        return smallCellList;
    }
    public static List<CellInfo> getTinyCellList(List<CellInfo> lists){
        List<CellInfo> tinyCellList = new ArrayList<CellInfo>();
        for (int i = 0;i<lists.size();i++){
            if (lists.get(i).getType() == 10904){
                tinyCellList.add(lists.get(i));
            }
        }
        return tinyCellList;
    }
}
