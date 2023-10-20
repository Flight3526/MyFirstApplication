package com.jnu.student;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView text_view_greet;
    private TextView text_view_hello;
    private TextView text_view_jnu;
    ActivityResultLauncher<Intent> launcher;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //不使用layout文件，纯Java代码显示Hello World
//        TextView text_view_hello = new TextView(this);
//        text_view_hello.setText(R.string.hello_world);
//        setContentView(text_view_hello);

        //使用layout文件显示布局
        setContentView(R.layout.activity_main);

        text_view_greet = findViewById(R.id.text_view_hello_world);        // 根据ID获取响应对象
        text_view_greet.setText(R.string.hello_android);                          // 修改原先内容
        text_view_greet.setTextColor(Color.RED);                           // 修改字体颜色
        text_view_greet.setTextSize(30);                                   // 修改字体大小
        text_view_hello = findViewById(R.id.text_view_hello);
        text_view_hello.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);      // 设置文字居中
        text_view_hello.setTextColor(Color.BLUE);
        text_view_hello.setTextSize(30);
        text_view_jnu = findViewById(R.id.text_view_jnu);
        text_view_jnu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text_view_jnu.setTextColor(Color.GREEN);
        text_view_jnu.setTextSize(30);

        launcher = registerForActivityResult(                     // 创建ActivityResultLauncher启动器对象
            new ActivityResultContracts.StartActivityForResult(), // 使用StartActivityForResult合同
            result -> {}
        );

        Button button_change_text = findViewById(R.id.button_change_text);
        button_change_text.setOnClickListener(this);

        Button button_change_color = findViewById(R.id.button_change_color);
        button_change_color.setOnClickListener(view -> {       // 设置监听器, 使三个TextView交换颜色
            Button button_change = (Button)view;
            button_change.setBackgroundColor(text_view_hello.getCurrentTextColor());
            int tempColor1 = text_view_greet.getCurrentTextColor();
            int tempColor2 = text_view_jnu.getCurrentTextColor();
            text_view_greet.setTextColor(text_view_hello.getCurrentTextColor());
            text_view_jnu.setTextColor(tempColor1); text_view_hello.setTextColor(tempColor2);
        });

        Button button_show_recycler = findViewById(R.id.button_show_recyclerview);
        button_show_recycler.setOnClickListener(view ->
            launcher.launch(new Intent(MainActivity.this, RecyclerviewBook.class))                             //启动Activity
        );
    }

    public void onClick(View view){                         // 交换TextView文本并显示Toast和对话框
        CharSequence tempText = text_view_hello.getText();
        text_view_hello.setText(text_view_jnu.getText());
        text_view_jnu.setText(tempText);
        Toast.makeText(getApplicationContext(), R.string.change_success, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result").setMessage(R.string.change_success)
                .setPositiveButton("确定", (dialog, id) -> {})
                .setNegativeButton("取消", (dialog, id) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();                                      // 显示对话框
    }
}