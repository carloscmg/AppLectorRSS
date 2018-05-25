package com.example.carlo.applectorrss.fragments;


import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.carlo.applectorrss.BuildConfig;
import com.example.carlo.applectorrss.R;
import com.example.carlo.applectorrss.activities.ArticleDetail;
import com.example.carlo.applectorrss.adapters.ArticlesAdapter;
import com.example.carlo.applectorrss.database.ArticlesDatabase;
import com.example.carlo.applectorrss.model.Article;
import com.example.carlo.applectorrss.model.Articles;
import com.example.carlo.applectorrss.network.GsonRequest;
import com.example.carlo.applectorrss.network.RequestsManager;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_AUTHOR;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_DATE;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_DESCRIPTION;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_IMAGE;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_TITLE;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_URL;


public class ArticlesListFragment extends Fragment {

    private static final String DB_NAME = "local_news";

    private static ArticlesDatabase db;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Article[] articlesArray;
    boolean dualPanel;
    int currentPosition;

    public ArticlesListFragment newInstace() { return new ArticlesListFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null){
            progressBar = getView().findViewById(R.id.progressBar);
            setupRecyclerView(getView());

            View detailsFrame = getActivity().findViewById(R.id.flArticleDetail);
            dualPanel = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

            doRequest();
        }

    }

    private void doRequest() {

        setLoading(true);




        GsonRequest gsonRequest = new GsonRequest<>(BuildConfig.API_URL, Articles.class, null, new Response.Listener<Articles>() {

            @Override
            public void onResponse(Articles articles) {
                setLoading(false);
                deleteArticles();

                if (articles !=null){
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

                    articlesArray = articles.getArticles();

                    if (articlesArray != null) {

                        for (Article a: articlesArray) {
                            db = Room.databaseBuilder(getActivity(), ArticlesDatabase.class, DB_NAME).build();
                            InsertTask insertTask = new InsertTask();
                            insertTask.execute(a);
                        }
                    }

                    if(dualPanel){
                        showDetail(0);
                    }

                    final ArticlesAdapter adapter = new ArticlesAdapter(articles.getArticles(), dualPanel);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDetail(recyclerView.getChildAdapterPosition(v));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                Log.e("ERROR",error.toString());

                //Persistence
                setLoading(false);

                getAllArticles();

            }
        });

        RequestsManager.getInstance().addToRequestQueue(getActivity(), gsonRequest);
    }

    private void getAllArticles() {

        db = Room.databaseBuilder(getActivity().getApplicationContext(), ArticlesDatabase.class, DB_NAME).build();
        GetArticlesAsyncTask selectAsyncTask = new GetArticlesAsyncTask();
        selectAsyncTask.execute();

    }

    private  class GetArticlesAsyncTask extends AsyncTask<Void, Integer, Article[]> {

        @Override
        protected Article[] doInBackground(Void... voids) {
            return db.ArticlesDao().getAll();
        }

        @Override
        protected void onPostExecute(Article[] articles) {
            super.onPostExecute(articles);

            if (articles !=null){

                //I sort the array even though the data provider already gives me the sorted data.
                Collections.sort(Arrays.asList(articles), new Comparator<Article>() {
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

                articlesArray = articles;



                if(dualPanel){
                    showDetail(0);
                }

                final ArticlesAdapter adapter = new ArticlesAdapter(articles, dualPanel);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDetail(recyclerView.getChildAdapterPosition(v));
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        }
    }





    private static class InsertTask extends AsyncTask<Article, Void, Void> {

        @Override
        protected Void doInBackground(Article... articles) {
            db.ArticlesDao().insert(articles[0]);
            return null;
        }
    }

    private void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }



    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void showDetail(int position) {

        currentPosition = position;

        if (dualPanel) {


            ArticleDetailFragment articleDetailFragment = ArticleDetailFragment.newInstace(articlesArray[position].getAuthor(),
                    articlesArray[position].getTitle(), articlesArray[position].getDescription(), articlesArray[position].getUrl(),
                    articlesArray[position].getImage(), articlesArray[position].getDate() );

            if(getFragmentManager()!=null){
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flArticleDetail, articleDetailFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ArticleDetail.class);
            intent.putExtra(KEY_AUTHOR, articlesArray[position].getAuthor());
            intent.putExtra(KEY_TITLE, articlesArray[position].getTitle());
            intent.putExtra(KEY_DESCRIPTION, articlesArray[position].getDescription());
            intent.putExtra(KEY_URL, articlesArray[position].getUrl());
            intent.putExtra(KEY_IMAGE, articlesArray[position].getImage());
            intent.putExtra(KEY_DATE, articlesArray[position].getDate());
            startActivity(intent);
        }

    }


    private void deleteArticles() {

        db = Room.databaseBuilder(getActivity().getApplicationContext(), ArticlesDatabase.class, DB_NAME).build();
        DeleteNewsAsyncTask selectAsyncTask = new DeleteNewsAsyncTask();
        selectAsyncTask.execute();


    }

    private static class DeleteNewsAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            db.ArticlesDao().deleteAll();

            return null;
        }
    }


}
