package com.example.aphw_1.utils;

public enum FragmentID {
    MONTH(0), WEEK(1);

    int id;

    private FragmentID(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }
}
