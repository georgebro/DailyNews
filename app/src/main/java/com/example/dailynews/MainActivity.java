package com.example.dailynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getNews(View view) {
        AllNews.setLink("http://10.0.2.2:8080/api/rssnews");
        Intent intent = new Intent(this,AllNews.class);
        startActivity(intent);
    }

    public void getByAuthor(View view) {
        EditText editByAuthor = (EditText) findViewById(R.id.editByAuthor);
        String authorName = editByAuthor.getText().toString();

        if(TextUtils.isEmpty(authorName)){
            editByAuthor.setError("You did not enter a author");
            return;
        }

        AllNews.setLink("http://10.0.2.2:8080/api/rssnews"+"/"+authorName);
        Intent intent = new Intent(this,AllNews.class);
        startActivity(intent);
    }
}
