package com.example.expressapp.utils;

import com.example.expressapp.config.CabinetInfo;
import com.example.expressapp.config.DeliveryExpDetailInfo;
import com.example.expressapp.config.DeliveryOpenCellInfo;
import com.example.expressapp.config.deliveryConfirmResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class MyJsonParser {
    private String jsonStr;



     public MyJsonParser(String str){
        this.jsonStr = str;
    }
public DeliveryExpDetailInfo parseDeliveryExpDetailInfo(){
        DeliveryExpDetailInfo deliveryexpdetailinfo = new DeliveryExpDetailInfo();
        try{
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            deliveryexpdetailinfo.setCode(jsonObj.getInt("code"));
            JSONObject jsonBody = jsonObj.getJSONObject("body");
            if(jsonBody != null){
                deliveryexpdetailinfo.setAddress(jsonBody.getString("address"));
                deliveryexpdetailinfo.setConsignee_phone(jsonBody.getString("consignee_phone"));
                deliveryexpdetailinfo.setEnd_time(jsonBody.getString("end_time"));
                deliveryexpdetailinfo.setStatus(jsonBody.getInt("status"));
                deliveryexpdetailinfo.setStatus_desc(jsonBody.getString("status_desc"));
                deliveryexpdetailinfo.setExp_code(jsonBody.getString("exp_code"));
                deliveryexpdetailinfo.setIn_time(jsonBody.getString("in_time"));
                deliveryexpdetailinfo.setOut_time(jsonBody.getString("out_time"));
                deliveryexpdetailinfo.setExpire_time(jsonBody.getString("expire_time"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            deliveryexpdetailinfo.setError(e.getMessage().toString());
        }
        return deliveryexpdetailinfo;
}
public CabinetInfo parseCabinetInfo(){
        CabinetInfo cabinetinfo =new CabinetInfo();
        try{
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            cabinetinfo.setCode(jsonObj.getInt("code"));
            JSONObject jsonBody = jsonObj.getJSONObject("body");
            if(jsonBody != null){
                cabinetinfo.setName(jsonBody.getString("name"));
                cabinetinfo.setAddr(jsonBody.getString("addr"));
                JSONArray jsonArray =jsonBody.getJSONArray("avail_cells");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject sonObject = (JSONObject) jsonArray.get(i);
                    int type = sonObject.getInt("type");
                    switch (type){
                        case 10903:
                            cabinetinfo.setSmallCellCount(sonObject.getInt("idle_count"));
                            break;
                        case 10902:
                            cabinetinfo.setMiddleCellCount(sonObject.getInt("idle_count"));
                            break;
                        case 10901:
                            cabinetinfo.setBigCellCount(sonObject.getInt("idle_count"));
                            break;
                        case 10904:
                            cabinetinfo.setTinyCellCount(sonObject.getInt("idle_count"));
                            break;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            cabinetinfo.setError(e.getMessage().toString());
        }
        return cabinetinfo;
}
public deliveryConfirmResult parserForDeliveryConfirm(){
    deliveryConfirmResult deliveryconfirmresult =new deliveryConfirmResult();
    try{
        JSONObject jsonObj = new JSONObject(this.jsonStr);
        deliveryconfirmresult.setCode(jsonObj.getInt("code"));
        deliveryconfirmresult.setMsg(jsonObj.getString("msg"));

    }catch (Exception e) {
        e.printStackTrace();
        deliveryconfirmresult.setMsg(e.getMessage().toString());
    }
    return deliveryconfirmresult;
}
    public DeliveryOpenCellInfo  parserForDeliveryOpenCell(){
        DeliveryOpenCellInfo deliveryOpenCellInfo =new DeliveryOpenCellInfo();
        try{
            JSONObject jsonObj = new JSONObject(this.jsonStr);
            deliveryOpenCellInfo.setCode(jsonObj.getInt("code"));
            deliveryOpenCellInfo.setMsg(jsonObj.getString("msg"));
            JSONObject jsonBody = jsonObj.getJSONObject("body");
            if(jsonBody != null){
                deliveryOpenCellInfo.setCellcode(jsonBody.getString("code"));
                deliveryOpenCellInfo.setDesc(jsonBody.getString("desc"));
                deliveryOpenCellInfo.setId(jsonBody.getString("id"));
                deliveryOpenCellInfo.setType(jsonBody.getString("type"));
                deliveryOpenCellInfo.setOrder_id(jsonBody.getString("order_id"));
            }

        }catch (Exception e) {
            e.printStackTrace();
            deliveryOpenCellInfo.setMsg(e.getMessage().toString());
        }
        return deliveryOpenCellInfo;

    }
}
