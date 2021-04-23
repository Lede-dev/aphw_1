package com.example.aphw_1.data;

public class ListItem {
    Integer year;
    Integer month;
    Integer day;

    public ListItem(Integer year, Integer month, Integer day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

}
