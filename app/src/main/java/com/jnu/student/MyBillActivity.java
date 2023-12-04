package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.jnu.student.adapter.BillAdapter;
import com.jnu.student.data.EventItem;
import com.jnu.student.data.EventBank;

import java.io.FileNotFoundException;
import java.util.List;

public class MyBillActivity extends AppCompatActivity {   // 显示账单信息
    List<EventItem> eventList;
    BillAdapter billAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        RecyclerView recycler_view_bill = findViewById(R.id.my_bill);
        recycler_view_bill.setLayoutManager(new LinearLayoutManager(this));
        try {
            eventList = EventBank.loadEventItems(this.getApplicationContext());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TextView text_view_no_value = findViewById(R.id.textView_no_value);
        if(eventList.isEmpty()) text_view_no_value.setVisibility(View.VISIBLE);
        billAdapter = new BillAdapter(eventList);
        recycler_view_bill.setAdapter(billAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recycler_view_bill.addItemDecoration(divider);

        Toolbar toolbar = (Toolbar)findViewById(R.id.bill_toolbar);
        toolbar.setTitle("我的账单");
        setSupportActionBar(toolbar);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {         // 标题栏组件点击调用
        int id = item.getItemId();
        if (id == R.id.action_return) {
            setResult(Activity.RESULT_OK, new Intent());
            MyBillActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}