package bignerdrunch.brestblacklistgen;

import java.text.SimpleDateFormat;

public class Utils {

    public static final int TAB_BEAUTY = 0;
    public static final int TAB_BUY = 1;
    public static final int TAB_TRANSPORT = 2;
    public static final int TAB_PUB = 3;
    public static final int TAB_FUN = 4;

    public static String getDate(long date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(long time){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(time);
    }
}
