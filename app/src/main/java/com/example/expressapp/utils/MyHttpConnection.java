package com.example.expressapp.utils;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyHttpConnection {
    public String getJson(String path, String content) {
        String result = new String();
        try {
            //设置各种连接参数
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content_Type","application/json");
            //使用获取的content写入输出流
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.close();
            //查询状态码，成功返回json
            int code = conn.getResponseCode();
            if (code == 200){
                //读取返回的json
                InputStream inputStream = conn.getInputStream();
                result = getStringFromInputStream(inputStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //通过字节流返回字符串
    private static String getStringFromInputStream(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        // 把流中的数据转换成字符串, 采用的编码是: utf-8
        String status = baos.toString();
        baos.close();
        return status;
    }
}
