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

import com.jnu.student.data.TaskItem;

import java.io.FileNotFoundException;
import java.util.List;
import com.jnu.student.data.*;
import com.jnu.student.*;

public class TaskDailyFragment extends Fragment {

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
        View rootView = inflater.inflate(R.layout.fragment_daily_task, container, false);
        RecyclerView recycler_view_tasks = rootView.findViewById(R.id.daily_task_view);
        recycler_view_tasks.setLayoutManager(new LinearLayoutManager(requireActivity()));
        try {
            dailyTaskList = TaskBank.LoadTaskItems(requireActivity().getApplicationContext(),0);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        dailyTaskList.add(new TaskItem("吃饭", 1,20));
        dailyTaskList.add(new TaskItem("运动", 2,30));
        dailyTaskAdapter = new TaskAdapter(dailyTaskList);
        recycler_view_tasks.setAdapter(dailyTaskAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.divider));
        recycler_view_tasks.addItemDecoration(divider);
        launcherSet();
        return rootView;
    }
    void launcherSet(){
//        this.launcherAdd = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
//                result -> {
//                    if(result.getResultCode() == Activity.RESULT_OK){
//                        Intent intent_get = result.getData();
//                        assert intent_get != null;
//                        Bundle bundle_get = intent_get.getExtras(); // 应用result.getData()获取返回的intent
//                        assert bundle_get != null;
//                        String title = bundle_get.getString("title");
//                        int price = bundle_get.getInt("price");
//                        bookList.add(new BookItem(title, price, R.drawable.book_no_name));
//                        bookAdapter.notifyItemInserted(bookList.size());
//                        if (!bookList.isEmpty())         //list非空时隐藏按钮
//                            button_add_book.setVisibility(View.GONE);
//                        BookBank.SaveBookItems(requireActivity().getApplicationContext(), bookList);
//                        Toast.makeText(requireActivity(), "已添加", Toast.LENGTH_SHORT).show();
//                    }
//                    else if(result.getResultCode() == Activity.RESULT_CANCELED){
//                        Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        this.launcherModify = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
//                result -> {
//                    if(result.getResultCode() == Activity.RESULT_OK){
//                        Intent intent_get = result.getData();
//                        assert intent_get != null;
//                        Bundle bundle_get = intent_get.getExtras();
//                        assert bundle_get != null;
//                        int position = bundle_get.getInt("position");
//                        bookList.get(position).setTitle(bundle_get.getString("title"));
//                        bookList.get(position).setPrice(bundle_get.getInt("price"));
//                        bookAdapter.notifyItemChanged(position);
//                        BookBank.SaveBookItems(requireActivity(), bookList);
//                        Toast.makeText(requireActivity(), "已修改", Toast.LENGTH_SHORT).show();
//                    }
//                    else if(result.getResultCode() == Activity.RESULT_CANCELED){
//                        Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
    }
//    public boolean onContextItemSelected(MenuItem item){    //设置菜单项选择响应
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch(item.getItemId()){
//            case 0:
//                Intent intent_add = new Intent(requireActivity(), BookDetailsActivity.class);
//                launcherAdd.launch(intent_add);
//                break;
//            case 1:
//                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
//                builder.setTitle("Delete Data");
//                builder.setMessage("你确定要删除该项吗？");
//                builder.setPositiveButton("是的", (dialogInterface, i) -> {
//                    int position = item.getOrder();
//                    bookList.remove(position);
//                    bookAdapter.notifyItemRemoved(position);
//                    Toast.makeText(requireActivity(), "删除第"+ (position+1)+"个", Toast.LENGTH_SHORT).show();
//                    if (bookList.isEmpty())
//                        button_add_book.setVisibility(View.VISIBLE); // 显示按钮
//                    BookBank.SaveBookItems(requireActivity(), bookList);
//                });
//                builder.setNegativeButton("取消", (dialogInterface, i) -> {});
//                builder.create().show();
//                break;
//            case 2:
//                Intent intent_modify = new Intent(requireActivity(), BookDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                int pos = item.getOrder();
//                bundle.putString("title", bookList.get(pos).getTitle());
//                bundle.putInt("price", bookList.get(pos).getPrice());
//                bundle.putInt("position", pos);
//                intent_modify.putExtras(bundle);
//                launcherModify.launch(intent_modify);
//                Toast.makeText(requireActivity(), "修改", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
}