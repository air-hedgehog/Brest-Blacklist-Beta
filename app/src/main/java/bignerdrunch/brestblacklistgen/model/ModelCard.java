package bignerdrunch.brestblacklistgen.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class ModelCard implements Item{

    private String title;
    private long date;
    private String hashtag;
    private Uri picturePath;
    private Bitmap imageBitmap;

    public ModelCard(){

    }

    public ModelCard(String title, long date, String hashtag, Uri picturePath, Bitmap imageBitmap){
        this.title = title;
        this.date = date;
        this.hashtag = hashtag;
        this.picturePath = picturePath;
        this.imageBitmap = imageBitmap;
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

    public Uri getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(Uri picturePath) {
        this.picturePath = picturePath;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
