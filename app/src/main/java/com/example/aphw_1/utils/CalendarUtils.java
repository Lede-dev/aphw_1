package com.example.aphw_1.utils;

import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.aphw_1.MainActivity;
import com.example.aphw_1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarUtils {

    // 캘린더 출력 오차
    private static int e;

    /**
     * 입력한 년도가 윤년인지 판단, 윤년이라면 true를 반환한다.
     *
     * @param year
     * @return boolean
     */

    public static Boolean isLeapYear(Integer year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) return true;
        return false;
    }

    /**
     * 입력한 년도, 입력한 월의 첫번째 날자의 요일을 반환.
     *
     * @param year
     * @param month 0 ~ 11
     * @return int 1 ~ 7 (일 ~ 토)
     */

    public static Integer getFirstDay(int year, int month) {
        Calendar cal = java.util.Calendar.getInstance(); // 새로운 Calendar 객체 생성
        cal.set(Calendar.YEAR, year); // 입력받은 년도를 Calendar 객체에 입력
        cal.set(Calendar.MONTH, month); // 입력받은 월을 Calendar 객체에 입력
        cal.set(Calendar.DATE, 1); // 입력받은 년도, 월의 첫번째 일을 Calendar 객체에 입력
        return cal.get(Calendar.DAY_OF_WEEK); // 입력받은 월의 첫번째 일의 요일을 반환
    }

    /**
     * 입력한 년도, 입력한 월의 마지막 날자의 요일을 반환
     *
     * @param year
     * @param month 0 ~ 11
     * @return int 1 ~ 7 (일 ~ 토)
     */

    public static Integer getLastDay(int year, int month) {
        Calendar cal = java.util.Calendar.getInstance(); // 새로운 Calendar 객체 생성
        cal.set(Calendar.YEAR, year); // 입력받은 년도를 Calendar 객체에 입력
        cal.set(Calendar.MONTH, month); // 입력받은 월을 Calendar 객체에 입력
        cal.set(Calendar.DATE, getDay(year, month)); // 입력받은 년도, 월의 마지막 일을 Calendar 객체에 입력
        return cal.get(Calendar.DAY_OF_WEEK); // 입력받은 월의 마지막 일의 요일을 반환
    }

    /**
     * 입력한 년도, 입력한 월, 입력한 일의 요일을 반환
     * 
     * @param year
     * @param month 0 ~ 11
     * @param day 1 ~ 31
     * @return int 1 ~ 7 (일 ~ 토)
     */
    public static Integer getDayOfWeek(int year, int month, int day){
        Calendar cal = Calendar.getInstance(); // 새로운 Calendar 객체 생성
        cal.set(Calendar.YEAR, year); // 입력받은 년도를 Calendar 객체에 입력
        cal.set(Calendar.MONTH, month); // 입력받은 월을 Calendar 객체에 입력
        cal.set(Calendar.DATE, day); // 입력받은 일을 Calendar 객체에 입력
        return cal.get(Calendar.DAY_OF_WEEK); // 입력받은 일의 요일을 반환
    }

    /**
     * 입력한 년도, 입력한 월이 몇 일 까지 존재하는지 반환
     *
     * @param year
     * @param month 0 ~ 11
     * @return int 28, 29, 30, 31
     */
    public static Integer getDay(int year, int month) {
        if (month == 3 || month == 5 || month == 8 || month == 10) return 30; // 4, 6, 9, 11 : 30일
        if (month == 1) { // 2월 윤년 : 29일, 2월 윤년X : 28일
            if (isLeapYear(year)) return 29;
            return 28;
        }
        return 31; // 나머지 31일
    }

    /**
     * 7 x 7 크기의 GridView에 출력할 날자가 포함된 배열을 생성
     *
     * @param year
     * @param month 0 ~ 11
     * @return List  - Calendar GridView에 출력할 날자 리스트 반환
     */
    public static List<Integer> getDays(int year, int month) {
        int firstDayOfMonth = getFirstDay(year, month); // 첫번째 일의 요일 1 ~ 7 (일 ~ 토)
        int lastDayOfMonth = getLastDay(year, month); // 마지막 일의 요일 1 ~ 7 (일 ~ 토)
        int daysOfMonth = getDay(year, month); // 해당 월의 총 일 수

        // 이전 달을 계산
        int daysOfLastMonth = getDay(year, month-1); // 지난 달의 총 일 수
        if ((month-1) < 0) { // month를 1 감소시킨 값이 범위를 벗어나는지 확인
            daysOfLastMonth = getDay(year-1, 11); // 지난 달의 총 일 수
        }

        List<Integer> days = new ArrayList<>(); // 날자 리스트 생성

        for (int i = firstDayOfMonth; i > 1; i--) {
            days.add(daysOfLastMonth-i+2); // 리스트에 이전 달의 일을 추가
        }

        for (int j = 1; j <= daysOfMonth; j++) {
            days.add(j); // 리스트에 첫번째 날자부터 추가
        }

        // 그리드뷰의 행을 6행으로 조정
        // days의 크기가 35개 이하일 때 (5행일 때)
        if (days.size() <= 35){
            for (int k = 0; k < 14-lastDayOfMonth; k ++){
                days.add(k+1); // 리스트에 이후 달의 일을 추가
            }
        }
        
        // 6행일 때
        else {
            for (int k = 0; k < 7-lastDayOfMonth; k ++){
                days.add(k+1); // 리스트에 이후 달의 일을 추가
            }
        }

        return days;
    }

    /**
     * 캘린더의 전체 높이 반환
     * pixel 단위
     * @return int height
     */
    public static int getCalenderHeight() {

        // 디스플레이 높이 설정
        int displayHeight = getDisplayHeight();

        // 스테이터스 바 높이 설정
        int statusHeight = getStatusBarHeight();

        // 앱 바 높이 설정
        int appBarHeight = getAppBarHeight();

        // 주 표시 바 높이 설정
        int weekHeight = getWeekBarHeight();

        // 높이 오차 설정 (스크롤바가 생기지 않도록 설정)
        int e = getErrorHeight();

        // 달력 높이 설정
        int calendarHeight = displayHeight - statusHeight - appBarHeight - weekHeight - e;

        System.out.println(displayHeight+ " - " + statusHeight + " - " + appBarHeight + " - " + weekHeight + " = " + (displayHeight - statusHeight - appBarHeight - weekHeight));

        return calendarHeight;
    }

    /**
     * 디스플레이 높이 반환
     * @return int Display height
     */
    public static int getDisplayHeight() {
        Display display = MainActivity.getInstance().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    /**
     * 스테이터스 바 높이 반환
     * @return int Ststusbar height
     */
    public static int getStatusBarHeight() {
        int	result = 0;
        int resourceId = MainActivity.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = MainActivity.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 앱 바 높이 반환
     * @return int Appbar height
     */
    public static int getAppBarHeight() {
        Toolbar bar = (Toolbar) MainActivity.getInstance().findViewById(R.id.toolbar);
        return bar.getHeight();
    }

    /**
     * 주 표시 바 높이 반환
     * @return int Weekbar height
     */
    public static int getWeekBarHeight() {
        TextView view = MainActivity.getInstance().findViewById(R.id.sun);
        return view.getHeight();
    }

    /**
     * 세로모드일 때 캘린더 출력 높이 오차 설정
     */
    public static void setErrorHeight() {
        CalendarUtils.e = getDisplayHeight()/70;
    }

    /**
     *  가로모드일 때 캘린더 출력 높이 오차 설정
     */
    public static void setErrorHeightInLandScape() {
        CalendarUtils.e = getDisplayHeight()/40;
    }

    /**
     * 캘린더 출력 높이 오차 반환
     */
    public static int getErrorHeight() {
        return e;
    }
}
