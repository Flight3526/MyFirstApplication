package com.jnu.student.data;

import java.io.Serializable;
public class RewardItem implements Serializable {
    private String rewardName;
    private int rewardCost;
    public RewardItem(String name, int cost){
        rewardName = name;
        rewardCost = cost;
    }

    public String getRewardName() {
        return rewardName;
    }

    public int getRewardCost() {
        return rewardCost;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public void setRewardCost(int rewardCost) {
        this.rewardCost = rewardCost;
    }
}
