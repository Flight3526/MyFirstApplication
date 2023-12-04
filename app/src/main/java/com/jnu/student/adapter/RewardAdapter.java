package com.jnu.student.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jnu.student.R;
import com.jnu.student.data.RewardItem;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder>{
    private List<RewardItem> rewardList;
    private onItemClickListener clickListener;
    public interface onItemClickListener{          // 设置响应点击事件的接口
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(RewardAdapter.onItemClickListener listener){
        this.clickListener = listener;
    }
    public  RewardAdapter(List<RewardItem> rewardList){this.rewardList = rewardList;}
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(view);
    }
    public void onBindViewHolder(RewardViewHolder holder, int position){
        RewardItem reward = rewardList.get(position);
        holder.name.setText(reward.getRewardName());
        holder.cost.setText("-" + reward.getRewardCost());
        holder.item_layout.setBackgroundColor(0x16A0A0A0);
        if(clickListener != null)
            holder.item_layout.setOnClickListener(view -> clickListener.onItemClick(view, position));
    }
    public int getItemCount(){return rewardList.size();}
    public static class RewardViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name;
        TextView cost;
        LinearLayout item_layout;

        public RewardViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_reward_name);
            cost = itemView.findViewById(R.id.textView_reward_cost);
            item_layout = itemView.findViewById(R.id.reward_item_layout);
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
