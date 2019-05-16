package com.app.boxee.shopper.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class DateTimeUtils {
    public static String parseDateTime(String dateString, String originalFormat, String outputFromat){

        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = formatter.parse(dateString);

            SimpleDateFormat dateFormat=new SimpleDateFormat(outputFromat, new Locale("US"));
            dateFormat.setTimeZone(TimeZone.getDefault());

            return dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
