package com.jnu.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.fragment.*;

public class HorizonScrollActivity extends AppCompatActivity {

    private final String[] tabHeaderStrings = {"Book Items", "News", "Baidu" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizon_scroll);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position])).attach();
    }
}


class FragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;
    public FragmentAdapter( FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        // 根据位置返回对应的Fragment实例
        switch (position) {
            case 0:
                return new BookFragment();
            case 1:
                return new WebViewFragment();
            case 2:
                return new WebViewFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
