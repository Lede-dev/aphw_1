package com.example.aphw_1.data;

import android.view.View;

public class ClickedView {

    private static View clickedView;
    private static View weekView_dayBar;

    public static View getClickedView() {
        return clickedView;
    }

    public static void setClickedView(View clickedView) {
        ClickedView.clickedView = clickedView;
    }

    public static View getWeekView_dayBar() {
        return weekView_dayBar;
    }

    public static void setWeekView_dayBar(View weekView_dayBar) {
        ClickedView.weekView_dayBar = weekView_dayBar;
    }

}
