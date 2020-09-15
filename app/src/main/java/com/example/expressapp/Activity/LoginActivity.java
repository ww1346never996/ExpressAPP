package com.example.expressapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.expressapp.R;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.LoginInfo;
import com.example.expressapp.utils.JsonUtils;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.UserINFO;

public class LoginActivity extends AppCompatActivity {
    private Button login_button;
    private Button signUp_button;
    private EditText phone_text;
    private EditText password_text;
    private CheckBox rem_user;
    private String phone;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        login_button = findViewById(R.id.login_button);
        phone_text = findViewById(R.id.name_text);
        password_text = findViewById(R.id.password_text);
        rem_user = findViewById(R.id.cb_saveUser);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //读取输入信息
                phone = phone_text.getText().toString();
                password = password_text.getText().toString();
                //写入表单
                JsonUtils jsonUtils = new JsonUtils();
                String content = jsonUtils.setLoginPost(phone,password);
                //启用连接
                MyHttpConnection conn = new MyHttpConnection();
                String result = conn.getJson(GlobalData.basic_path+"9000/capp/login/normal",content);
                //解析json
                LoginInfo loginInfo = jsonUtils.parseLoginInfo(result);
                //验证返回码
                if (loginInfo.getCode() == 0) {
                    //写入正确的登录信息
                    if (rem_user.isChecked()){
                        UserINFO userINFO = new UserINFO();
                        Boolean flag = userINFO.saveUserInfo(phone,password);
                        if (flag == false){
                            Toast.makeText(getApplicationContext(),"保存用户信息失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                    //跳转至用户主界面
                    Intent intentToUser = new Intent(LoginActivity.this, UserActivity.class);
                    intentToUser.putExtra("id",loginInfo.getId());
                    intentToUser.putExtra("sid",loginInfo.getSid());
                    startActivity(intentToUser);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"发生错误   "+loginInfo.getError(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
