package com.pain.fleetin.brestblacklist.model;

import android.graphics.Bitmap;

import java.util.Date;

public class ModelCard implements Item{

    private String title;
    private long date;
    private String hashtag;
    private String pictureURL;
    private Bitmap imageBitmap;
    private long timestamp;

    public ModelCard(){
        this.timestamp = new Date().getTime();
    }

    public ModelCard(String title, long date, String hashtag, String pictureURL, Bitmap imageBitmap, long timestamp){
        this.title = title;
        this.date = date;
        this.hashtag = hashtag;
        this.pictureURL = pictureURL;
        //this.imageBitmap = imageBitmap;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
