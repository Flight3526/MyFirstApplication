package com.jnu.student.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.R;
import com.jnu.student.adapter.TaskAdapter;
import com.jnu.student.data.TaskItem;

import java.util.List;

public class TaskDailyFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    List<TaskItem> dailyTaskList;
    TaskAdapter dailyTaskAdapter;
    public TaskDailyFragment() {}

    public static TaskDailyFragment newInstance(String param1, String param2) {
        TaskDailyFragment fragment = new TaskDailyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_task_daily, container, false);
        RecyclerView recycler_view_tasks = rootView.findViewById(R.id.daily_task_view);
        recycler_view_tasks.setLayoutManager(new LinearLayoutManager(requireActivity()));
        dailyTaskAdapter = TaskFragment.taskAdapter[0];
        recycler_view_tasks.setAdapter(dailyTaskAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_divider));
        recycler_view_tasks.addItemDecoration(divider);
        return rootView;
    }
}