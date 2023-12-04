package com.jnu.student.data;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
public class TaskBank {
    final static String[] DATA_FILENAME = {"items_task_daily.data", "items_task_weekly.data", "items_task_normal.data"};
    public static List<TaskItem> loadTaskItems(Context context, int type) {
        List<TaskItem> data = new ArrayList<>();
        try{
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME[type]);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<TaskItem>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("TaskSerialization", "Data loaded successfully: " + data.size());
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return data;
    }

    public static void saveTaskItems(Context context, List<TaskItem> taskList, int type){
        try{
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME[type], Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(taskList);
            objectOut.close();
            fileOut.close();
            Log.d("TaskSerialization", "Data is serialized and saved");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
