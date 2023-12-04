package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import com.jnu.student.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataTime {
    private String date;
    private int weekOrder;
    private Context context;
    private String DATA_TIME = "data_time.data";
   public DataTime() {
       context = MyApplication.context;
       File file = new File(context.getFilesDir(), DATA_TIME);
       if(!file.exists()){
           Log.i("loadTimeData","File does not exist");
           date = null;
           weekOrder = 0;
       }
       else {
           Log.i("loadTimeData","File exists");
           loadData();
       }
   }
    private void loadData(){
        try{
            FileInputStream fileIn = context.openFileInput(DATA_TIME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            date = (String) objectIn.readObject();
            weekOrder = (int) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.i("loadTimeData","Data loaded successfully");
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            Log.i("loadTimeData","Data load failed");
        }
    }

    public void saveData(String date, int weekOrder){
        try{
            this.date = date;
            this.weekOrder = weekOrder;
            FileOutputStream fileOut = context.openFileOutput(DATA_TIME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(date);
            objectOut.writeObject(weekOrder);
            objectOut.close();
            fileOut.close();
            Log.i("saveTimeData","Data saved successfully " + date + " " + weekOrder);
        }catch(IOException e){
            e.printStackTrace();
            Log.i("saveTimeData","Data save failed");
        }
    }
    public String getDate() {
        return date;
    }

    public int getWeekOrder() {
        return weekOrder;
    }
}
