package com.jnu.student.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.R;
import com.jnu.student.TaskAdapter;
import com.jnu.student.data.TaskBank;
import com.jnu.student.data.TaskItem;

import java.io.FileNotFoundException;
import java.util.List;

public class TaskNormalFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    List<TaskItem> normalTaskList;
    TaskAdapter normalTaskAdapter;
    public TaskNormalFragment() {}

    public static TaskNormalFragment newInstance(String param1, String param2) {
        TaskNormalFragment fragment = new TaskNormalFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_normal_task, container, false);
        RecyclerView recycler_view_tasks = rootView.findViewById(R.id.normal_task_view);
        recycler_view_tasks.setLayoutManager(new LinearLayoutManager(requireActivity()));
        normalTaskAdapter = TaskFragment.taskAdapter[2];
        recycler_view_tasks.setAdapter(normalTaskAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.divider));
        recycler_view_tasks.addItemDecoration(divider);
        return rootView;
    }
//    public boolean onContextItemSelected(MenuItem item){}
}