package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //不使用layout文件，纯Java代码显示Hello World
        TextView text_view_hello = new TextView(this);
        text_view_hello.setText(R.string.hello_world);
        setContentView(text_view_hello);

        //使用layout文件显示布局
        setContentView(R.layout.activity_main);
        TextView text_view_hello_world = findViewById(R.id.text_view_hello_world);
        text_view_hello_world.setText(R.string.hello_android);
        text_view_hello_world.setTextColor(Color.RED);
        text_view_hello_world.setTextSize(25);
    }
}