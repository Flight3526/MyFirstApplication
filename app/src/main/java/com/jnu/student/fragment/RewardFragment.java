package com.jnu.student.fragment;

import static androidx.core.app.ActivityCompat.invalidateOptionsMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jnu.student.*;
import com.jnu.student.adapter.RewardAdapter;
import com.jnu.student.data.EventBank;
import com.jnu.student.data.RewardBank;
import com.jnu.student.data.RewardItem;
import com.jnu.student.data.DataScore;
import java.util.List;

public class RewardFragment extends Fragment implements RewardAdapter.onItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2;
    List<RewardItem> rewardList;
    RewardAdapter rewardAdapter;
    private ActivityResultLauncher<Intent> launcherTaskAdd, launcherTaskModify;

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
        setHasOptionsMenu(true);
        rewardList = RewardBank.LoadRewardItems(requireActivity());
        rewardAdapter = new RewardAdapter(rewardList);
        rewardAdapter.setOnItemClickListener(this);
        launcherSet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        RecyclerView recycler_view_reward = rootView.findViewById(R.id.reward_view);
        recycler_view_reward.setLayoutManager(new LinearLayoutManager(requireActivity()));
        if(0 == rewardList.size()) {
            rewardList.add(new RewardItem("逛十分钟手机", 30));
            rewardList.add(new RewardItem("读小说", 30));
            rewardList.add(new RewardItem("喝奶茶", 35));
            rewardList.add(new RewardItem("看电影", 50));
            rewardList.add(new RewardItem("购物", 50));
        }
        recycler_view_reward.setAdapter(rewardAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.divider));
        recycler_view_reward.addItemDecoration(divider);
        launcherSet();
        return rootView;
    }
    public void onItemClick(View view, int position){      // 实现recyclerView的item点击响应
        RewardItem reward = rewardList.get(position);
        String rewardName = reward.getRewardName();
        int cost = reward.getRewardCost();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("提示").setMessage("你确定用"+cost+"任务币兑换\""+rewardName+"\"吗?");
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            if(DataScore.updateScore(-reward.getRewardCost()))
                EventBank.addEventItem(requireActivity(), rewardName, -cost);
            invalidateOptionsMenu(getActivity());
        });
        builder.setNegativeButton("取消", (dialogInterface, i) -> {});
        builder.create().show();
    }
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item_add = menu.findItem(R.id.action_add);
        if(null != item_add) item_add.setVisible(true);
        MenuItem item_bill = menu.findItem(R.id.action_bill);
        if(null != item_bill) item_bill.setVisible(false);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent_add = new Intent(requireActivity(), AddRewardActivity.class);
            launcherTaskAdd.launch(intent_add);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void launcherSet(){
        this.launcherTaskAdd = registerForActivityResult(             // 处理添加结果
            new ActivityResultContracts.StartActivityForResult(),     // 使用StartActivityForResult合同
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent_get = result.getData();
                    assert intent_get != null;
                    Bundle bundle_get = intent_get.getExtras();
                    assert bundle_get != null;
                    String name = bundle_get.getString("name");
                    int cost = bundle_get.getInt("cost");
                    rewardList.add(new RewardItem(name, cost));
                    rewardAdapter.notifyItemInserted(rewardList.size());
                    RewardBank.SaveRewardItems(requireActivity().getApplicationContext(), rewardList);
                    Toast.makeText(requireActivity(), "已添加", Toast.LENGTH_SHORT).show();
                }
                else if(result.getResultCode() == Activity.RESULT_CANCELED){
                    Toast.makeText(requireActivity(), "取消", Toast.LENGTH_SHORT).show();
                }
            }
        );

        this.launcherTaskModify = registerForActivityResult(          // 处理AddRewardActivity返回的修改结果
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent_get = result.getData();
                    assert intent_get != null;
                    Bundle bundle_get = intent_get.getExtras();
                    assert bundle_get != null;
                    String name = bundle_get.getString("name");
                    int cost = bundle_get.getInt("cost");
                    int position = bundle_get.getInt("position");
                    RewardItem reward = rewardList.get(position);
                    reward.setRewardName(name); reward.setRewardCost(cost);
                    rewardAdapter.notifyItemChanged(position);
                    RewardBank.SaveRewardItems(requireActivity(), rewardList);
                    Toast.makeText(requireActivity(), "已修改", Toast.LENGTH_SHORT).show();
                }
                else if(result.getResultCode() == Activity.RESULT_CANCELED){
                    Toast.makeText(requireActivity(), "取消", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }
    public boolean onContextItemSelected(MenuItem item){    //设置菜单项选择响应
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case 0:
                Intent intent_add = new Intent(requireActivity(), AddRewardActivity.class);
                launcherTaskAdd.launch(intent_add);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                String rewardName = rewardList.get(item.getOrder()).getRewardName();
                builder.setTitle("Delete Reward").setMessage("你确定要删除\""+rewardName+"\"吗？");
                builder.setPositiveButton("是的", (dialogInterface, i) -> {
                    int pos = item.getOrder();
                    rewardList.remove(pos);
                    rewardAdapter.notifyItemRemoved(pos);
                    rewardAdapter.notifyItemRangeChanged(pos, rewardList.size() - pos);
                    RewardBank.SaveRewardItems(requireActivity(), rewardList);
                    Toast.makeText(requireActivity(), "删除第"+ (pos + 1) +"项", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("取消", (dialogInterface, i) -> {});
                builder.create().show();
                break;
            case 2:
                Intent intent_modify = new Intent(requireActivity(), AddRewardActivity.class);
                Bundle bundle = new Bundle();
                int pos = item.getOrder();
                bundle.putString("name", rewardList.get(pos).getRewardName());
                bundle.putInt("cost", rewardList.get(pos).getRewardCost());
                bundle.putInt("position", pos);
                intent_modify.putExtras(bundle);
                launcherTaskModify.launch(intent_modify);
        }
        return true;
    }
}