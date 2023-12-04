package com.jnu.student.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jnu.student.fragment.RewardFragment;
import com.jnu.student.fragment.StatisticsFragment;
import com.jnu.student.fragment.TaskFragment;

public class MainFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;
    public TaskFragment taskFragment;
    public RewardFragment rewardFragment;
    public StatisticsFragment statisticsFragment;

    public MainFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        taskFragment = new TaskFragment();
        rewardFragment = new RewardFragment();
        statisticsFragment = new StatisticsFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置返回对应的Fragment实例
        switch (position) {
            case 0:
                return taskFragment;
            case 1:
                return rewardFragment;
            case 2:
                return statisticsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
