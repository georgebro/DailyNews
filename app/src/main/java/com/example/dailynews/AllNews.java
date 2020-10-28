package com.example.dailynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dailynews.DetailsNewsActivity;
import com.example.dailynews.News;
import com.example.dailynews.NewsAdapter;
import com.example.dailynews.R;
import com.example.dailynews.TextDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllNews extends AppCompatActivity implements TextDownloader.Callbacks,  AdapterView.OnItemClickListener{

    private ListView listViewNews;
    private NewsAdapter newsAdapter;
    private ArrayList<News> YnetNewsArray = new ArrayList<>();
    private static String link = "http://10.0.2.2:8080/api/rssnews";

    public static String getLink() {
        return link;
    }
    public static void setLink(String link) {
        AllNews.link = link;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        listViewNews = findViewById(R.id.listViewNews);
        listViewNews.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        Intent intent = getIntent();
        getDataFromWeb();
    }

    public void getDataFromWeb() {
        TextDownloader textDownloader = new TextDownloader(this);
        textDownloader.execute(link);
        Toast.makeText(this, "Starting", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String downloadedText) {
        try {
            YnetNewsArray = newsFilling(downloadedText);
            newsAdapter = new NewsAdapter(this, YnetNewsArray);
            listViewNews.setAdapter(newsAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "ERROR!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<News> newsFilling(String json) throws JSONException {
        ArrayList<News> news = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong("id");
            String title = jsonObject.getString("title");
            String author = jsonObject.getString("author");
            String link = jsonObject.getString("link");
            String pubDate = jsonObject.getString("pubDate");
            String description = jsonObject.getString("description");
            News newsList = new News(id, title, author, link, pubDate, description);
            news.add(newsList);
        }
        return news;
    }

    @Override
    public void onError(int httpStatusCode, String errorMessage) {
        Toast.makeText(this, httpStatusCode + ", " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onAboutToBegin() {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(this, DetailsNewsActivity.class);
        intent.putExtra("news",YnetNewsArray.get(position));
        startActivity(intent);
    }
}