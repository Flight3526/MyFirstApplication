package com.jnu.student;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jnu.student.data.TaskItem;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<TaskItem> taskList;
    public TaskAdapter(List<TaskItem> tasklist){ this.taskList = tasklist;}
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    public void onBindViewHolder(TaskViewHolder holder, int position){
        TaskItem task = taskList.get(position);
        holder.name.setText(task.getTaskName());
        holder.times.setText(String.valueOf(task.getTaskTimes()));     //需将数字转为字符串
        holder.reward.setText(String.valueOf(task.getTaskReward()));
    }

    public int getItemCount(){
        return taskList.size();
    }
    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name;
        TextView times;
        TextView reward;
        public TaskViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.task_name);
            times = itemView.findViewById(R.id.task_times);
            reward = itemView.findViewById(R.id.task_reward);
            itemView.setOnCreateContextMenuListener(this);
        }
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            menu.setHeaderTitle("具体操作");
            menu.add(0,0, this.getAdapterPosition(),"添加");
            menu.add(0,1, this.getAdapterPosition(),"删除");
            menu.add(0,2, this.getAdapterPosition(),"修改");
        }
    }
}