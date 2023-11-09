package com.jnu.student.data;

import java.io.Serializable;
public class RewardItem implements Serializable {
    private String rewardName;
    private String rewardCost;
    public RewardItem(String name, String cost){
        rewardName = name;
        rewardCost = cost;
    }

    public String getRewardName() {
        return rewardName;
    }

    public String getRewardCost() {
        return rewardCost;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public void setRewardCost(String rewardCost) {
        this.rewardCost = rewardCost;
    }
}
