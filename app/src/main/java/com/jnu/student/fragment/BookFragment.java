package com.jnu.student.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jnu.student.BookDetailsActivity;
import com.jnu.student.MyAdapter;
import com.jnu.student.R;
import com.jnu.student.data.BookBank;
import com.jnu.student.data.BookItem;

import java.io.FileNotFoundException;
import java.util.List;

public class BookFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ActivityResultLauncher<Intent> launcherAdd;
    ActivityResultLauncher<Intent> launcherModify;
    List<BookItem> bookList;
    MyAdapter bookAdapter;
    Button button_add_book;

    public BookFragment() {
        // Required empty public constructor
    }

    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);

        RecyclerView recycler_View_books = rootView.findViewById(R.id.book_view);

        recycler_View_books.setLayoutManager(new LinearLayoutManager(requireActivity()));
        try {
            bookList = BookBank.LoadBookItems(requireActivity().getApplicationContext());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(0 == bookList.size()){
            bookList.add(new BookItem("软件项目管理案例教程（第4版）", 30, R.drawable.book_2));
            bookList.add(new BookItem("创新工程实践", 25, R.drawable.book_no_name));
            bookList.add(new BookItem("信息安全教学基础（第2版）", 35, R.drawable.book_1));
        }
        bookAdapter = new MyAdapter(bookList);
        recycler_View_books.setAdapter(bookAdapter);
        launcherSet();    //设置launcher

        button_add_book = rootView.findViewById(R.id.button_add_book);
        button_add_book.setOnClickListener(view -> {
            Intent intent_add = new Intent(requireActivity(), BookDetailsActivity.class);
            launcherAdd.launch(intent_add);
        });
        button_add_book.setVisibility(View.GONE);

        Button button_return = rootView.findViewById(R.id.button_return);
        button_return.setOnClickListener(view -> requireActivity().finish());

        return rootView;
    }
    void launcherSet(){
        this.launcherAdd = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent_get = result.getData();
                        assert intent_get != null;
                        Bundle bundle_get = intent_get.getExtras(); // 应用result.getData()获取返回的intent
                        assert bundle_get != null;
                        String title = bundle_get.getString("title");
                        int price = bundle_get.getInt("price");
                        bookList.add(new BookItem(title, price, R.drawable.book_no_name));
                        bookAdapter.notifyItemInserted(bookList.size());
                        if (!bookList.isEmpty())         //list非空时隐藏按钮
                            button_add_book.setVisibility(View.GONE);
                        BookBank.SaveBookItems(requireActivity().getApplicationContext(), bookList);
                        Toast.makeText(requireActivity(), "已添加", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED){
                        Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        this.launcherModify = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent_get = result.getData();
                        assert intent_get != null;
                        Bundle bundle_get = intent_get.getExtras();
                        assert bundle_get != null;
                        int position = bundle_get.getInt("position");
                        bookList.get(position).setTitle(bundle_get.getString("title"));
                        bookList.get(position).setPrice(bundle_get.getInt("price"));
                        bookAdapter.notifyItemChanged(position);
                        BookBank.SaveBookItems(requireActivity(), bookList);
                        Toast.makeText(requireActivity(), "已修改", Toast.LENGTH_SHORT).show();
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED){
                        Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public boolean onContextItemSelected(MenuItem item){    //设置菜单项选择响应
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case 0:
                Intent intent_add = new Intent(requireActivity(), BookDetailsActivity.class);
                launcherAdd.launch(intent_add);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("你确定要删除该项吗？");
                builder.setPositiveButton("是的", (dialogInterface, i) -> {
                    int position = item.getOrder();
                    bookList.remove(position);
                    bookAdapter.notifyItemRemoved(position);
                    Toast.makeText(requireActivity(), "删除第"+ (position+1)+"个", Toast.LENGTH_SHORT).show();
                    if (bookList.isEmpty())
                        button_add_book.setVisibility(View.VISIBLE); // 显示按钮
                    BookBank.SaveBookItems(requireActivity(), bookList);
                });
                builder.setNegativeButton("取消", (dialogInterface, i) -> {});
                builder.create().show();
                break;
            case 2:
                Intent intent_modify = new Intent(requireActivity(), BookDetailsActivity.class);
                Bundle bundle = new Bundle();
                int pos = item.getOrder();
                bundle.putString("title", bookList.get(pos).getTitle());
                bundle.putInt("price", bookList.get(pos).getPrice());
                bundle.putInt("position", pos);
                intent_modify.putExtras(bundle);
                launcherModify.launch(intent_modify);
                Toast.makeText(requireActivity(), "修改", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}