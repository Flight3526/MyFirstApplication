package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import com.jnu.student.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventBank {                             // 管理收入支出事件
    final static String DATA_FILENAME = "data_event.data";
    public static List<EventItem> loadEventItems(Context context) throws FileNotFoundException {
        List<EventItem> data = new ArrayList<>();
        try{
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<EventItem>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("EventSerialization", "Data loaded successfully: " + data.size());
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return data;
    }
    public static void saveEventItems(Context context, List<EventItem> eventList){
        try{
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(eventList);
            objectOut.close();
            fileOut.close();
            Log.d("EventSerialization", "Data is serialized and saved");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void addEventItem(Context context, EventItem eventItem){     // 直接添加item，不用在外部先加载再保存
        try {
            List<EventItem> data = loadEventItems(context);
            data.add(0, eventItem);
            saveEventItems(context, data);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void addEventItem(Context context, String name, int value){     // 只传入name和value，时间在内部计算
        try {
            List<EventItem> data = loadEventItems(context);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String currentDateTime = dateFormat.format(calendar.getTime());
            data.add(0, new EventItem(name, value, currentDateTime));
            saveEventItems(context, data);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
