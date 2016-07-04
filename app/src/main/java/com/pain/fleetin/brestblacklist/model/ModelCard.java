package com.pain.fleetin.brestblacklist.model;

public class ModelCard implements Item{

    private String title;
    private long date;
    private String hashtag;
    private String pictureURL;
    private long postId;

    public ModelCard(){

    }

    public ModelCard(String title, long date, String hashtag, String pictureURL, long postId){
        this.title = title;
        this.date = date;
        this.hashtag = hashtag;
        this.pictureURL = pictureURL;
        this.postId = postId;
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

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
