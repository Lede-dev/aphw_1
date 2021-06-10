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

    public static FragmentID getFragmentId(int id) {
        for (FragmentID fid : values()) {
            if (fid.getID() == id) {
                return fid;
            }
        }
        return null;
    }
}
