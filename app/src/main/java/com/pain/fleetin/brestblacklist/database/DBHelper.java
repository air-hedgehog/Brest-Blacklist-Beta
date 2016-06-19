package com.pain.fleetin.brestblacklist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.pain.fleetin.brestblacklist.model.ModelCard;

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "blacklist_database";
    public static final String CRIMES_TABLE = "crimes_table";

    public static final String CRIME_TITLE_COLUMN = "crime_title";
    public static final String CRIME_DATE_COLUMN = "crime_date";
    public static final String CRIME_HASHTAG_COLUMN = "crime_hashtag";
    //public static final String CRIME_IMAGE_BITMAP_COLUMN = "crime_image_bitmap";
    public static final String CRIME_TIME_STAMP_COLUMN = "crime_time_stamp";

    public static final String CRIMES_TABLE_CREATE_SCRIPT = "CREATE TABLE " +
            CRIMES_TABLE + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CRIME_TITLE_COLUMN + " TEXT NOT NULL, " + CRIME_DATE_COLUMN + " LONG, " +
            CRIME_HASHTAG_COLUMN + " TEXT NOT NULL, " +
            CRIME_TIME_STAMP_COLUMN + " LONG);";

    public static final String SELECTION_LIKE_TITLE = CRIME_TITLE_COLUMN + " LIKE ?";

    private DBQueryManager queryManager;
    private DBUpdateManager updateManager;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        queryManager = new DBQueryManager(getReadableDatabase());
        updateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CRIMES_TABLE_CREATE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + CRIMES_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void saveCrime (ModelCard card){
        ContentValues newValues = new ContentValues();

        newValues.put(CRIME_TITLE_COLUMN, card.getTitle());
        newValues.put(CRIME_DATE_COLUMN, card.getDate());
        newValues.put(CRIME_HASHTAG_COLUMN, card.getHashtag());
        //newValues.put(CRIME_IMAGE_BITMAP_COLUMN, card.getImageBitmap());
        newValues.put(CRIME_TIME_STAMP_COLUMN, card.getTimestamp());

        getWritableDatabase().insert(CRIMES_TABLE, null, newValues);
    }

    public DBQueryManager query(){
        return queryManager;
    }

    public DBUpdateManager update(){
        return updateManager;
    }
}
