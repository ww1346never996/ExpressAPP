package com.example.expressapp.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;
import com.example.expressapp.config.GetPost;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.RecordList;

import java.util.List;

public class GetPostAdapter extends ArrayAdapter<GetPost>{

    private int resourceID;


    public GetPostAdapter(@NonNull Context context, int resource, @NonNull List<GetPost> objects) {

        super(context, resource, objects);
        resourceID = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //获取当前实例
        final GetPost recordList = getItem(position);
        final String uid  = GlobalData.getUid();
        //使用LayoutInflater为子项加载传入的布局


        View view = LayoutInflater.from(getContext()).inflate(resourceID,null);
        TextView sendId_text = view.findViewById(R.id.id);
        TextView sendTime_text = view.findViewById(R.id.in_time);
        TextView send_addr_text = view.findViewById(R.id.addr);
        TextView send_boxcounts = view.findViewById(R.id.count);
        TextView send_boxsize = view.findViewById(R.id.box_size);


        //引入recordList对象的属性值
        sendId_text.setText(recordList.getId());
        sendTime_text.setText(recordList.getIn_time());
        send_addr_text.setText(recordList.getAddr());
        send_boxcounts.setText(String.valueOf(recordList.getCount()));
        send_boxsize.setText(recordList.getBox_size());
        return view;
    }

}

