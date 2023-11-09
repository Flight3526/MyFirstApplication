package com.jnu.student;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.fragment.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//    private TextView text_view_greet;
//    private TextView text_view_hello;
//    private TextView text_view_jnu;
    ActivityResultLauncher<Intent> launcher;
    private final String[] tabHeaderStrings = {"任务", "奖励", "统计" };
//    private WebView webView;
    ViewPager2 viewPager;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        MyFragmentAdapter fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position])).attach();
//        reduceDragSensitivity(viewPager);
//
//        text_view_greet = findViewById(R.id.text_view_hello_world);        // 根据ID获取响应对象
//        text_view_greet.setText(R.string.hello_android);                          // 修改原先内容
//        text_view_greet.setTextColor(Color.RED);                           // 修改字体颜色
//        text_view_greet.setTextSize(30);                                   // 修改字体大小
//        text_view_hello = findViewById(R.id.text_view_hello);
//        text_view_hello.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);      // 设置文字居中
//        text_view_hello.setTextColor(Color.BLUE);
//        text_view_hello.setTextSize(30);
//        text_view_jnu = findViewById(R.id.text_view_jnu);
//        text_view_jnu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        text_view_jnu.setTextColor(Color.GREEN);
//        text_view_jnu.setTextSize(30);
//
//        launcher = registerForActivityResult(                     // 创建ActivityResultLauncher启动器对象
//            new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
//            result -> {}
//        );
//
//        Button button_change_text = findViewById(R.id.button_change_text);
//        button_change_text.setOnClickListener(this);
//
//        Button button_change_color = findViewById(R.id.button_change_color);
//        button_change_color.setOnClickListener(view -> {       // 设置监听器, 使三个TextView交换颜色
//            Button button_change = (Button)view;
//            button_change.setBackgroundColor(text_view_hello.getCurrentTextColor());
//            int tempColor1 = text_view_greet.getCurrentTextColor();
//            int tempColor2 = text_view_jnu.getCurrentTextColor();
//            text_view_greet.setTextColor(text_view_hello.getCurrentTextColor());
//            text_view_jnu.setTextColor(tempColor1); text_view_hello.setTextColor(tempColor2);
//        });
//
//        Button button_show_recycler = findViewById(R.id.button_show_recyclerview);
//        button_show_recycler.setOnClickListener(view ->
//            launcher.launch(new Intent(MainActivity.this, RecyclerviewBook.class))                             //启动Activity
//        );
//
//        Button button_show_horizon = findViewById(R.id.button_show_horizon);
//        button_show_horizon.setOnClickListener(view ->
//            launcher.launch(new Intent(MainActivity.this, HorizonScrollActivity.class))                             //启动Activity
//        );
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
}


class MyFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;
    public MyFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置返回对应的Fragment实例
        switch (position) {
            case 0:
                return new TaskFragment();
            case 1:
                return new RewardFragment();
            case 2:
                return new StatisticsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
