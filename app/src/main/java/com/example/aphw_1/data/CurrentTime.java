package com.example.aphw_1.data;


/*
    MainActivity 외부에서도 년/월 정보를 쉽게 불러오기 위하여 기능을 분리
 */
public class CurrentTime {

    private static int year;
    private static int month;

    public CurrentTime(int year, int month){
        this.year = year;
        this.month = month;
    }

    public CurrentTime(){

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
