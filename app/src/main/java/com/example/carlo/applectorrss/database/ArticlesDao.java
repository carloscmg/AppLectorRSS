package com.example.carlo.applectorrss.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.carlo.applectorrss.model.Article;

@Dao
public interface ArticlesDao {

    @Query("SELECT * FROM Article")
    Article[] getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Query("DELETE FROM Article")
    void deleteAll();
}
