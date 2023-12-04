package com.jnu.student.data;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
public class RewardBank {
    final static String DATA_FILENAME = "items_reward.data";
    public static List<RewardItem> LoadRewardItems(Context context){
        List<RewardItem> data = new ArrayList<>();
        try{
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<RewardItem>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("RewardSerialization", "Data loaded successfully: " + data.size());
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return data;
    }
    public static void SaveRewardItems(Context context, List<RewardItem> rewardList){
        try{
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(rewardList);
            objectOut.close();
            fileOut.close();
            Log.d("RewardSerialization", "Data is serialized and saved");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
