package com.example.expressapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.expressapp.Activity.RecordListActivity;
import com.example.expressapp.R;
import com.example.expressapp.config.RecordList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordListAdapter extends ArrayAdapter<RecordList> {
        //resourceID指定ListView的布局方式
        private int resourceID;
        //重写Adapter的构造器
        public RecordListAdapter(Context context, int textViewResourceID , List<RecordList> objects){
            super(context,textViewResourceID,objects);
            resourceID = textViewResourceID;
        }
        //自定义item资源的解析方式
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //获取当前实例
            RecordList recordList = getItem(position);
            //使用LayoutInfater为子项加载传入的布局
            View view = LayoutInflater.from(getContext()).inflate(resourceID,null);
            TextView sendId_text = view.findViewById(R.id.sendId_text);
            TextView sendTime_text = view.findViewById(R.id.sendTime_text);
            TextView send_getTime_text = view.findViewById(R.id.send_getTime_text);
            TextView send_getStatus_text = view.findViewById(R.id.send_getStatus_text);
            //引入recordList对象的属性值
            sendId_text.setText(recordList.getExp_code());
            sendTime_text.setText(recordList.getExp_time());
            send_getStatus_text.setText(recordList.getGet_status());
            send_getTime_text.setText(recordList.getGet_time());
            return view;
        }
}
