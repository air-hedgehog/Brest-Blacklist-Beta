package com.pain.fleetin.brestblacklist.model;

import java.util.Date;

public class ModelCard implements Item{

    private String title;
    private long date;
    private String hashtag;
    //private Uri pictureUri;
    //private Bitmap imageBitmap;
    private long timestamp;

    public ModelCard(){
        this.timestamp = new Date().getTime();
    }

    public ModelCard(String title, long date, String hashtag, /*Uri pictureUri, Bitmap imageBitmap,*/ long timestamp){
        this.title = title;
        this.date = date;
        this.hashtag = hashtag;
        //this.pictureUri = pictureUri;
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

    /*public Uri getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(Uri pictureUri) {
        this.pictureUri = pictureUri;
    }*/

    /*public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
*/
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
