package com.jnu.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView text_view_hello;
    private TextView text_view_jnu;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text_view_hello_world;
//        //不使用layout文件，纯Java代码显示Hello World
//        TextView text_view_hello = new TextView(this);
//        text_view_hello.setText(R.string.hello_world);
//        setContentView(text_view_hello);

        //使用layout文件显示布局
        setContentView(R.layout.activity_main);

        text_view_hello_world = findViewById(R.id.text_view_hello_world);  //根据ID获取响应对象
        text_view_hello_world.setText(R.string.hello_android);       //修改原先内容
        text_view_hello_world.setTextColor(Color.RED);               //修改字体颜色
        text_view_hello_world.setTextSize(25);                       //修改字体大小

        text_view_hello = findViewById(R.id.text_view_hello);
        text_view_hello.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);   //设置文字居中
        text_view_hello.setTextColor(Color.BLUE);
        text_view_hello.setTextSize(25);
        text_view_jnu = findViewById(R.id.text_view_jnu);
        text_view_jnu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text_view_jnu.setTextColor(Color.GREEN);
        text_view_jnu.setTextSize(25);

        Button button_change_text = findViewById(R.id.button_change_text);
        button_change_text.setOnClickListener(this);

        Button button_change_color = findViewById(R.id.button_change_color);
        button_change_color.setOnClickListener(view -> {
            Button button_change = (Button)view;
            button_change.setBackgroundColor(Color.YELLOW);
            int tempColor = text_view_hello.getCurrentTextColor();
            text_view_hello.setTextColor(text_view_jnu.getCurrentTextColor());
            text_view_jnu.setTextColor(tempColor);
            button_change.setBackgroundColor(text_view_jnu.getCurrentTextColor());
        });
    }

    public void onClick(View view){                         //交换TextView文本并显示Toast和对话框
        CharSequence tempText = text_view_hello.getText();
        text_view_hello.setText(text_view_jnu.getText());
        text_view_jnu.setText(tempText);
        Toast.makeText(getApplicationContext(), R.string.change_success, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result").setMessage(R.string.change_success)
                .setPositiveButton("确定", (dialog, id) -> {})
                .setNegativeButton("取消", (dialog, id) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();                                      //显示对话框
    }
}