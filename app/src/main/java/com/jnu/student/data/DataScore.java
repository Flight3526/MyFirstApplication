package com.jnu.student.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jnu.student.MyApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataScore {
    private final static String DATA_SCORES = "data_score.data";
    private static int score = 0;
    private static void loadScore(){
        try {
            FileInputStream fileIn = MyApplication.context.openFileInput(DATA_SCORES);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            score = (int) in.readObject();
            in.close();
            fileIn.close();
            Log.d("loadScore", "Data loaded successfully");
        } catch(IOException | ClassNotFoundException e){
            Log.d("loadScore", "Data loaded failed");
            e.printStackTrace();
        }
    }
    private static void saveScore() {
        try {
            FileOutputStream fileOut = MyApplication.context.openFileOutput(DATA_SCORES, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(score);
            out.close();
            fileOut.close();
            Log.d("saveScore", "Data saved successfully");
        } catch(IOException e){
            Log.d("saveScore", "Data saved failed");
            e.printStackTrace();
        }
    }
    public static int getScore(){
        if(0 == score) loadScore();
        return score;
    }
    public static boolean updateScore(int num){
        int temp = score + num;
        if(temp < 0) {
            if(num > 0){
                score = Integer.MAX_VALUE;
                saveScore();
                Toast.makeText(MyApplication.context, "任务币已达上限", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MyApplication.context, "任务币不足，无法兑换", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(num < 0) Toast.makeText(MyApplication.context, "兑换成功", Toast.LENGTH_SHORT).show();
        score = temp;
        saveScore();
        return true;
    }
}
