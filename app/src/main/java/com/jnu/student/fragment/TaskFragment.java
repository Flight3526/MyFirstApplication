package com.jnu.student.fragment;

import static androidx.core.app.ActivityCompat.invalidateOptionsMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.AddTaskActivity;
import com.jnu.student.R;
import com.jnu.student.TaskAdapter;
import com.jnu.student.data.ScoreData;
import com.jnu.student.data.TaskBank;
import com.jnu.student.data.TaskItem;

import java.util.List;

public class TaskFragment extends Fragment implements TaskAdapter.onItemClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2;
    private ViewPager2 viewPager;
    private final String[] tabHeaderStrings = {"每日任务", "每周任务", "普通任务" };
    public static List<TaskItem> []taskList;
    public static TaskAdapter[] taskAdapter;
    private ActivityResultLauncher<Intent> launcherAdd, launcherModify;
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
        setHasOptionsMenu(true);                               // 用于编辑标题栏图标，设置响应事件
        taskList = new List[3];
        taskAdapter = new TaskAdapter[3];
        for(int i = 0; i < 3; ++i){
            taskList[i] = TaskBank.LoadTaskItems(requireActivity(), i);
            moveTask(taskList[i], i);                          // 将已完成任务移到末尾
            taskAdapter[i] = new TaskAdapter(taskList[i], i);
            taskAdapter[i].setOnItemClickListener(this);       // 回调接口，用于设置item点击响应事件
        }
        launcherSet();
    }
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
    private void moveTask(List<TaskItem> list, int type){           // 将已完成任务移到末尾
        TaskItem firstDone = null;
        for(int i = 0; i < list.size();){
            TaskItem task = list.get(i);
            if(task.isDone()) {
                if (null == firstDone) firstDone = task;
                else if (firstDone == task) break;                  // 已移动所有已完成任务
                list.remove(i); list.add(task);
            }
            else ++i;
        }
        TaskBank.SaveTaskItems(requireActivity(), list, type);
    }
    public void onItemClick(View view, int position, int type){      // 实现recyclerView的item点击响应
        TaskItem task = taskList[type].get(position);
        if(!task.isDone()) {
            task.addCntTimes();
            ScoreData.updateScore(task.getTaskReward());
            invalidateOptionsMenu(getActivity());
            if (!task.isDone()) taskAdapter[type].notifyItemChanged(position);
            else {                         // 将已完成任务添加到末尾
                taskList[type].remove(position);
                taskAdapter[type].notifyItemRemoved(position);
                taskList[type].add(task);
                taskAdapter[type].notifyItemInserted(taskList[type].size());
                taskAdapter[type].notifyItemRangeChanged(position, taskList[type].size() - position);
            }
        }
        TaskBank.SaveTaskItems(requireActivity(), taskList[type], type);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {                 // 设置标题栏
        MenuItem item=menu.findItem(R.id.action_add);
        if(null != item) item.setVisible(true);
    }
    public boolean onOptionsItemSelected(MenuItem item) {         // 标题栏组件点击调用
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent_add = new Intent(requireActivity(), AddTaskActivity.class);
            Bundle bundle_add = new Bundle();
            bundle_add.putInt("type", viewPager.getCurrentItem());
            intent_add.putExtras(bundle_add);
            launcherAdd.launch(intent_add);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void launcherSet(){
        this.launcherAdd = registerForActivityResult(             // 处理添加结果
            new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent_get = result.getData();
                    assert intent_get != null;
                    Bundle bundle_get = intent_get.getExtras(); // 应用result.getData()获取返回的intent
                    assert bundle_get != null;
                    String name = bundle_get.getString("name");
                    int times = bundle_get.getInt("times");
                    int reward = bundle_get.getInt("reward");
                    int type = bundle_get.getInt("type");
                    taskList[type].add(0, new TaskItem(name, times, reward));
                    taskAdapter[type].notifyItemInserted(0);
                    taskAdapter[type].notifyItemRangeChanged(0, taskList[type].size());
                    TaskBank.SaveTaskItems(requireActivity().getApplicationContext(), taskList[type], type);
                    Toast.makeText(requireActivity(), "已添加", Toast.LENGTH_SHORT).show();
                }
                else if(result.getResultCode() == Activity.RESULT_CANCELED){
                    Toast.makeText(requireActivity(), "取消", Toast.LENGTH_SHORT).show();
                }
            }
        );

        this.launcherModify = registerForActivityResult(          // 处理AddTaskActivity返回的修改结果
            new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent_get = result.getData();
                    assert intent_get != null;
                    Bundle bundle_get = intent_get.getExtras();
                    assert bundle_get != null;
                    String name = bundle_get.getString("name");
                    int times = bundle_get.getInt("times");
                    int reward = bundle_get.getInt("reward");
                    int origin_type = bundle_get.getInt("origin_type");
                    int type = bundle_get.getInt("type");
                    int position = bundle_get.getInt("position");
                    TaskItem task = taskList[origin_type].get(position);
                    task.setTaskName(name); task.setTaskNeedTimes(times); task.setTaskReward(reward);
                    if(origin_type == type) {                    // 修改前后任务类型相同
                        taskAdapter[type].notifyItemChanged(position);
                        TaskBank.SaveTaskItems(requireActivity(), taskList[type], type);
                    }
                    else{                                        // 修改前后任务类型不同
                        taskList[origin_type].remove(position);
                        taskAdapter[origin_type].notifyItemRemoved(position);
                        taskAdapter[origin_type].notifyItemRangeChanged(position, taskList[origin_type].size() - position);
                        TaskBank.SaveTaskItems(requireActivity().getApplicationContext(), taskList[origin_type], origin_type);
                        int site = task.isDone() ? taskList[type].size() : 0;     // 插入头部或尾部
                        taskList[type].add(site, task);
                        taskAdapter[type].notifyItemInserted(site);
                        taskAdapter[type].notifyItemRangeChanged(site, taskList[type].size() - site);
                        TaskBank.SaveTaskItems(requireActivity().getApplicationContext(), taskList[type], type);
                    }
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
                Intent intent_add = new Intent(requireActivity(), AddTaskActivity.class);
                Bundle bundle_add = new Bundle();
                bundle_add.putInt("type", viewPager.getCurrentItem());
                intent_add.putExtras(bundle_add);
                launcherAdd.launch(intent_add);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Task").setMessage("你确定要删除该任务吗？");
                builder.setPositiveButton("是的", (dialogInterface, i) -> {
                    int pos = item.getOrder();
                    int type = viewPager.getCurrentItem();
                    taskList[type].remove(pos);
                    taskAdapter[type].notifyItemRemoved(pos);
                    taskAdapter[type].notifyItemRangeChanged(pos, taskList[type].size() - pos);
                    TaskBank.SaveTaskItems(requireActivity(), taskList[type], type);
                    Toast.makeText(requireActivity(), "删除第"+ (pos + 1)+"项", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("取消", (dialogInterface, i) -> {});
                builder.create().show();
                break;
            case 2:
                Intent intent_modify = new Intent(requireActivity(), AddTaskActivity.class);
                Bundle bundle = new Bundle();
                int pos = item.getOrder();
                int type = viewPager.getCurrentItem();
                bundle.putString("name", taskList[type].get(pos).getTaskName());
                bundle.putInt("times", taskList[type].get(pos).getTaskNeedTimes());
                bundle.putInt("reward", taskList[type].get(pos).getTaskReward());
                bundle.putInt("type", type);
                bundle.putInt("position", pos);
                intent_modify.putExtras(bundle);
                launcherModify.launch(intent_modify);
        }
        return true;
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
        Log.i("createFragment", "createFragment: "+position);
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