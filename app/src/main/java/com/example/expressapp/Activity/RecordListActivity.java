package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.expressapp.R;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.RecordList;
import com.example.expressapp.utils.JsonUtils;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.RecordListAdapter;
import com.example.expressapp.utils.RecordListJson;

import java.util.List;

public class RecordListActivity extends AppCompatActivity {
    private String uid;
    private SwipeRefreshLayout sRefreshLayout;
    private ListView recordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendlist);
        recordList = findViewById(R.id.record_list);
        //从上一个activity获取数据
        final Intent intentFormUser = getIntent();
        uid = intentFormUser.getStringExtra("uid");
        //调用api获取list
        final MyHttpConnection conn = new MyHttpConnection();
        final RecordListJson listJson = new RecordListJson();
        final String content = listJson.setRecordListPost(uid);
        String result = conn.getJson(GlobalData.basic_path + "9002/capp/order/list",content);
        final List<RecordList> list = listJson.parseRecordList(result);
        //设置listView
        RecordListAdapter adapter = new RecordListAdapter(RecordListActivity.this,R.layout.recordlist_item,list);
        recordList.setAdapter(adapter);
        //设置下拉刷新
        final Handler handler = new Handler();
        sRefreshLayout = findViewById(R.id.swipe_ly);
        sRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String rResult = conn.getJson(GlobalData.basic_path + "9002/capp/order/list",content);
                List<RecordList> rList = listJson.parseRecordList(rResult);
                RecordListAdapter rAdapter = new RecordListAdapter(RecordListActivity.this,R.layout.recordlist_item,rList);
                recordList.setAdapter(rAdapter);
                //结束刷新状态
                if (!rList.isEmpty() && rList.get(0).getCode() == 0){
                    sRefreshLayout.setRefreshing(false);
                }
            }
        });
        //设置点击事件
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //跳转至订单详情
                String order_id = list.get(position).getSendId();
                Intent intentToDetail = new Intent(RecordListActivity.this,RecordDetailActivity.class);
                intentToDetail.putExtra("uid",uid);
                intentToDetail.putExtra("order_id",order_id);
                startActivity(intentToDetail);
            }
        });
    }
}
