package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerviewBook extends AppCompatActivity{
    ActivityResultLauncher<Intent> launcher_add;
    List<BookItem> booklist;
    MyAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_book);
        Log.i("RecyclerviewBook","enter OnCreate");

        RecyclerView recycler_View_books = findViewById(R.id.book_view);
        recycler_View_books.setLayoutManager(new LinearLayoutManager(this));
        booklist = new ArrayList<>();
        for(int i=0;i<1;i++) {
            booklist.add(new BookItem("软件项目管理案例教程（第4版）", "30元", R.drawable.book_2));
            booklist.add(new BookItem("创新工程实践", "25元", R.drawable.book_no_name));
            booklist.add(new BookItem("信息安全教学基础（第2版）", "35元", R.drawable.book_1));
        }
        bookAdapter = new MyAdapter(booklist);
        recycler_View_books.setAdapter(bookAdapter);

        this.launcher_add = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    assert data != null;
                    String title = data.getStringExtra("title");
                    String price = data.getStringExtra("price");
                    booklist.add(new BookItem(title, price, R.drawable.book_1));
                    bookAdapter.notifyItemInserted(booklist.size());
                    Toast.makeText(getApplicationContext(), "已添加", Toast.LENGTH_SHORT).show();
                }
                else if(result.getResultCode() == Activity.RESULT_CANCELED){
                    Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }
    public boolean onContextItemSelected(MenuItem item){
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case 0:
                Intent intent = new Intent(RecyclerviewBook.this, BookAddActivity.class);
                launcher_add.launch(intent);
                break;
            case 1:
                int position = item.getOrder();
                booklist.remove(position);
                bookAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "删除第"+ (position+1)+"个", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "修改", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}