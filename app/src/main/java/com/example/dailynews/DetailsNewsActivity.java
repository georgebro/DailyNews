package com.example.dailynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsNewsActivity extends AppCompatActivity {

    private TextView textViewId;
    private TextView textViewDate;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewByAuthor;
    private TextView textViewLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewByAuthor = findViewById(R.id.textViewByAuthor);
        textViewLink = findViewById(R.id.textViewLink);

        Intent intent = getIntent();
        News news = (News) intent.getSerializableExtra("news");
        textViewDate.setText(news.getPubDate());
        textViewTitle.setText(news.getTitle());
        textViewDescription.setText(news.getDescription());
        textViewByAuthor.setText(news.getAuthor());
        textViewLink.setText(news.getLink());
    }
}
