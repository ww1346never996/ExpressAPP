package com.example.expressapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.expressapp.R;
import com.example.expressapp.config.GetPost;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.RecordList;
import com.example.expressapp.utils.GetPostAdapter;
import com.example.expressapp.utils.GetPostJson;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.RecordListAdapter;
import com.example.expressapp.utils.RecordListJson;

import java.util.ArrayList;
import java.util.List;

public class GetPostActivity extends AppCompatActivity {                         //取件类

    private String uid;
    private String sid;
    private ListView recordList;
    private String TAG="调试";
    private SwipeRefreshLayout sRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_getpost);

        uid = getIntent().getStringExtra("uid");
        sid = GlobalData.getSid();

        final MyHttpConnection conn = new MyHttpConnection();
        final GetPostJson listJson = new GetPostJson();
        final String content = listJson.setGetPost(uid,sid);
        String result = conn.getJson(GlobalData.basic_path + "9002/sexp/order/all/list", content);
        final List<GetPost> list = listJson.parseGetPostList(result);

//        GetPost p = new GetPost();
//        List<GetPost> list = new ArrayList<>();
//        list.add(p);
//        Log.d(TAG,"List"+list.get(0).getIn_time());


        //设置listView
        GetPostAdapter adapter = new GetPostAdapter(GetPostActivity.this, R.layout.activity_get_post, list);
        recordList = (ListView) findViewById(R.id.listview_getbox);

        //设置点击事件
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentToConfirm = new Intent(GetPostActivity.this,GetPostConfirmActivity.class);
                intentToConfirm.putExtra("uid",GlobalData.getUid());
                intentToConfirm.putExtra("order_id",list.get(i).getId());
                startActivity(intentToConfirm);
            }
        });
        recordList.setAdapter(adapter);
        //设置下拉刷新
        final Handler handler = new Handler();
        sRefreshLayout = findViewById(R.id.swipe_ly1);
        sRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String rResult = conn.getJson(GlobalData.basic_path + "9002/sexp/order/all/list", content);
                List<GetPost> rList = listJson.parseGetPostList(rResult);
                GetPostAdapter rAdapter = new GetPostAdapter(GetPostActivity.this, R.layout.activity_get_post, rList);
                recordList.setAdapter(rAdapter);
                //结束刷新状态
                if (!rList.isEmpty() && rList.get(0).getCode() == 0) {
                    sRefreshLayout.setRefreshing(false);
                }
            }
        });


    }
}