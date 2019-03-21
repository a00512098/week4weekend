package com.example.week4weekend.utils;

import java.util.HashMap;

public class CommonConstants {
    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    public static final String ZIP_CODE_PREFERENCES = "ZIP_CODE_PREFERENCES";
    public static final String ZIP_CODE_BUNDLE = "zipCode";
    public static final int SHARED_ZIP_DEFAULT = -1;
    public static final String ON_BOARDING_COUNT = "ON_BOARDING_COUNT";
    public static final int ON_BOARDING_COUNT_DEFAULT = 0;

    private static final HashMap<Integer, String> DAYS_OF_THE_WEEK = new HashMap<Integer, String>();

    public static String getDayOfTheWeek(int num) {
        String day;
        if (!DAYS_OF_THE_WEEK.isEmpty()) {
            day = DAYS_OF_THE_WEEK.get(num);
        } else {
            day = populateDaysOfTheWeek(num);
        }

        return day;
    }

    private static String populateDaysOfTheWeek(int day) {
        DAYS_OF_THE_WEEK.put(1, "Sunday");
        DAYS_OF_THE_WEEK.put(2, "Monday");
        DAYS_OF_THE_WEEK.put(3, "Tuesday");
        DAYS_OF_THE_WEEK.put(4, "Wednesday");
        DAYS_OF_THE_WEEK.put(5, "Thursday");
        DAYS_OF_THE_WEEK.put(6, "Friday");
        DAYS_OF_THE_WEEK.put(7, "Saturday");

        return DAYS_OF_THE_WEEK.get(day);
    }
}
