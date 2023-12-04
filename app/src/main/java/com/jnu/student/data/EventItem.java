package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventItem implements Serializable {
    String eventName;
    int eventValue;
    String time;
    public EventItem(String eventName, int reward, String time) {
        this.eventName = eventName;
        this.eventValue = reward;
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventValue() {
        return eventValue;
    }

    public String getTime() {
        return time;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventValue(int eventValue) {
        this.eventValue = eventValue;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
