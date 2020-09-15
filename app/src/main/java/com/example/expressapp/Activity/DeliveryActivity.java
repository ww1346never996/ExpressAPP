package com.example.expressapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressapp.R;
import com.example.expressapp.config.CabinetInfo;
import com.example.expressapp.config.DeliveryExpDetailInfo;
import com.example.expressapp.config.DeliveryOpenCellInfo;
import com.example.expressapp.config.GlobalData;
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

public class DeliveryActivity extends AppCompatActivity {
    private EditText et_sendexp_expnum;
    private EditText et_sendexp_rectel;
    private String sendexp_expnum;
    private String sendexp_rectel;
    private Button btn_sendexp_yes;
    private RadioGroup rg_choose_cell;
    private TextView tv_delivery_cab;
    private TextView tv_celltype1;
    private TextView tv_celltype2;
    private TextView tv_celltype3;
    private TextView tv_celltype4;
    private RadioButton rb_celltype1;
    private RadioButton rb_celltype2;
    private RadioButton rb_celltype3;
    private RadioButton rb_celltype4;
    private String str_json;
    private String str_json1;
    public String inputcabnum_cabnum;
    int int_celltype = 10;
    CabinetInfo cabinetInfo=new CabinetInfo();
    // private TextView tv_show_msg;
    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_delivery);
        //显示柜体编号
        Intent intentFormInput = getIntent();
        inputcabnum_cabnum = intentFormInput.getStringExtra("inputcabnum_cabnum");
        GlobalData.setCabNum(inputcabnum_cabnum);
        tv_delivery_cab=(TextView)findViewById(R.id.tv_delivery_cab);
        tv_delivery_cab.setText(inputcabnum_cabnum);
        et_sendexp_expnum=(EditText)findViewById(R.id.et_sendexp_expnum);
        et_sendexp_rectel=(EditText)findViewById(R.id.et_sendexp_rectel);
        btn_sendexp_yes=(Button)findViewById(R.id.btn_sendexp_yes);
        rg_choose_cell=(RadioGroup)findViewById(R.id.rg_choose_cell);
        //通过柜体编号查看柜体信息
        str_json1=invokeCabinetInfoAPI( );
        cabinetInfo=new MyJsonParser(str_json1).parseCabinetInfo();

        int left_count0 = (int) cabinetInfo.getBigCellCount();
        int left_count1 = (int) cabinetInfo.getMiddleCellCount();
        int left_count2 = (int) cabinetInfo.getSmallCellCount();
        int left_count3 = (int) cabinetInfo.getTinyCellCount();
        final String cellType = null;
        //通过ID获取TextView控件实例，并为TextView控件设置箱体剩余数量的文字
        tv_celltype1=(TextView)findViewById(R.id.tv_celltype1);
        tv_celltype1.setText("剩余" + left_count0 + "个");
        tv_celltype2=(TextView)findViewById(R.id.tv_celltype2);
        tv_celltype2.setText("剩余" + left_count1 + "个");
        tv_celltype3=(TextView)findViewById(R.id.tv_celltype3);
        tv_celltype3.setText("剩余" + left_count2 + "个");
        tv_celltype4=(TextView)findViewById(R.id.tv_celltype4);
        tv_celltype4.setText("剩余" + left_count3 + "个");
        //通过ID获取RadioButton实例，并为RadioButton控件设置箱体类型的文字
        rb_celltype1=(RadioButton)findViewById(R.id.rb_celltype1);
        rb_celltype2=(RadioButton)findViewById(R.id.rb_celltype2);
        rb_celltype3=(RadioButton)findViewById(R.id.rb_celltype3);
        rb_celltype4=(RadioButton)findViewById(R.id.rb_celltype4);
        rb_celltype1.setText("大箱");
        rb_celltype2.setText("中箱");
        rb_celltype3.setText("小箱");
        rb_celltype4.setText("超小箱");
        //如果该类型格口剩余为0，则按钮不可单击
        if(left_count0==0){
            rb_celltype1.setClickable(false);
        }
        if(left_count1==0){
            rb_celltype2.setClickable(false);
        }
        if(left_count2==0){
            rb_celltype3.setClickable(false);
        }
        if(left_count3==0){
            rb_celltype4.setClickable(false);
        }

//radiogroup设置
        rg_choose_cell.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                //初始化格口信息文字的背景颜色(白色)和文字颜色(黑色)
                tv_celltype1.setBackgroundColor(Color.WHITE);
                tv_celltype1.setTextColor(Color.BLACK);
                tv_celltype2.setBackgroundColor(Color.WHITE);
                tv_celltype2.setTextColor(Color.BLACK);
                tv_celltype3.setBackgroundColor(Color.WHITE);
                tv_celltype3.setTextColor(Color.BLACK);
                tv_celltype4.setBackgroundColor(Color.WHITE);
                tv_celltype4.setTextColor(Color.BLACK);
                //得到被得到被选中RadiobButton的ID
                int id=group.getCheckedRadioButtonId();
                //根据ID获取RadioButton实例
                RadioButton rb=(RadioButton)DeliveryActivity.this.findViewById(id);
                switch(id){
                    case R.id.rb_celltype1:
                        //为int_celltype赋值箱体种类编号，该变量作为调用服务器API的参数之一
                        int_celltype = 10901;
                        //设置RadioButton被选中后对应的格口信息文字的背景颜色(深灰色)和文字颜色(绿色)
                        tv_celltype1.setBackgroundColor(getResources().getColor(R.color.bg));
                        tv_celltype1.setTextColor(getResources().getColor(R.color.button));
                        break;
                    case R.id.rb_celltype2:
                        int_celltype = 10902;
                        tv_celltype2.setBackgroundColor(getResources().getColor(R.color.bg));
                        tv_celltype2.setTextColor(getResources().getColor(R.color.button));
                        break;
                    case R.id.rb_celltype3:
                        int_celltype = 10903;
                        tv_celltype3.setBackgroundColor(getResources().getColor(R.color.bg));
                        tv_celltype3.setTextColor(getResources().getColor(R.color.button));
                        break;
                    case R.id.rb_celltype4:
                        int_celltype = 10904;
                        tv_celltype4.setBackgroundColor(getResources().getColor(R.color.bg));
                        tv_celltype4.setTextColor(getResources().getColor(R.color.button));
                        break;
                }
                //  tv_show_msg = (TextView) findViewById(R.id.tv_show_msg);
            }});
        btn_sendexp_yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendexp_expnum = et_sendexp_expnum.getText().toString();
                sendexp_rectel = et_sendexp_rectel.getText().toString();
                if(!sendexp_expnum.equals("")&&!et_sendexp_rectel.equals("")&& int_celltype !=0){
                    //调用服务器API，与服务器交换数据，服务器返回JSON字符串
                    Toast.makeText(getApplicationContext(),"点击确认",Toast.LENGTH_LONG).show();
                    str_json=invokeDeliveryOpenCellAPI( );
                    DeliveryOpenCellInfo deliveryOpenCellInfo = new DeliveryOpenCellInfo( );
                    //将JSON字符串解析并返回数据信息
                    deliveryOpenCellInfo = new MyJsonParser(str_json).parserForDeliveryOpenCell( );
                    switch(deliveryOpenCellInfo.getCode()){
                        case 0:
                            Toast.makeText(DeliveryActivity.this, "打开格口成功", Toast.LENGTH_LONG).show( );
                            //创建一个Intent实例，该Intent主要实现从该Activity到DeliveryConfirmActivity的跳转
                            Intent it_toDeliveryConfirmActivity=new Intent(DeliveryActivity.this, DeliveryConfirmActivity.class);
                            //将键值对压入Intent中，为DeliveryConfirmActivity提供数据
                            it_toDeliveryConfirmActivity.putExtra("Tel", sendexp_rectel);
                            it_toDeliveryConfirmActivity.putExtra("Exp", sendexp_expnum);
                            it_toDeliveryConfirmActivity.putExtra("Cabnum", deliveryOpenCellInfo.getCellcode());
                            it_toDeliveryConfirmActivity.putExtra("Cellnum", deliveryOpenCellInfo.getDesc( ));
                            it_toDeliveryConfirmActivity.putExtra("Order_id", deliveryOpenCellInfo.getOrder_id( ));
                            //startActivity方法激活该Intent实现跳转
                            startActivity(it_toDeliveryConfirmActivity);
                            break;
                        case 15100:
                            Toast.makeText(DeliveryActivity.this, "单号已存在", Toast.LENGTH_LONG).show( );
                            break;
                        case 30002:
                            Toast.makeText(DeliveryActivity.this, "余额不足", Toast.LENGTH_LONG).show( );
                            break;
                    }
                }else {
                    if(sendexp_expnum.equals("")){
                        Toast.makeText(DeliveryActivity.this, "请填写快递单号", Toast.LENGTH_LONG).
                                show( );
                    }else {
                        if(et_sendexp_rectel.equals("")){
                            Toast.makeText(DeliveryActivity.this, "请填写收件人手机号", Toast.LENGTH_LONG).
                                    show( );
                        }else {
                            if(int_celltype ==0){
                                Toast.makeText(DeliveryActivity.this, "请填写所需要的箱体", Toast.LENGTH_LONG).
                                        show( );
                            }
                        }
                    }
                }

            }
        });



    }
    String invokeCabinetInfoAPI(){
        String URL="http://101.200.89.170:9002/capp/cabinet/info";
        String userid= GlobalData.getUid();
        inputcabnum_cabnum = GlobalData.getCabNum();
        String content = null;
        try {
            content = "uid=" + URLEncoder.encode(userid,"UTF-8") + "&cabinet_code=" + URLEncoder.encode(inputcabnum_cabnum,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MyHttpConnection conn = new MyHttpConnection();
        return conn.getJson(URL,content);
    }
    String invokeDeliveryOpenCellAPI(){
        String URL="http://101.200.89.170:9002/capp/delivery/allocate_cell";
        String userid= GlobalData.getUid();
        DeliveryExpDetailInfo deliveryExpDetailInfo = new DeliveryExpDetailInfo();
        String orderid= deliveryExpDetailInfo.getExp_code();
        String content = null;
        try {
            content = "uid=" + URLEncoder.encode(userid,"UTF-8") + "&cabinet_code=" + URLEncoder.encode(inputcabnum_cabnum,"UTF-8") + "&cell_type=" + URLEncoder.encode(String.valueOf(int_celltype),"UTF-8")
                    + "&exp_code=" + URLEncoder.encode(sendexp_expnum,"UTF-8") + "&consignee_phone=" + URLEncoder.encode(sendexp_rectel,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MyHttpConnection conn = new MyHttpConnection();
        return conn.getJson(URL,content);
    }
    public String inttostringtype (int i){
        String celltype ="0";
        if(i==10901) celltype="大箱";
        if(i==10902) celltype="中箱";
        if(i==10903) celltype="小箱";
        if(i==10904) celltype="超小箱";
        return celltype;
    }

}
