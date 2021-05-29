
package com.example.aphw_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, CalendarData.DB_NAME, null, CalendarData.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(CalendarData.Calendar.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(CalendarData.Calendar.DELETE_TABLE);
        onCreate(db);
    }



    public void insertUserBySQL(String Date, String Title, String Location, String Note) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (NULL, '%s', '%s', '%s', '%s')",

                    CalendarData.Calendar.TABLE_NAME,
                    CalendarData.Calendar._ID,
                    CalendarData.Calendar.KEY_DATE,
                    CalendarData.Calendar.KEY_TITLE,
                    CalendarData.Calendar.KEY_LOCATION,
                    CalendarData.Calendar.KEY_NOTE,

                    Date,
                    Title,
                    Location,
                    Note);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }




}

