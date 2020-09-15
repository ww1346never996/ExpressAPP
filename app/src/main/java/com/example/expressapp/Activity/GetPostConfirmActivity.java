package com.example.expressapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;
import com.example.expressapp.config.GetPost;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.utils.JsonUtils;
import com.example.expressapp.utils.MyHttpConnection;

public class GetPostConfirmActivity extends AppCompatActivity {
    TextView id_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpost);
        final Intent intentFormList = getIntent();
        final Button getpost_btn = findViewById(R.id.getpost_btn);
        final String uid = intentFormList.getStringExtra("uid");
        final String code = intentFormList.getStringExtra("order_id");
        id_text = findViewById(R.id.confirmid);
        id_text.setText(code);
        getpost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String openurl = "http://api.ec-logistics.cn/cabzoo/capp/retrieve/apply";
                String openCheckurl = "http://api.ec-logistics.cn/cabzoo/capp/retrieve/check";
                MyHttpConnection conn = new MyHttpConnection();
                JsonUtils jsonUtils = new JsonUtils();
                String content = jsonUtils.setOpenPost(uid,code);
                String openResult = conn.getJson(openurl,content);
                String openCheckResult = conn.getJson(openCheckurl,content);
                String openStatus = jsonUtils.parseOpenJson(openResult);
                String openCheck = jsonUtils.parseOpenCheckJson(openCheckResult);
                if (openStatus.equals("成功")){
                    Toast.makeText(getApplicationContext(),"取件成功",Toast.LENGTH_LONG).show();
                    Intent intentToList = new Intent(GetPostConfirmActivity.this, UserActivity.class);
                    intentToList.putExtra("id", GlobalData.getUid());
                    startActivity(intentToList);
                    finish();
                }
                getpost_btn.setText(openStatus);
                getpost_btn.setClickable(false);
                if (openCheck.equals("true")){
                    Toast.makeText(getApplicationContext(),"取件成功",Toast.LENGTH_LONG).show();
                    Intent intentToList = new Intent(GetPostConfirmActivity.this, UserActivity.class);
                    intentToList.putExtra("id", GlobalData.getUid());
                    startActivity(intentToList);
                    finish();
                }
            }
        });
    }

}
