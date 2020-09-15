package com.example.expressapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;
import com.example.expressapp.config.DeliveryExpDetailInfo;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.config.deliveryConfirmResult;
import com.example.expressapp.utils.MyHttp;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.MyJsonParser;
import com.example.expressapp.utils.NetworkUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DeliveryConfirmActivity extends AppCompatActivity {
    private Button btn_delivery_confirm_ok;
    private Button btn_delivery_confirm_quit;
    private String str_json;
    private String et_sendexp_expnum;
    private  String et_sendexp_rectel;
    private String Cabnum;
    private String Cellnum;
    private TextView tv_delivery_confirm_expnum;
    private TextView tv_delivery_confirm_tel;
    private TextView tv_delivery_confirm_cabnum;
    private TextView tv_delivery_confirm_cellnum;
    private String order_id;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_delivery_confirm);
        //强制在主线程请求网络连接
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Intent intentFormDelivery = getIntent();
        et_sendexp_expnum= intentFormDelivery.getStringExtra("Exp");
        et_sendexp_rectel= intentFormDelivery.getStringExtra("Tel");
        order_id = intentFormDelivery.getStringExtra("Order_id");
        Cabnum= intentFormDelivery.getStringExtra("Cabnum");
        Cellnum= intentFormDelivery.getStringExtra("Cellnum");
        tv_delivery_confirm_expnum=(TextView)findViewById(R.id.tv_delivery_confirm_expnum);
        tv_delivery_confirm_expnum.setText(et_sendexp_expnum);
        tv_delivery_confirm_tel=(TextView)findViewById(R.id.tv_delivery_confirm_tel);
        tv_delivery_confirm_tel.setText(et_sendexp_rectel);
        tv_delivery_confirm_cabnum=(TextView)findViewById(R.id.tv_delivery_confirm_cabnum);
        tv_delivery_confirm_cabnum.setText(Cabnum);
        tv_delivery_confirm_cellnum=(TextView)findViewById(R.id.tv_delivery_confirm_cellnum);
        tv_delivery_confirm_cellnum.setText(Cellnum);
//取消投递
        btn_delivery_confirm_quit=(Button)findViewById(R.id.btn_delivery_confirm_quit);
        btn_delivery_confirm_quit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent it_toUserActivity=new Intent(DeliveryConfirmActivity.this, UserActivity.class);
                startActivity(it_toUserActivity);
            }
        });

        //在布局文件中获取Id为btn_delivery_confirm_yes的Button控件实例
        btn_delivery_confirm_ok =(Button)findViewById(R.id.btn_delivery_confirm_yes);
        //为Button控件实例btn_delivery_confirm_ok绑定单击监听器
        btn_delivery_confirm_ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                str_json=invokeDeliveryConfirmAPI( ); //调用服务器API，与服务器交换数据，服务器回JSON字符串
                //调用服务器API，与服务器交换数据，服务器返回JSON字符串
                deliveryConfirmResult deliveryConfirmResult;
                deliveryConfirmResult=new MyJsonParser(str_json).parserForDeliveryConfirm( );
                switch(deliveryConfirmResult.getCode( )){
                    case 0:
                        AlertDialog.Builder dialog=new AlertDialog.Builder(DeliveryConfirmActivity.this);
                        dialog.setTitle("提示").setMessage("投递完成");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener( ){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //TODO Auto-generated method stub
                                dialog.dismiss();
                                //销毁对话框
                                Intent intentToUser = new Intent(DeliveryConfirmActivity.this,UserActivity.class);
                                intentToUser.putExtra("id",GlobalData.getUid());
                                startActivity(intentToUser);
                                finish( );
                                //销毁当前Activity
                            }
                        });
                        dialog.create( ).show( ); //显示当前对话框
                        break;
                    case 15100:
                        Toast.makeText(DeliveryConfirmActivity.this, "订单已存在", Toast.LENGTH_LONG).
                                show( );
                        break;
                    case 10001:
                        Toast.makeText(DeliveryConfirmActivity.this, "柜体无效", Toast.LENGTH_LONG).
                                show( );
                        break;
                    case 10002:
                        Toast.makeText(DeliveryConfirmActivity.this, "格口无效", Toast.LENGTH_LONG).
                                show( );
                        break;
                }
            }
        });
    }
    String invokeDeliveryConfirmAPI(){
        String URL="http://101.200.89.170:9002/capp/delivery/confirm";
        String userid= GlobalData.getUid();
        MyHttpConnection conn = new MyHttpConnection();
        String content = null;
        try {
            content = "uid=" + URLEncoder.encode(userid,"UTF-8") + "&order_id=" + URLEncoder.encode(order_id,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return conn.getJson(URL,content);

    }
}
