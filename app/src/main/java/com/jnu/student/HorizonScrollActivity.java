package com.jnu.student;

import static android.view.KeyEvent.KEYCODE_BACK;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.fragment.*;
import java.lang.reflect.Field;
import com.jnu.student.fragment.WebViewFragment;


public class HorizonScrollActivity extends AppCompatActivity {

    private final String[] tabHeaderStrings = {"Book Items", "Map", "Baidu" };
    private WebView webView;
    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizon_scroll);
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position])).attach();
        reduceDragSensitivity(viewPager);
    }

    public static void reduceDragSensitivity(ViewPager2 viewPager2) {     // 降低viewPager2的fragment切换灵敏度
        try {
            Field recyclerViewField = ViewPager2.class.getDeclaredField("mRecyclerView");
            recyclerViewField.setAccessible(true);
            RecyclerView recyclerView = (RecyclerView) recyclerViewField.get(viewPager2);

            Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop");
            touchSlopField.setAccessible(true);
            int touchSlop = (int) touchSlopField.get(recyclerView);

            touchSlopField.set(recyclerView, touchSlop * 8);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {     // 处理KeyDown事件
        webView = WebViewFragment.webView;
        if (keyCode == KEYCODE_BACK && webView!=null && webView.canGoBack() && 2 == viewPager.getCurrentItem()) {
            webView.goBack();                                   // 返回上一网页
            return true;
        }
        return super.onKeyDown(keyCode, event);                 // 返回MainActivity
    }
}


class FragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 3;
    public FragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置返回对应的Fragment实例
        switch (position) {
            case 0:
                return new BookFragment();
            case 1:
                return new MapFragment();
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
