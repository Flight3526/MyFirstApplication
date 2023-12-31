package com.jnu.student.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.R;
import com.jnu.student.adapter.StatisticsFragmentAdapter;
import com.jnu.student.MyBillActivity;

public class StatisticsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2;
    private ViewPager2 viewPager;
    private final String[] tabHeaderStrings = {"日", "周", "月", "年" };
    ActivityResultLauncher<Intent> launcher;
    public StatisticsFragment() {}

    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        setHasOptionsMenu(true);
        launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {});
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        viewPager = rootView.findViewById(R.id.viewPager_statistics);
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout_statistics);
        StatisticsFragmentAdapter fragmentAdapter = new StatisticsFragmentAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabHeaderStrings[position])).attach();

        return rootView;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item_add = menu.findItem(R.id.action_add);
        if(null != item_add) item_add.setVisible(false);
        MenuItem item_bill = menu.findItem(R.id.action_bill);
        if(null != item_bill) item_bill.setVisible(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {         // 标题栏组件点击调用
        int id = item.getItemId();
        if (id == R.id.action_bill) {
            Intent intent = new Intent(requireActivity(), MyBillActivity.class);
            launcher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}