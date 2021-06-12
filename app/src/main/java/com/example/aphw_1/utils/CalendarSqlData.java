package com.example.aphw_1.utils;



import android.provider.BaseColumns;

public final class CalendarSqlData {


    public static final String DB_NAME="Calendar.db";
    public static final int DATABASE_VERSION = 2;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";


    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private CalendarSqlData() {}

    /* Inner class that defines the table contents */
    public static class Calendar implements BaseColumns {

        public static final String TABLE_NAME="Calender";
        public static final String KEY_DATE = "Date";
        public static final String KEY_START_HOUR = "StartHour";
        public static final String KEY_START_MINUTE = "StartMinute";
        public static final String KEY_END_HOUR = "EndHour";
        public static final String KEY_END_MINUTE = "EndMinute";
        public static final String KEY_TITLE = "Title";
        public static final String KEY_LATITUDE = "Latitude";
        public static final String KEY_LONGITUDE = "Longitude";
        public static final String KEY_NOTE = "Note";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_DATE + TEXT_TYPE + COMMA_SEP +
                KEY_START_HOUR + INTEGER_TYPE + COMMA_SEP +
                KEY_START_MINUTE + INTEGER_TYPE + COMMA_SEP +
                KEY_END_HOUR + INTEGER_TYPE + COMMA_SEP +
                KEY_END_MINUTE + INTEGER_TYPE + COMMA_SEP +
                KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_LATITUDE + DOUBLE_TYPE + COMMA_SEP +
                KEY_LONGITUDE + DOUBLE_TYPE + COMMA_SEP +
                KEY_NOTE + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
