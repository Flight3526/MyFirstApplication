package com.jnu.student;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jnu.student.data.RewardItem;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder>{
    List<RewardItem> rewardList;
    public  RewardAdapter(List<RewardItem> rewardList){this.rewardList = rewardList;}
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(view);
    }
    public void onBindViewHolder(RewardViewHolder holder, int position){
        RewardItem reward = rewardList.get(position);
        holder.name.setText(reward.getRewardName());
        holder.cost.setText(reward.getRewardCost());
    }
    public int getItemCount(){return rewardList.size();}
    public static class RewardViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name;
        TextView cost;
        public RewardViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_reward_name);
            cost = itemView.findViewById(R.id.textView_reward_cost);
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
