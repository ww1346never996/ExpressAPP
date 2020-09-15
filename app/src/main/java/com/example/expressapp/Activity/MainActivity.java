package com.example.expressapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expressapp.R;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.LoginInfo;
import com.example.expressapp.utils.JsonUtils;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.UserINFO;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String LoginURL = "http://101.200.89.170:9000/capp/login/normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btn_toLogin;
        Button btn_toSignUp;
        TextView tv;
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        //gif
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        ImageView ivGif = findViewById(R.id.ivGif);
        Glide.with(this).load(R.drawable.mainactivity_gif).apply(options).into(ivGif);



        //调取本地信息
        UserINFO userInfo = new UserINFO();
        Map<String,String> userInfoMap = UserINFO.readUserInfo();
        if (userInfoMap == null){
            //若未有已保存的登录信息,则选择登录或者注册
            btn_toLogin = findViewById(R.id.btn_toLogin);
            btn_toSignUp = findViewById(R.id.btn_toSignUp);


            //艺术字
            String fonts = "fonts/main.ttf";
            tv= (TextView) findViewById(R.id.main_tv);
            tv.setTypeface(Typeface.createFromAsset(getAssets(),fonts));


            btn_toLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentToLogin = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intentToLogin);
                }
            });
            btn_toSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentToSignUp = new Intent(MainActivity.this,SignUpActivity.class);
                    startActivity(intentToSignUp);
                }
            });
        }else {
            String userName = userInfoMap.get("userName");
            String userPassword = userInfoMap.get("userPassword");
            JsonUtils jsonUtils = new JsonUtils();
            String content = jsonUtils.setLoginPost(userName,userPassword);
            //启用网络连接
            MyHttpConnection conn = new MyHttpConnection();
            String JsonLogin = conn.getJson(LoginURL,content);
            //解析json
            LoginInfo loginInfo = jsonUtils.parseLoginInfo(JsonLogin);
            //验证返回码
            if (loginInfo.getCode() == 0) {
                //跳转至用户主界面
                //GlobalData.setSid(loginInfo.getSid());
                //GlobalData.setUid(loginInfo.getId());
                Intent intentToUser = new Intent(MainActivity.this, UserActivity.class);
                intentToUser.putExtra("id",loginInfo.getId());
                intentToUser.putExtra("sid",loginInfo.getSid());
                startActivity(intentToUser);
                this.finish();
            }else {
                Toast.makeText(getApplicationContext(),"发生错误"+loginInfo.getError()+"错误码："+loginInfo.getCode(),Toast.LENGTH_LONG).show();
            }
        }

    }
}