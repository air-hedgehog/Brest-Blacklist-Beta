package com.pain.fleetin.brestblacklist.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.pain.fleetin.brestblacklist.model.ModelCard;

public class DBUpdateManager {

    SQLiteDatabase database;

    DBUpdateManager (SQLiteDatabase database){
        this.database = database;
    }

    public void title (long date, String title){
        update(DBHelper.CRIME_TITLE_COLUMN, date, title);
    }

    public void hashtag (long date, String hashtag){
        update(DBHelper.CRIME_HASHTAG_COLUMN, date, hashtag);
    }

    public void imageURL(long date, String imageURL){
        update(DBHelper.CRIME_PICTURE_URL_COLUMN, date, imageURL);
    }

    public void postId (long date, long postId){
        update(DBHelper.CRIME_POST_ID_COLUMN, date, postId);
    }

    public void card (ModelCard card){
        title(card.getDate(), card.getTitle());
        hashtag(card.getDate(), card.getHashtag());
        imageURL(card.getDate(), card.getPictureURL());
        postId(card.getDate(), card.getPostId());
    }

    private void update(String column, long key, String value){
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.CRIMES_TABLE, cv, DBHelper.CRIME_DATE_COLUMN + " = " + key, null);
    }

    private void update (String column, long key, long value){
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.CRIMES_TABLE, cv, DBHelper.CRIME_DATE_COLUMN + " = " + key, null);
    }

}
