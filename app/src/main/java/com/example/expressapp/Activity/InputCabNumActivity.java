package com.example.expressapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.expressapp.R;
import com.example.expressapp.config.GlobalData;
import com.example.expressapp.utils.MyHttp;
import com.example.expressapp.utils.MyHttpConnection;
import com.example.expressapp.utils.NetworkUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class InputCabNumActivity extends AppCompatActivity {
    private EditText et_inputcabnum_cabnum;
    private String inputcabnum_cabnum;
    private Button inputcabnum_ok;
    private TextView tv_show_msg;




    @Override
    protected void onCreate(Bundle saveInstenceState){
        super.onCreate(saveInstenceState);

        setContentView(R.layout.activity_inputcabnum);
        et_inputcabnum_cabnum=(EditText)findViewById(R.id.et_inputcabnum_cabnum);
        inputcabnum_ok=(Button)findViewById(R.id.btn_inputcabnum_ok);
        tv_show_msg = (TextView) findViewById(R.id.tv_show_msg);



        inputcabnum_ok.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                inputcabnum_cabnum=et_inputcabnum_cabnum.getText().toString();
                if(!inputcabnum_cabnum.equals("")){
                        Intent intentToDelivery = new Intent(InputCabNumActivity.this, DeliveryActivity.class);
                        intentToDelivery.putExtra("inputcabnum_cabnum",inputcabnum_cabnum);
                        startActivity(intentToDelivery);
                }
                else{
                    Toast.makeText(InputCabNumActivity.this,"请输入柜体编号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
