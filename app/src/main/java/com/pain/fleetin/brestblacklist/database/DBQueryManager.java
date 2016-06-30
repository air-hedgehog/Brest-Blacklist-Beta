package com.pain.fleetin.brestblacklist.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pain.fleetin.brestblacklist.model.ModelCard;

import java.util.ArrayList;
import java.util.List;

public class DBQueryManager {

    private SQLiteDatabase database;

    DBQueryManager (SQLiteDatabase database){
        this.database = database;
    }

    public List<ModelCard> getCrimes(String selection, String[] selectionArgs, String orderBy){
        List<ModelCard> crimes = new ArrayList<>();

        Cursor c = database.query(DBHelper.CRIMES_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if (c.moveToFirst()){
            do {
                String title = c.getString(c.getColumnIndex(DBHelper.CRIME_TITLE_COLUMN));
                long date = c.getLong(c.getColumnIndex(DBHelper.CRIME_DATE_COLUMN));
                String hashtag = c.getString(c.getColumnIndex(DBHelper.CRIME_HASHTAG_COLUMN));
                String pictureURL = c.getString(c.getColumnIndex(DBHelper.CRIME_PICTURE_URL_COLUMN));
                long timestamp = c.getLong(c.getColumnIndex(DBHelper.CRIME_TIME_STAMP_COLUMN));
                long postId = c.getInt(c.getColumnIndex(DBHelper.CRIME_POST_ID_COLUMN));

                ModelCard card = new ModelCard(title, date, hashtag, pictureURL, timestamp, postId);
                crimes.add(card);
            } while (c.moveToNext());
        }
        c.close();
        return  crimes;
    }

    public List<ModelCard> getCrimes(String orderBy){
        List<ModelCard> crimes = new ArrayList<>();

        Cursor c = database.query(DBHelper.CRIMES_TABLE, null, null, null, null, null, orderBy);

        if (c.moveToFirst()){
            do {
                String title = c.getString(c.getColumnIndex(DBHelper.CRIME_TITLE_COLUMN));
                long date = c.getLong(c.getColumnIndex(DBHelper.CRIME_DATE_COLUMN));
                String hashtag = c.getString(c.getColumnIndex(DBHelper.CRIME_HASHTAG_COLUMN));
                String pictureURL = c.getString(c.getColumnIndex(DBHelper.CRIME_PICTURE_URL_COLUMN));
                long timestamp = c.getLong(c.getColumnIndex(DBHelper.CRIME_TIME_STAMP_COLUMN));
                long postId = c.getInt(c.getColumnIndex(DBHelper.CRIME_POST_ID_COLUMN));

                ModelCard card = new ModelCard(title, date, hashtag, pictureURL, timestamp, postId);
                crimes.add(card);
            } while (c.moveToNext());
        }
        c.close();
        return  crimes;
    }

}
