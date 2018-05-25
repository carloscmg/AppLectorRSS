package com.example.carlo.applectorrss.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlo.applectorrss.R;
import com.example.carlo.applectorrss.model.Article;
import com.example.carlo.applectorrss.model.Articles;
import com.squareup.picasso.Picasso;

public class ArticlesAdapter  extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private Context context;
    private Articles articles;


    public ArticlesAdapter(Context context, Articles articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_recycler, viewGroup, false);

        return new ArticlesViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder viewHolder, int pos) {
        Article article = articles.getArticles()[pos];
        viewHolder.bindArticle(article);
    }

    @Override
    public int getItemCount() {
        return articles.getArticles().length;
    }

    public static class ArticlesViewHolder
            extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;
        private TextView description;
        private Context vcontext;

        public ArticlesViewHolder(Context context, View itemView) {
            super(itemView);

            vcontext = context;
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

        public void bindArticle(Article article) {
            title.setText(article.getTitle());
            description.setText(article.getDescription());

            if (article.getImage() != null) {
                Picasso.with(vcontext).load(article.getImage()).into(imageView);
            }
        }
    }
}
