package com.jnu.student;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.widget.*;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.adapter.MainFragmentAdapter;
import com.jnu.student.data.DataName;
import com.jnu.student.data.DataScore;
import com.jnu.student.data.EventBank;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ViewPager2 viewPager;
    MainFragmentAdapter fragmentAdapter;
    NavigationView navigationView;
    ActivityResultLauncher<Intent> launcher;
    private final String[] tabHeaderStrings = {"任务", "奖励", "统计" };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);                     // 取消默认标题栏
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        int[] drawable = {R.drawable.png_task, R.drawable.png_reward, R.drawable.png_statistics};
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position]).setIcon(drawable[position])).attach();

        Toolbar toolbar_add = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_add);
        toolbar_add.setNavigationIcon(R.drawable.ic_drawer);               // 需放置在setSupportActionBar之后
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);      // 抽屉菜单
        toolbar_add.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),  result -> {});
        navigationView = findViewById(R.id.navigation_view);
        View view_header = navigationView.getHeaderView(0);
        TextView text_view_nickname = view_header.findViewById(R.id.text_view_user_nickname);
        TextView text_view_signature = view_header.findViewById(R.id.text_view_user_signature);
        text_view_nickname.setText(DataName.getNickName());
        text_view_signature.setText(DataName.getSignature());

        navigationView.setNavigationItemSelectedListener(this);            // 设置抽屉菜单项响应

//        View decorView = getWindow().getDecorView();         // 隐藏状态栏
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }

    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item_name){                        // 通过对话框修改昵称、签名
            View view_header = navigationView.getHeaderView(0);
            TextView text_view_nickname = view_header.findViewById(R.id.text_view_user_nickname);
            TextView text_view_signature = view_header.findViewById(R.id.text_view_user_signature);

            View view_modify = LayoutInflater.from(this).inflate(R.layout.motify_name, null);
            EditText edit_view_nickName = view_modify.findViewById(R.id.editText_nickname);
            EditText edit_view_signature = view_modify.findViewById(R.id.editText_signature);
            edit_view_nickName.setText(DataName.getNickName());         // 显示当前昵称
            edit_view_signature.setText(DataName.getSignature());       // 显示当前签名

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("设置昵称、签名").setView(view_modify);
            builder.setPositiveButton("确定", (dialogInterface, i) -> {
                text_view_nickname.setText(edit_view_nickName.getText().toString());
                text_view_signature.setText(edit_view_signature.getText().toString());
                DataName.setNickName(edit_view_nickName.getText().toString());
                DataName.setSignature(edit_view_signature.getText().toString());
            }).setNegativeButton("取消", (dialogInterface, i) -> {}).show();
        }
        else if (id == R.id.item_bill) {                  // 显示账单
            Intent intent = new Intent(this, MyBillActivity.class);
            launcher.launch(intent);
        }
        else if(id == R.id.item_remove_bill){             // 清空账单记录
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("清空收支记录").setMessage("清空收支记录将导致账单和统计数据清除，确定清空吗？");
            builder.setPositiveButton("确定", (dialogInterface, i) -> {
                EventBank.saveEventItems(this, new ArrayList<>());
                Toast.makeText(this, "已清空", Toast.LENGTH_SHORT).show();
            }).setNegativeButton("取消", (dialogInterface, i) -> {});
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);  // 禁止马上确认

            Handler handler = new Handler();
            Runnable runnable = new Runnable(){              // 设置延时，经过一定时间才能确认
                int remainingTime = 3000;                    // 经过3秒才能确认
                final Button button_pos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                @SuppressLint("SetTextI18n")
                public void run() {
                    int seconds = remainingTime / 1000;       // 剩余时间，单位为秒
                    String text = "确定 (" + seconds + "s)";
                    button_pos.setText(text);
                    remainingTime -= 1000;                    // 继续倒计时
                    if (remainingTime >= 0) handler.postDelayed(this, 1000);
                    else {                                    // 倒计时结束时，恢复按钮可用状态
                        button_pos.setEnabled(true);
                        button_pos.setText("确定");
                        button_pos.setTextColor(Color.RED);
                    }
                }
            };
            handler.postDelayed(runnable, 1000);      // 每隔一秒显示剩余时间
        }
        item.setCheckable(false);                               // 取消选中状态
//        drawerLayout.closeDrawer(GravityCompat.START);        //关闭滑动菜单
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {             // 控制标题栏内容显示
        getMenuInflater().inflate(R.menu.menu_add, menu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.actionbar_score);
            TextView otherTextView = actionBar.getCustomView().findViewById(R.id.text_view_score);
            String text = "任务币: " + DataScore.getScore();
            otherTextView.setText(text);
        }
        return true;
    }

    public boolean onContextItemSelected(@NonNull MenuItem item){    //设置菜单项选择响应
        Fragment fragment;
        switch(viewPager.getCurrentItem()){                      // 根据当前fragment选择适当的方法
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


