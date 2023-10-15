package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class BookAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        Button button_add = findViewById(R.id.button_add);
        button_add.setOnClickListener(view -> {
            Intent intent = new Intent();
            EditText book_title = findViewById(R.id.book_title);
            EditText book_price = findViewById(R.id.book_price);
            intent.putExtra("title",book_title.getText().toString());
            intent.putExtra("price",book_price.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            BookAddActivity.this.finish();
        });
    }
}