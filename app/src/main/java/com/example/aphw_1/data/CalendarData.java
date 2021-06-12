package com.example.aphw_1.data;


import com.example.aphw_1.utils.EditorState;
import com.example.aphw_1.utils.FragmentID;

/*
    MainActivity 외부에서도 년/월 정보를 쉽게 불러오기 위하여 기능을 분리
 */
public class CalendarData {

    private static int year = 0; // 년
    private static int month = 0; // 월
    private static int day = 0; // 일
    private static int hour = 0; // 시간
    private static int minute = 0; // 분

    private static int endHour = 0; // 일정 종료 시간
    private static int endMinute = 0; // 일정 종료 분

    private static int position = 0; // 뷰 클릭 포지션
    private static int page = 0; // 페이저 페이지

    private static double lat = 0; // 위도
    private static double lng = 0; // 경도

    private static EditorState editorState = EditorState.INSERT; // INSERT - UPDATE
    private static String id = ""; // database ID

    private static FragmentID fragmentID;

    public static void setDay(int day) {
        CalendarData.day = day;
    }

    public static int getDay() {
        return CalendarData.day;
    }

    public static void setPosition(int position) {
        CalendarData.position = position;
    }

    public static int getPosition() {
        return CalendarData.position;
    }

    public static void setYear(int year) {
        CalendarData.year = year;
    }

    public static int getYear() {
        return CalendarData.year;
    }

    public static void setMonth(int month) {
        CalendarData.month = month;
    }

    public static int getMonth() {
        return CalendarData.month;
    }

    public static void setPage(int page) { CalendarData.page = page; }

    public static int getPage() { return CalendarData.page; }

    public static int getHour() {
        return hour;
    }

    public static void setHour(int hour) {
        CalendarData.hour = hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static void setMinute(int minute) {
        CalendarData.minute = minute;
    }

    public static EditorState getEditorState() {
        return editorState;
    }

    public static void setEditorState(EditorState editorState) {
        CalendarData.editorState = editorState;
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        CalendarData.lat = lat;
    }

    public static double getLng() {
        return lng;
    }

    public static void setLng(double lng) {
        CalendarData.lng = lng;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        CalendarData.id = id;
    }

    public static int getEndHour() {
        return endHour;
    }

    public static void setEndHour(int endHour) {
        CalendarData.endHour = endHour;
    }

    public static int getEndMinute() {
        return endMinute;
    }

    public static void setEndMinute(int endMinute) {
        CalendarData.endMinute = endMinute;
    }

    public static FragmentID getFragmentID() {
        return fragmentID;
    }

    public static void setFragmentID(FragmentID fragmentID) {
        CalendarData.fragmentID = fragmentID;
    }
}
