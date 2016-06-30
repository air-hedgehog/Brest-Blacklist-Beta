package com.pain.fleetin.brestblacklist.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.pain.fleetin.brestblacklist.model.ModelCard;

public class DBUpdateManager {

    SQLiteDatabase database;

    DBUpdateManager (SQLiteDatabase database){
        this.database = database;
    }

    public void title (long timestamp, String title){
        update(DBHelper.CRIME_TITLE_COLUMN, timestamp, title);
    }

    public void date (long timestamp, long date){
        update(DBHelper.CRIME_DATE_COLUMN, timestamp, date);
    }

    public void hashtag (long timestamp, String hashtag){
        update(DBHelper.CRIME_HASHTAG_COLUMN, timestamp, hashtag);
    }

    public void imageURL(long timestamp, String imageURL){
        update(DBHelper.CRIME_PICTURE_URL_COLUMN, timestamp, imageURL);
    }

    public void postId (long timestamp, long postId){
        update(DBHelper.CRIME_POST_ID_COLUMN, timestamp, postId);
    }

    public void card (ModelCard card){
        title(card.getTimestamp(), card.getTitle());
        date(card.getTimestamp(), card.getDate());
        hashtag(card.getTimestamp(), card.getHashtag());
        imageURL(card.getTimestamp(), card.getPictureURL());
        postId(card.getTimestamp(), card.getPostId());
    }

    private void update(String column, long key, String value){
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.CRIMES_TABLE, cv, DBHelper.CRIME_TIME_STAMP_COLUMN + " = " + key, null);
    }

    private void update (String column, long key, long value){
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.CRIMES_TABLE, cv, DBHelper.CRIME_TIME_STAMP_COLUMN + " = " + key, null);
    }

}
