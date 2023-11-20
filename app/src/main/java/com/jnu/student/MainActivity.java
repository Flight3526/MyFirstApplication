package com.jnu.student;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import android.view.Window;
import android.widget.*;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.data.RewardBank;
import com.jnu.student.data.ScoreData;
import com.jnu.student.fragment.*;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager2 viewPager;
    MyFragmentAdapter fragmentAdapter;
    private final String[] tabHeaderStrings = {"任务", "奖励", "统计" };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        ScoreData.loadScore();
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        int[] drawable = {R.drawable.task, R.drawable.reward, R.drawable.statistics};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position]).setIcon(drawable[position])).attach();

        Toolbar toolbar_add = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_add);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.score_menu);
            TextView otherTextView = actionBar.getCustomView().findViewById(R.id.other_text_view);
            otherTextView.setText("Score: " + ScoreData.getScore());
        }
        return true;
    }
    public void onClick(View view){                         // 交换TextView文本并显示Toast和对话框
//        CharSequence tempText = text_view_hello.getText();
//        text_view_hello.setText(text_view_jnu.getText());
//        text_view_jnu.setText(tempText);
//        Toast.makeText(getApplicationContext(), R.string.change_success, Toast.LENGTH_SHORT).show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Result").setMessage(R.string.change_success)
//                .setPositiveButton("确定", (dialog, id) -> {})
//                .setNegativeButton("取消", (dialog, id) -> {});
//        AlertDialog dialog = builder.create();
//        dialog.show();                                      // 显示对话框
    }
    public boolean onContextItemSelected(MenuItem item){    //设置菜单项选择响应
        Fragment fragment;
        switch(viewPager.getCurrentItem()){                // 根据当前fragment选择适当的方法
            case 0:
                fragment = fragmentAdapter.taskFragment;
                if(null != fragment) fragment.onContextItemSelected(item);
                break;
            case 1:
                fragment = fragmentAdapter.rewardFragment;
                if(null != fragment) fragment.onContextItemSelected(item);
                break;
            default:
                break;
        }
        return true;
    }
}


class MyFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;
    TaskFragment taskFragment;
    RewardFragment rewardFragment;
    StatisticsFragment statisticsFragment;
    public MyFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
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
