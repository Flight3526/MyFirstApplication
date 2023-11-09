package com.jnu.student.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.*;
import com.jnu.student.data.RewardBank;
import com.jnu.student.data.RewardItem;

import java.io.FileNotFoundException;
import java.util.List;

public class RewardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    List<RewardItem> rewardList;
    RewardAdapter rewardAdapter;

    public RewardFragment() {}

    public static RewardFragment newInstance(String param1, String param2) {
        RewardFragment fragment = new RewardFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        RecyclerView recycler_view_reward = rootView.findViewById(R.id.reward_view);
        recycler_view_reward.setLayoutManager(new LinearLayoutManager(requireActivity()));
        try {
            rewardList = RewardBank.LoadRewardItems(requireActivity().getApplicationContext());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        rewardList.add(new RewardItem("吃饭", ""+20));
        rewardList.add(new RewardItem("睡觉", ""+30));
        rewardAdapter = new RewardAdapter(rewardList);
        recycler_view_reward.setAdapter(rewardAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.divider));
        recycler_view_reward.addItemDecoration(divider);
        launcherSet();
        return rootView;
    }
    void launcherSet(){}
//    public boolean onContextItemSelected(MenuItem item){}
}