package com.jnu.student.data;

import java.io.Serializable;

public class TaskItem implements Serializable {
    private String taskName;
    private int taskTimes;
    private int taskReward;

    public TaskItem(String name, int times, int reward) {
        taskName = name;
        taskTimes = times;
        taskReward = reward;
    }
    public String getTaskName() {
        return taskName;
    }

    public int getTaskTimes() {
        return taskTimes;
    }

    public int getTaskReward() {
        return taskReward;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskTimes(int taskTimes) {
        this.taskTimes = taskTimes;
    }

    public void setTaskReward(int taskReward) {
        this.taskReward = taskReward;
    }
}
