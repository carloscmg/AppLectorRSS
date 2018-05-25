package com.example.carlo.applectorrss.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.carlo.applectorrss.model.Article;

@Database(entities = {Article.class},version = 1, exportSchema = false)
public abstract class ArticlesDatabase extends RoomDatabase {
    public abstract ArticlesDao ArticlesDao();
}
