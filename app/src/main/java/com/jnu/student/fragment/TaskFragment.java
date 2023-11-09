package com.jnu.student.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.R;

public class TaskFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ViewPager2 viewPager;
    private final String[] tabHeaderStrings = {"每日任务", "每周任务", "普通任务" };
    public TaskFragment() {}
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        viewPager = rootView.findViewById(R.id.viewPager_task);
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout_task);
        TaskFragmentAdapter fragmentAdapter = new TaskFragmentAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position])).attach();
        return rootView;
    }
}

class TaskFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;
    public TaskFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.i("TAG1", "createFragment: "+position);
        switch (position) {
            case 0:
                return new TaskDailyFragment();
            case 1:
                return new TaskWeeklyFragment();
            case 2:
                return new TaskNormalFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}