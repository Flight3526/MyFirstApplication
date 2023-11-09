package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText taskName;
    EditText taskTimes;
    EditText taskReward;
    Spinner taskType;
    Button button_OK;
    Bundle bundle_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskType = (Spinner)findViewById(R.id.spinner_type);
        String[]arr = {"每日任务", "每周任务", "普通任务"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        taskType.setAdapter(adapter);
        taskType.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent){

    }
}