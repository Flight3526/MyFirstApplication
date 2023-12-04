package com.jnu.student.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.jnu.student.fragment.StatisticsDailyFragment;
import com.jnu.student.fragment.StatisticsMonthlyFragment;
import com.jnu.student.fragment.StatisticsWeeklyFragment;
import com.jnu.student.fragment.StatisticsYearlyFragment;

public class StatisticsFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 4;

    public StatisticsFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    public Fragment createFragment(int position) {
        Log.i("createStatisticsFragment", "createStatisticsFragment: " + position);
        switch (position) {
            case 0:
                return new StatisticsDailyFragment();
            case 1:
                return new StatisticsWeeklyFragment();
            case 2:
                return new StatisticsMonthlyFragment();
            default:
                return new StatisticsYearlyFragment();
        }
    }
    public int getItemCount() {
        return NUM_TABS;
    }
}
