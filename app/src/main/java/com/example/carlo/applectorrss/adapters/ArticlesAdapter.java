package com.example.carlo.applectorrss.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlo.applectorrss.R;
import com.example.carlo.applectorrss.model.Article;

import com.squareup.picasso.Picasso;

public class ArticlesAdapter  extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> implements View.OnClickListener {

    private final boolean dualPanel;
    private Article[] articles;
    private View.OnClickListener listener;


    public ArticlesAdapter(Article[] articles, boolean dualPanel) {
        this.articles = articles;
        this.dualPanel = dualPanel;
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_recycler, viewGroup, false);

        itemView.setOnClickListener(this);

        return new ArticlesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder viewHolder, int pos) {
        Article article = articles[pos];
        viewHolder.title.setText(article.getTitle());
        viewHolder.description.setText(article.getDescription());

        if (article.getImage() != null) {
            Picasso.with(viewHolder.imageView.getContext()).load(article.getImage()).into(viewHolder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return articles.length;
    }



    static class ArticlesViewHolder
            extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;
        private TextView description;

        ArticlesViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }


}
