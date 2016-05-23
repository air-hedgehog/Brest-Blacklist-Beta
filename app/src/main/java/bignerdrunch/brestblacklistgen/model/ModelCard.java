package bignerdrunch.brestblacklistgen.model;

public class ModelCard implements Item{

    private String title;
    private long date;
    private String hashtag;

    public ModelCard(){

    }

    public ModelCard(String title, long date, String hashtag){
        this.title = title;
        this.date = date;
        this.hashtag = hashtag;
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

    @Override
    public boolean isCrime() {
        return true;
    }
}
