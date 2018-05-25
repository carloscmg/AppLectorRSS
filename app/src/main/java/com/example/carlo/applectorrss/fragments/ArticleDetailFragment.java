package com.example.carlo.applectorrss.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlo.applectorrss.R;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_AUTHOR;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_DATE;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_DESCRIPTION;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_IMAGE;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_TITLE;
import static com.example.carlo.applectorrss.activities.ArticleDetail.KEY_URL;


public class ArticleDetailFragment extends Fragment {


    public static ArticleDetailFragment newInstace(String author, String title, String description, String url, String image, Date date) {
        ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_AUTHOR, author);
        args.putString(KEY_TITLE,title);
        args.putString(KEY_DESCRIPTION, description);
        args.putString(KEY_URL, url);
        args.putString(KEY_IMAGE, image);
        args.putString(KEY_DATE, String.valueOf(date));
        articleDetailFragment.setArguments(args);
        return articleDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        String title="";
        String description="";
        String url="";
        String image="";
        if (getArguments() != null) {
            title = (String) getArguments().get(KEY_TITLE);
            description = (String) getArguments().get(KEY_DESCRIPTION);
            url = (String) getArguments().get(KEY_URL);
            image = (String) getArguments().get(KEY_IMAGE);
        }

        setDetailView(view,title,description,url,image);
        return view;

    }

    private void setDetailView(final View view, String title, String description, final String url, String image) {

        ImageView imageDetail;
        TextView titleDetail;
        TextView descriptionDetail;
        Button buttonDetail;

        imageDetail = view.findViewById(R.id.imageDetail);
        titleDetail = view.findViewById(R.id.titleDetail);
        descriptionDetail = view.findViewById(R.id.descriptionDetail);
        buttonDetail = view.findViewById(R.id.buttonDetail);

        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                view.getContext().startActivity(intent);
            }
        });
        titleDetail.setText(title);
        descriptionDetail.setText(description);

        if (image != null) {
            Picasso.with(imageDetail.getContext()).load(image).into(imageDetail);
        }
    }
}
