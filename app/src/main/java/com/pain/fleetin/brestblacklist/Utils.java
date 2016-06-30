package com.pain.fleetin.brestblacklist;

import java.text.SimpleDateFormat;

public class Utils {

    public static final int FLEETIN_PAIN = 71924797;
    public static final int BREST_BLACKLIST = 84025643;
    public static final int CLOSED_NONAME = 122565629;

    public static String getDate(long date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(long time){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(time);
    }

    public static String getFullDate(long fullDate){
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yy  HH:mm");
        return fullDateFormat.format(fullDate);
    }

}
