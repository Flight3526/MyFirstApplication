package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class BookDetailsActivity extends AppCompatActivity {
    Button button_OK;
    Button button_cancel;
    EditText edit_book_title;
    EditText edit_book_price;
    Bundle bundle_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        button_OK = findViewById(R.id.button_OK);
        button_cancel = findViewById(R.id.button_cancel);
        edit_book_title = findViewById(R.id.book_title);
        edit_book_price = findViewById(R.id.book_price);

        bundle_get = getIntent().getExtras();
        if(bundle_get != null && bundle_get.containsKey("title") && bundle_get.containsKey("price")) {
            edit_book_title.setText(bundle_get.getString("title"));
            edit_book_price.setText(String.valueOf(bundle_get.getInt("price")));
        }

        button_OK.setOnClickListener(view -> {
            Intent intent_result = new Intent();
            Bundle bundle_result = new Bundle();
            bundle_result.putString("title", edit_book_title.getText().toString());
            String price;
            if(!(price = edit_book_price.getText().toString()).equals(""))
                bundle_result.putInt("price", Integer.parseInt(price));
            if(bundle_get != null && bundle_get.containsKey("position"))
                bundle_result.putInt("position", bundle_get.getInt("position"));
            intent_result.putExtras(bundle_result);
            setResult(Activity.RESULT_OK, intent_result);
            BookDetailsActivity.this.finish();
        });

        button_cancel.setOnClickListener(view->{
            setResult(Activity.RESULT_CANCELED, new Intent());
            BookDetailsActivity.this.finish();
        });

    }
}