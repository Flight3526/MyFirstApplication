package com.jnu.student.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jnu.student.fragment.TaskDailyFragment;
import com.jnu.student.fragment.TaskNormalFragment;
import com.jnu.student.fragment.TaskWeeklyFragment;

public class TaskFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;

    public TaskFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.i("createTaskFragment", "createTaskFragment: " + position);
        switch (position) {
            case 0:
                return new TaskDailyFragment();
            case 1:
                return new TaskWeeklyFragment();
            default:
                return new TaskNormalFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
