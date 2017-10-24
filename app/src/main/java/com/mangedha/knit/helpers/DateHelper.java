package com.mangedha.knit.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 4/29/2017.
 */

public class DateHelper {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private static final int WEEK = DAY_MILLIS * 7;

    private static final int MONTH = DAY_MILLIS * 30;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = new Date().getTime();

        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;

        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return "today";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        }else if(diff > (24 * 7)  * WEEK){
            return "week ago";
        }
        else if(diff > (30*24) * MONTH){
            return "month ago";
        }
        else if(diff > (31*24) * MONTH){
            return "more than month";
        }else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static long parse(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
    }

    public static long getTime(String date) throws ParseException {
        if(date == null) return 0;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
    }


    public static String getTimeAgo(String pubDate) {
        try {
            return getTimeAgo(getTime(pubDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {

        }

        return outputDate;

    }

    public static String getHolidayDate(String date){
        return formateDateFromstring("yyyy-MM-dd", "dd MMM", date);
    }

    public static long getHolidayDateTime(String date) throws ParseException {
        if(date == null) return 0;
        return new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
    }

    public static String formatMangedha(long milliseconds){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date = new Date();
        date.setTime(milliseconds);
        return simpleDateFormat.format(date);
    }
}
