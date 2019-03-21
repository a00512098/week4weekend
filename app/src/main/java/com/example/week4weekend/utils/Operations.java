package com.example.week4weekend.utils;

import android.util.Log;

import com.example.week4weekend.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Operations {

    // This method convert the current temperature to other scale
    public static  float changeScale(boolean isCelsius, float currentTemp) {
        if (isCelsius) {
             return (currentTemp * 9/5) + 32;
        } else {
            return (currentTemp - 32) * 5/9;
        }
    }

    public static String formatScale(boolean isCelsius, float currentTemp) {
        String format = isCelsius ? "%.2f °F" : "%.2f °C";
        return String.format(format, currentTemp);
    }

    public static String getDayOfTheWeek(String date) throws ParseException {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(formatter.parse(date));
        return CommonConstants.getDayOfTheWeek(c.get(Calendar.DAY_OF_WEEK));
    }

    public static String getHour(String date) throws ParseException {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(formatter.parse(date));
        Log.d("Log.d", c.get(Calendar.HOUR) + " " + c.get(Calendar.AM_PM));
        StringBuilder builder = new StringBuilder();
        builder.append(c.get(Calendar.HOUR_OF_DAY));
        if (builder.length() == 1) {
            builder.insert(0, 0);
        }
        builder.append(c.get(Calendar.AM_PM) == 0 ? " AM" : " PM");
        return builder.toString();
    }
}
