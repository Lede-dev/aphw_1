
package com.example.aphw_1.utils;

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
        super(context, CalendarSqlData.DB_NAME, null, CalendarSqlData.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(CalendarSqlData.Calendar.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(CalendarSqlData.Calendar.DELETE_TABLE);
        onCreate(db);
    }
    /*

    // insert data
    public void insertCalendarDataBySQL(String date, String title, String location, String note) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (NULL, '%s', '%s', '%s', '%s')",

                    CalendarSqlData.Calendar.TABLE_NAME,
                    CalendarSqlData.Calendar._ID,
                    CalendarSqlData.Calendar.KEY_DATE,
                    CalendarSqlData.Calendar.KEY_TITLE,
                    CalendarSqlData.Calendar.KEY_LOCATION,
                    CalendarSqlData.Calendar.KEY_NOTE,

                    date,
                    title,
                    location,
                    note);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    // delete data
    public void deleteCalendarDataBySQL(String id) {
        try {
            String sql = String.format(
                    "DELETE FROM %s WHERE %S = %S",
                    CalendarSqlData.Calendar.TABLE_NAME,
                    CalendarSqlData.Calendar._ID,
                    id);

            getWritableDatabase().execSQL(sql);
        }
        catch (SQLException e) {
            Log.e(TAG, "Error in deleting recodes");
        }
    }

    // update data
    public void updateCalendarDataBySQL(String id, String date, String title, String location, String note) {
        try {
            String sql = String.format(
                    "UPDATE %s SET %s = '%s', %s = '%s', %s = '%s', %s = '%s' WHERE %s = '%s'",
                    CalendarSqlData.Calendar.TABLE_NAME,
                    CalendarSqlData.Calendar.KEY_DATE, date,
                    CalendarSqlData.Calendar.KEY_TITLE, title,
                    CalendarSqlData.Calendar.KEY_LOCATION, location,
                    CalendarSqlData.Calendar.KEY_NOTE, note,
                    CalendarSqlData.Calendar._ID, id);

            getWritableDatabase().execSQL(sql);
        }
        catch (SQLException e) {
            Log.e(TAG, "Error in updating recodes");
        }
    }

     */

    // get All data
    public Cursor getAllCalendarDataBySQL() {
        String sql = "SELECT * FROM " + CalendarSqlData.Calendar.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor getDataMatched(String search, String value) {
        String sql = "SELECT * FROM " + CalendarSqlData.Calendar.TABLE_NAME + " WHERE " + search + "='" + value + "'";
        //String sql = String.format(String.format("SELECT * FROM %s WHERE %s='%s'",CalendarSqlData.Calendar.TABLE_NAME, search, value));
        return getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor getDataMatched(String search, String search2, String value, String value2) {
        String sql = "SELECT * FROM " + CalendarSqlData.Calendar.TABLE_NAME + " WHERE " + search + "='" + value + "' AND " + search2 + "='" + value2 + "'";
        //String sql = String.format(String.format("SELECT * FROM %s WHERE %s='%s'",CalendarSqlData.Calendar.TABLE_NAME, search, value));
        return getReadableDatabase().rawQuery(sql, null);
    }

    // insert data
    public long insertCalendarData(String date, int startHour, int startMinute, int endHour, int endMinute, String title, double lat, double lng, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CalendarSqlData.Calendar.KEY_DATE, date);
        values.put(CalendarSqlData.Calendar.KEY_START_HOUR, startHour);
        values.put(CalendarSqlData.Calendar.KEY_START_MINUTE, startMinute);
        values.put(CalendarSqlData.Calendar.KEY_END_HOUR, endHour);
        values.put(CalendarSqlData.Calendar.KEY_END_MINUTE, endMinute);
        values.put(CalendarSqlData.Calendar.KEY_TITLE, title);
        values.put(CalendarSqlData.Calendar.KEY_LATITUDE, lat);
        values.put(CalendarSqlData.Calendar.KEY_LONGITUDE, lng);
        values.put(CalendarSqlData.Calendar.KEY_NOTE, note);

        return db.insert(CalendarSqlData.Calendar.TABLE_NAME, null, values);
    }

    // delete data
    public long deleteCalendarData(String id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = CalendarSqlData.Calendar._ID + " = ?";
        String[] whereArgs = { id };

        return db.delete(CalendarSqlData.Calendar.TABLE_NAME, whereClause, whereArgs);
    }

    // update data
    public long updateCalendarData(String id, String date, int startHour, int startMinute, int endHour, int endMinute, String title, double lat, double lng, String note) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CalendarSqlData.Calendar.KEY_DATE, date);
        values.put(CalendarSqlData.Calendar.KEY_START_HOUR, startHour);
        values.put(CalendarSqlData.Calendar.KEY_START_MINUTE, startMinute);
        values.put(CalendarSqlData.Calendar.KEY_END_HOUR, endHour);
        values.put(CalendarSqlData.Calendar.KEY_END_MINUTE, endMinute);
        values.put(CalendarSqlData.Calendar.KEY_TITLE, title);
        values.put(CalendarSqlData.Calendar.KEY_LATITUDE, lat);
        values.put(CalendarSqlData.Calendar.KEY_LONGITUDE, lng);
        values.put(CalendarSqlData.Calendar.KEY_NOTE, note);

        String whereClause = CalendarSqlData.Calendar._ID + " = ?";
        String[] whereArgs = { id };

        return db.update(CalendarSqlData.Calendar.TABLE_NAME, values, whereClause, whereArgs);
    }

    // query data
    public Cursor getAllCalendarData() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(CalendarSqlData.Calendar.TABLE_NAME, null, null, null, null, null, null);
    }

}

