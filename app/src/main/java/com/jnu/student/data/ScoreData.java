package com.jnu.student.data;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.jnu.student.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class ScoreData {
    final static String DATA_SCORES = "saved_scores.data";
    private static int score;
    public static void loadScore(){
        try {
            FileInputStream fileIn = MyApplication.context.openFileInput(DATA_SCORES);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            score = (int) in.readObject();
            in.close();
            fileIn.close();
            Log.d("loadScore", "Data loaded successfully");
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static void saveScore() {
        try {
            FileOutputStream fileOut = MyApplication.context.openFileOutput(DATA_SCORES, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(score);
            out.close();
            fileOut.close();
            Log.d("saveScore", "Data saved successfully");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static int getScore(){ return score; }
    public static boolean updateScore(int num){
        int temp = score + num;
        if(temp < 0) {
            if(num > 0){
                score = Integer.MAX_VALUE;
                saveScore();
                Toast.makeText(MyApplication.context, "积分已达上限", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MyApplication.context, "积分不足，无法兑换", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        if(num < 0) Toast.makeText(MyApplication.context, "兑换成功", Toast.LENGTH_SHORT).show();
        score = temp;
        saveScore();
        return true;
    }
}
