package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddRewardActivity extends AppCompatActivity {
    EditText editText_reward_name;
    EditText editText_reward_cost;
    Button button_OK, button_cancel;
    Bundle bundle_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reward);
        editText_reward_name = findViewById(R.id.editText_reward_name);
        editText_reward_cost = findViewById(R.id.editText_reward_cost);
        button_OK = findViewById(R.id.button_reward_OK);
        button_cancel = findViewById(R.id.button_reward_cancel);

        bundle_get = getIntent().getExtras();
        if(bundle_get != null && bundle_get.containsKey("name")){
            editText_reward_name.setText(bundle_get.getString("name"));
            editText_reward_cost.setText("" + bundle_get.getInt("cost"));
        }

        button_cancel.setOnClickListener(view -> {
            Intent intent_result = new Intent();
            setResult(Activity.RESULT_CANCELED, intent_result);
            AddRewardActivity.this.finish();
        });

        button_OK.setOnClickListener(view -> {
            Intent intent_result = new Intent();
            Bundle bundle_result = new Bundle();
            String temp;

            if(!(temp = editText_reward_name.getText().toString()).equals(""))
                bundle_result.putString("name", temp);
            else bundle_result.putString("name", "未命名");

            if(!(temp = editText_reward_cost.getText().toString()).equals(""))
                bundle_result.putInt("cost", Integer.parseInt(temp));
            else bundle_result.putInt("cost", 20);

            if(bundle_get != null && bundle_get.containsKey("position"))
                bundle_result.putInt("position", bundle_get.getInt("position"));

            intent_result.putExtras(bundle_result);
            setResult(Activity.RESULT_OK, intent_result);
            AddRewardActivity.this.finish();
        });
    }
}