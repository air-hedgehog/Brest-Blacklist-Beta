package bignerdrunch.brestblacklistgen.model;

public class ModelCrime implements Item{

    private String title;
    private long date;

    public ModelCrime() {

    }

    public ModelCrime(String title, long date){
        this.title = title;
        this.date = date;
    }

    @Override
    public boolean isCrime() {
        return true;
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
}
