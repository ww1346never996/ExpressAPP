package com.example.expressapp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class UserINFO {
    public static boolean saveUserInfo(String name,String password){
        //写入用户信息
        try {
            File file = new File("/data/data/com.example.expressapp/UserInfo.txt");
            String result = name + "##" + password;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(result.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("/data/data/com.example.expressapp/UserInfo.txt");
        if (file != null || file.exists() || !file.isDirectory()){
            return true;
        }else {
            return false;
        }
    }
    public static Map<String,String> readUserInfo() {
        //读取用户信息
        try {
            Map<String,String> userInfoMap = new HashMap<String,String>();
            File file = new File("/data/data/com.example.expressapp/UserInfo.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String content = bufferedReader.readLine();
            String[] UserInfo = content.split("##");
            userInfoMap.put("userName",UserInfo[0]);
            userInfoMap.put("userPassword",UserInfo[1]);
            fileInputStream.close();
            return userInfoMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //删除用户信息
    public static void deleteUserInfo() {
        File file = new File("/data/data/com.example.expressapp/UserInfo.txt");
        if (file != null || file.exists() || !file.isDirectory()){
            file.delete();
        }
    }
}
