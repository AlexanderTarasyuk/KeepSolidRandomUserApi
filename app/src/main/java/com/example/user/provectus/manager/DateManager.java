package com.example.user.provectus.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

    public String getRightDate(String date_val) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format1.parse(date_val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format2.format(date);
    }
}
