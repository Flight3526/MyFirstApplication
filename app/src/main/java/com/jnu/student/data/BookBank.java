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

public class BookBank {
    final static String DATA_FILENAME = "book_items.data";


    public static List<BookItem> LoadBookItems(Context context) throws FileNotFoundException {
        List<BookItem> data = new ArrayList<>();
        try{
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<BookItem>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "Data loaded successfully" + data.size());
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return data;
    }

    public static void SaveBookItems(Context context, List<BookItem> bookList){
        try{
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(bookList);
            objectOut.close();
            fileOut.close();
            Log.d("Serialization", "Data is serialized and saved");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
