package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.utils.JsonUtils;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.TimeCount;

public class SignUpActivity extends AppCompatActivity {
    private Button register_btn;
    private Button send_btn;
    private String phone;
    private String password;
    private String passwordConfirm;
    private EditText phone_text;
    private EditText password_text;
    private EditText passwordconfrim_text;
    private EditText verifycode_text;
    private String verifyCode;
    private String getVerifyCode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        register_btn = findViewById(R.id.signupconfirm_button);
        send_btn = findViewById(R.id.send_button);
        verifycode_text = findViewById(R.id.signup_verifycode);
        password_text = findViewById(R.id.signup_password);
        phone_text = findViewById(R.id.signup_phone);
        passwordconfrim_text = findViewById(R.id.signup_passwordconfirm);
        //创建TimeCount，设置倒计时60秒，计时间隔1秒，绑定按钮
        final TimeCount time = new TimeCount(60 * 1000,1000,send_btn);
        //设置单击事件
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = phone_text.getText().toString();
                if (phone.equals("")){
                    Toast.makeText(getApplicationContext(),"请输入手机号",Toast.LENGTH_LONG).show();
                }else{
                    //调用api发送验证码
                    MyHttpConnection conn = new MyHttpConnection();
                    JsonUtils jsonUtils = new JsonUtils();
                    String content = jsonUtils.setSignUpCodePost(phone);
                    String jsonverifyCode = conn.getJson(GlobalData.basic_path+"9000/capp/register/send_pcode",content);
                    verifyCode = jsonUtils.parseVerifyCode(jsonverifyCode);
                    if(verifyCode.equals("error")) {
                       Toast.makeText(getApplicationContext(),"该手机已经注册",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_LONG).show();
                        time.start();
                    }
                }
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从文本框获取用户输入
                phone = phone_text.getText().toString();
                passwordConfirm = passwordconfrim_text.getText().toString();
                password = password_text.getText().toString();
                getVerifyCode = verifycode_text.getText().toString();
                //检查输入错误
                if (phone.equals("")){
                    Toast.makeText(getApplicationContext(),"请输入电话号码",Toast.LENGTH_LONG).show();
                }else if (password.equals("")){
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_LONG).show();
                }else if (passwordConfirm.equals("")){
                    Toast.makeText(getApplicationContext(),"请确认密码",Toast.LENGTH_LONG).show();
                }else if (!passwordConfirm.equals(password)){
                    Toast.makeText(getApplicationContext(),"请确认两次密码输入相同",Toast.LENGTH_LONG).show();
                }else if (!verifyCode.equals(getVerifyCode)){
                    Toast.makeText(getApplicationContext(),"验证码有误，请重试",Toast.LENGTH_LONG).show();
                }else {
                    //检查通过则发送验证码请求
                    MyHttpConnection conn = new MyHttpConnection();
                    JsonUtils jsonUtils = new JsonUtils();
                    String content = jsonUtils.setSignUpPost(phone,password,verifyCode);
                    String signUpJson = conn.getJson(GlobalData.basic_path + "9000/capp/register/phone",content);
                    String status = jsonUtils.parseSignUp(signUpJson);
                    if (status.equals("yes")){
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                        //跳转到登录页
                        Intent intentToLogin = new Intent(SignUpActivity.this,LoginActivity.class);
                        startActivity(intentToLogin);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"该手机已经注册",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
