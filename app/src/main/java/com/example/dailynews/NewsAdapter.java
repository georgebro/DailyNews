package com.example.dailynews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    private LayoutInflater layoutInflater;
    private Object LinearLayout;

    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> newsArrayList) {
        super(context, 0, newsArrayList);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Inflate the layout:
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.item_news, null);

        // Layout's inner components:
        TextView textTitle = linearLayout.findViewById(R.id.textViewTitle);
        TextView textDate = linearLayout.findViewById(R.id.textViewDate);

        // Fill them with values:
        News news = getItem(position);
        textTitle.setText(news.getTitle());
        textDate.setText(news.getPubDate());
        return linearLayout;
    }
}