package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.RecordDetail;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.RecordDetailJson;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView DsendId_text;
    private TextView Dphone_text;
    private TextView Dposition_text;
    private TextView DsendTime_text;
    private TextView DgetTime_text;
    private String uid;
    private String order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorddetail);
        DgetTime_text = findViewById(R.id.DgetTime_text);
        Dphone_text = findViewById(R.id.Dphone_text);
        Dposition_text = findViewById(R.id.Dposition_text);
        DsendId_text = findViewById(R.id.DsendId_text);
        DsendTime_text = findViewById(R.id.DsendTime_text);
        //获取Intent传值
        Intent intentFormList = getIntent();
        uid = intentFormList.getStringExtra("uid");
        order_id = intentFormList.getStringExtra("order_id");
        //调用API
        MyHttpConnection conn = new MyHttpConnection();
        RecordDetailJson recordDetailJson = new RecordDetailJson();
        String content = recordDetailJson.setDetailPost(uid,order_id);
        String result = conn.getJson(GlobalData.basic_path + "9002/capp/order/detail",content);
        RecordDetail recordDetail = recordDetailJson.parseRecordDetailJson(result);
        if (recordDetail.getErr().equals("success")){
            DsendId_text.setText(recordDetail.getExp_code());
            Dposition_text.setText(recordDetail.getAddress());
            Dphone_text.setText(recordDetail.getConsignee_phone());
            DgetTime_text.setText(recordDetail.getOut_time());
            DsendTime_text.setText(recordDetail.getIn_time());
        }else {
            Toast.makeText(getApplicationContext(),recordDetail.getErr(),Toast.LENGTH_LONG).show();
        }
    }
}
