package com.jnu.student;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import android.view.Window;
import android.widget.*;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.adapter.MainFragmentAdapter;
import com.jnu.student.data.DataScore;


public class MainActivity extends AppCompatActivity{
    ViewPager2 viewPager;
    MainFragmentAdapter fragmentAdapter;
    private final String[] tabHeaderStrings = {"任务", "奖励", "统计" };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        int[] drawable = {R.drawable.task, R.drawable.reward, R.drawable.statistics};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position]).setIcon(drawable[position])).attach();

        Toolbar toolbar_add = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_add);
        toolbar_add.setNavigationIcon(R.drawable.ic_drawer);     // 需放置在setSupportActionBar之后
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        toolbar_add.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),  result -> {});
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.item_bill) {
                Intent intent = new Intent(this, MyBillActivity.class);
                launcher.launch(intent);
            }
            item.setCheckable(false);   // 取消选中状态
//            drawerLayout.closeDrawer(GravityCompat.START);   //关闭滑动菜单
            return true;
        });

//        View decorView = getWindow().getDecorView();      // 隐藏状态栏
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_score);
            TextView otherTextView = actionBar.getCustomView().findViewById(R.id.text_view_score);
            otherTextView.setText("任务币: " + DataScore.getScore());
        }
        return true;
    }

    public boolean onContextItemSelected(MenuItem item){    //设置菜单项选择响应
        Fragment fragment;
        switch(viewPager.getCurrentItem()){                 // 根据当前fragment选择适当的方法
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


