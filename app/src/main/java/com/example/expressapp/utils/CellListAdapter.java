package com.example.expressapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.expressapp.R;

import com.example.expressapp.config.CellInfo;
import com.example.expressapp.config.RecordList;

import java.util.List;

public class CellListAdapter extends ArrayAdapter<CellInfo> {
    //resourceID指定ListView的布局方式
    private int resourceID;
    //重写Adapter的构造器
    public CellListAdapter(Context context, int textViewResourceID , List<CellInfo> objects){
        super(context, textViewResourceID, objects);
        resourceID = textViewResourceID;
    }
    //自定义item资源的解析方式
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String num =  null;
        String type = null;
        //获取实例
        CellInfo cellInfo = getItem(position);
        //加载传入的布局
        View view = LayoutInflater.from(getContext()).inflate(resourceID,null);
        TextView cellnum_text = view.findViewById(R.id.cellnum_text);
        TextView cellsize_text = view.findViewById(R.id.cellsize_text);
        //引入对象属性值
        num = String.valueOf(cellInfo.getCode());
        cellnum_text.setText(num);
        //通过返回码判断格口大小
        switch (cellInfo.getType()){
            case 10901: type = "大";
            break;
            case 10902: type = "中";
            break;
            case 10903: type = "小";
            break;
            case  10904: type = "超小";
            break;
        }
        cellsize_text.setText(type);
        return view;
    }
}
