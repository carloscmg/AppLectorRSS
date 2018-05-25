package com.example.carlo.applectorrss.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.carlo.applectorrss.database.DateConverter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Article {



    @SerializedName("author")
    private String author;


    @NonNull
    @PrimaryKey
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String image;


    @TypeConverters(DateConverter.class)
    @SerializedName("publishedAt")
    private Date date;

    public Article( String author, String title, String description, String url, String image, Date date) {

        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.image = image;
        this.date = date;
    }




    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
