package com.example.aphw_1;



import android.provider.BaseColumns;

public final class CalendarData {


    public static final String DB_NAME="Calendar.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private CalendarData() {}

    /* Inner class that defines the table contents */
    public static class Calendar implements BaseColumns {


        public static final String TABLE_NAME="Calender";
        public static final String KEY_DATE = "Date";
        public static final String KEY_TITLE = "Title";
        public static final String KEY_LOCATION = "Location";
        public static final String KEY_NOTE = "Note";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_DATE + TEXT_TYPE + COMMA_SEP +
                KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_LOCATION + TEXT_TYPE + COMMA_SEP +
                KEY_NOTE + TEXT_TYPE + COMMA_SEP + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }
}
