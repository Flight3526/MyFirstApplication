package com.jnu.student;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jnu.student.data.TaskItem;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskItem> taskList;
    public int type;
    private onItemClickListener   clickListener;
    public interface onItemClickListener{          // 设置响应点击事件的接口
        void onItemClick(View view, int position, int type);
    }
    public void setOnItemClickListener(TaskAdapter.onItemClickListener listener){
        this.clickListener = listener;
    }
    public TaskAdapter(List<TaskItem> tasklist, int type){
        this.taskList = tasklist;
        this.type = type;
    }
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    public void onBindViewHolder(TaskViewHolder holder, int position){
        TaskItem task = taskList.get(position);
        holder.name.setText(task.getTaskName());
        holder.times.setText(task.getTaskCntTimes() + " / " + task.getTaskNeedTimes());
        holder.reward.setText("+" + task.getTaskReward());
        holder.icon.setImageResource(task.isDone()?R.drawable.done:R.drawable.undone);
        holder.item_layout.setBackgroundColor(0x16A0A0A0);
        if(clickListener != null)
            holder.item_layout.setOnClickListener(view -> clickListener.onItemClick(view, position, type));
    }

    public int getItemCount(){ return taskList.size();}

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name;
        TextView times;
        TextView reward;
        ImageView icon;
        LinearLayout item_layout;

        public TaskViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.task_name);
            times = itemView.findViewById(R.id.task_times);
            reward = itemView.findViewById(R.id.task_reward);
            icon = itemView.findViewById(R.id.imageView_done);
            item_layout = itemView.findViewById(R.id.item_layout);
            itemView.setOnCreateContextMenuListener(this);
        }
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            menu.setHeaderTitle("具体操作");
            menu.add(0,0, this.getAdapterPosition(),"添加任务");
            menu.add(0,1, this.getAdapterPosition(),"删除任务");
            menu.add(0,2, this.getAdapterPosition(),"修改任务");
        }
    }
}