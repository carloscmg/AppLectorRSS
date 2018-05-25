package com.example.carlo.applectorrss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.carlo.applectorrss.adapters.ArticlesAdapter;
import com.example.carlo.applectorrss.model.Article;
import com.example.carlo.applectorrss.model.Articles;
import com.example.carlo.applectorrss.network.GsonRequest;
import com.example.carlo.applectorrss.network.RequestsManager;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        setupRecyclerView();
        doRequest();
    }



    private void doRequest() {

        setLoading(true);


        GsonRequest gsonRequest = new GsonRequest<>(BuildConfig.API_URL, Articles.class, null, new Response.Listener<Articles>() {

            @Override
            public void onResponse(Articles articles) {
                setLoading(false);


                //I sort the array even though the data provider already gives me the sorted data.
                Collections.sort(Arrays.asList(articles.getArticles()), new Comparator<Article>() {
                    @Override
                    public int compare(Article article1, Article article2) {
                        Calendar eventDate1 = Calendar.getInstance();
                        Calendar eventDate2 = Calendar.getInstance();
                        eventDate1.setTime(article1.getDate());
                        eventDate2.setTime(article2.getDate());

                        int month1 = eventDate1.get(Calendar.MONTH);
                        int month2 = eventDate2.get(Calendar.MONTH);
                        if (month1 != month2) return Integer.compare(month1, month2);
                        int day1 = eventDate1.get(Calendar.DATE);
                        int day2 = eventDate2.get(Calendar.DATE);
                        return Integer.compare(day1, day2);
                    }
                });


                ArticlesAdapter adapter = new ArticlesAdapter(MainActivity.this, articles);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                Log.e("ERROR",error.toString());
            }
        });

        RequestsManager.getInstance().addToRequestQueue(this, gsonRequest);
    }

    private void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
