package com.example.aphw_1.data;


/*
    MainActivity 외부에서도 년/월 정보를 쉽게 불러오기 위하여 기능을 분리
 */
public class CurrentTime {

    private static int year;
    private static int month;
    private static int position;
    private static int day;


    public CurrentTime(int year, int month, int position, int day){
        this.year = year;
        this.month = month;
        this.position = position;
        this.day = day;

    }

    public CurrentTime(){

    }

    public void setday(int day) {
        CurrentTime.day = day;
    }

    public int getday() {
        return CurrentTime.day;
    }


    public void setPosition(int position) {
        CurrentTime.position = position;
    }

    public int getPosition() {
        return CurrentTime.position;
    }

    public void setYear(int year) {
        CurrentTime.year = year;
    }

    public int getYear() {
        return CurrentTime.year;
    }

    public void setMonth(int month) {
        CurrentTime.month = month;
    }

    public int getMonth() {
        return CurrentTime.month;
    }
}
