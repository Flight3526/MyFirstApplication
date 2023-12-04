package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText edit_task_name;
    EditText edit_task_times;
    EditText edit_task_reward;
    Spinner spinner_task_type;
    Button button_OK, button_cancel;
    Bundle bundle_get;
    String taskType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        edit_task_name = findViewById(R.id.editText_name);
        edit_task_times = findViewById(R.id.editText_times);
        edit_task_reward = findViewById(R.id.editText_reward);
        spinner_task_type = (Spinner)findViewById(R.id.spinner_type);
        button_OK = findViewById(R.id.button_task_OK);
        button_cancel = findViewById(R.id.button_task_cancel);
        String[] arr = {"每日任务", "每周任务", "普通任务"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, arr);
        spinner_task_type.setAdapter(adapter);
        spinner_task_type.setOnItemSelectedListener(this);
//        spinner_task_type.setGravity(View.TEXT_ALIGNMENT_CENTER);

        bundle_get = getIntent().getExtras();
        if(bundle_get != null && bundle_get.containsKey("name")){
            edit_task_name.setText(bundle_get.getString("name"));
            edit_task_times.setText(String.valueOf(bundle_get.getInt("times")));
            edit_task_reward.setText(String.valueOf(bundle_get.getInt("reward")));
        }
        if(bundle_get != null && bundle_get.containsKey("type"))
            spinner_task_type.setSelection(bundle_get.getInt("type"));

        button_cancel.setOnClickListener(view -> {
            Intent intent_result = new Intent();
            setResult(Activity.RESULT_CANCELED, intent_result);
            AddTaskActivity.this.finish();
        });

        button_OK.setOnClickListener(view -> {
            Intent intent_result = new Intent();
            Bundle bundle_result = new Bundle();
            String temp;

            if(!(temp = edit_task_name.getText().toString()).equals(""))
                bundle_result.putString("name", temp);
            else bundle_result.putString("name", "未命名任务");

            if(!(temp = edit_task_times.getText().toString()).equals("") && !temp.equals("0"))
                bundle_result.putInt("times", Integer.parseInt(temp));
            else bundle_result.putInt("times", 1);

            if(!(temp = edit_task_reward.getText().toString()).equals(""))
                bundle_result.putInt("reward", Integer.parseInt(temp));
            else bundle_result.putInt("reward", 10);

            bundle_result.putInt("type", spinner_task_type.getSelectedItemPosition());
            if(bundle_get != null && bundle_get.containsKey("type"))
                bundle_result.putInt("origin_type", bundle_get.getInt("type"));
            if(bundle_get != null && bundle_get.containsKey("position"))
                bundle_result.putInt("position", bundle_get.getInt("position"));

            intent_result.putExtras(bundle_result);
            setResult(Activity.RESULT_OK, intent_result);
            AddTaskActivity.this.finish();
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        taskType = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> parent){}
}