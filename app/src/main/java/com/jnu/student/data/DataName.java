package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import com.jnu.student.MyApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataName {                          // 存储昵称，签名数据
    final static String DATA_NAME = "data_name.data";
    private static String nickName = "user";
    private static String signature = "积一勺以成江河，累微尘以崇峻极";
    private static boolean loadFlag = false;             // 是否已加载

    private static void loadData(){
        try {
            FileInputStream fileIn = MyApplication.context.openFileInput(DATA_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            nickName = (String) in.readObject();
            signature = (String) in.readObject();
            in.close();
            fileIn.close();
            loadFlag = true;
            Log.d("loadData", "Data loaded successfully");
        } catch(IOException | ClassNotFoundException e){
            Log.d("loadData", "Data loaded failed");
            e.printStackTrace();
        }
    }

    private static void saveData(String nickName, String signature){
        try {
            FileOutputStream fileOut = MyApplication.context.openFileOutput(DATA_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(nickName);
            out.writeObject(signature);
            out.close();
            fileOut.close();
            Log.d("saveScore", "Data saved successfully");
        } catch(IOException e){
            Log.d("saveScore", "Data saved failed");
            e.printStackTrace();
        }
    }

    public static String getNickName(){
        if(!loadFlag) loadData();
        return nickName;
    }

    public static void setNickName(String _nickName) {
        nickName = _nickName;
        saveData(_nickName, signature);
    }

    public static String getSignature(){
        if(!loadFlag) loadData();
        return signature;
    }

    public static void setSignature(String _signature){
        signature = _signature;
        saveData(nickName, _signature);
    }
}
