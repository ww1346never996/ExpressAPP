package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.utils.UserINFO;

public class UserActivity extends AppCompatActivity {
    private Button send_button;
    private ImageButton send_Ibutton;
    private String uid;
    private String sid;
    private Button get_button;
    private Button info_button;
    private Button record_button;
    private ImageButton get_Ibutton;
    private ImageButton info_Ibutton;
    private ImageButton record_Ibutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        TextView textView = findViewById(R.id.hello_text);
        Button logout_button = findViewById(R.id.logout_button);
        final Intent intentFormLogin = getIntent();
        uid = intentFormLogin.getStringExtra("id");
        sid = intentFormLogin.getStringExtra("sid");
        GlobalData.setUid(uid);
        GlobalData.setSid(sid);
        textView.setText("欢迎 "+uid);
        send_button = findViewById(R.id.send_button);
        send_Ibutton = findViewById(R.id.send_imageButton);
        get_button = findViewById(R.id.get_button);
        get_Ibutton = findViewById(R.id.get_Ibutton);
        info_button = findViewById(R.id.info_button);
        info_Ibutton = findViewById(R.id.info_Ibutton);
        record_button = findViewById(R.id.record_button);
        record_Ibutton = findViewById(R.id.record_Ibutton);
        //退出登录
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToLogin = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intentToLogin);
                UserINFO userINFO =  new UserINFO();
                userINFO.deleteUserInfo();
                finish();
            }
        });
        //跳转到投递页面
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSend = new Intent(UserActivity.this,InputCabNumActivity.class);
                startActivity(intentToSend);
            }
        });
        send_Ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSend = new Intent(UserActivity.this,InputCabNumActivity.class);
                startActivity(intentToSend);
            }
        });
        //跳转到取件页面
        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToGet = new Intent(UserActivity.this,GetPostActivity.class);
                intentToGet.putExtra("uid",uid);
                startActivity(intentToGet);
            }
        });
        get_Ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToGet = new Intent(UserActivity.this,GetPostActivity.class);
                intentToGet.putExtra("uid",uid);
                startActivity(intentToGet);
            }
        });
        //跳转到记录页面
        record_Ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRecord = new Intent(UserActivity.this,RecordListActivity.class);
                intentToRecord.putExtra("uid",uid);
                startActivity(intentToRecord);
            }
        });
        record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRecord = new Intent(UserActivity.this,RecordListActivity.class);
                intentToRecord.putExtra("uid",uid);
                startActivity(intentToRecord);
            }
        });
        //跳转到格口信息页面
        info_Ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCell = new Intent(UserActivity.this,GetNumActivity.class);
                startActivity(intentToCell);
            }
        });
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCell = new Intent(UserActivity.this,GetNumActivity.class);
                startActivity(intentToCell);
            }
        });
    }
}
