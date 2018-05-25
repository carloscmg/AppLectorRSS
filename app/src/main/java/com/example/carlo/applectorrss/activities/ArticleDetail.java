package com.example.carlo.applectorrss.activities;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carlo.applectorrss.R;
import com.example.carlo.applectorrss.fragments.ArticleDetailFragment;

import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class ArticleDetail extends AppCompatActivity {

    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DATE = "date";

    private android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        setContentView(R.layout.activity_article_detail);


        if (savedInstanceState == null) {
            ArticleDetailFragment articleDetailFragment = ArticleDetailFragment.newInstace((String) getIntent().getExtras().get(KEY_AUTHOR),
                    (String) getIntent().getExtras().get(KEY_TITLE), (String) getIntent().getExtras().get(KEY_DESCRIPTION), (String) getIntent().getExtras().get(KEY_URL),
                    (String) getIntent().getExtras().get(KEY_IMAGE), (Date) getIntent().getExtras().get(KEY_DATE));

            if(getFragmentManager()!=null){

                    android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.detailContent, articleDetailFragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();

            }

        }

    }
}
