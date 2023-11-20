package com.jnu.student.data;

import android.content.Context;

import java.io.Serializable;

public class TaskItem implements Serializable {
    private String taskName;
    private int taskNeedTimes;
    private int taskReward;
    private int taskType;
    private int taskCntTimes;
    private boolean done;

    public TaskItem(String name, int times, int reward) {
        taskName = name;
        taskNeedTimes = times;
        taskReward = reward;
        taskCntTimes = 0;
        if(taskCntTimes >= taskNeedTimes) done = true;
        else done = false;
    }
    public String getTaskName() {
        return taskName;
    }

    public int getTaskNeedTimes() {
        return taskNeedTimes;
    }

    public int getTaskReward() {
        return taskReward;
    }
    public int getTaskType() {
        return taskType;
    }
    public int getTaskCntTimes() {
        return taskCntTimes;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskNeedTimes(int taskNeedTimes) {
        this.taskNeedTimes = taskNeedTimes;
        updateDoneState();
    }
    public void setTaskReward(int taskReward) {
        this.taskReward = taskReward;
    }
    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
    public void setTaskCntTimes(int taskCntTimes) {
        this.taskCntTimes = taskCntTimes;
        updateDoneState();
    }
    public void addCntTimes(){
        ++this.taskCntTimes;
        updateDoneState();
    }
    public boolean isDone() { return done;}
    private void updateDoneState(){
        if(taskCntTimes >= taskNeedTimes) done = true;
        else done = false;
    }
}
