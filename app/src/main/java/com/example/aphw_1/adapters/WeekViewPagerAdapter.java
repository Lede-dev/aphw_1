package com.example.aphw_1.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aphw_1.data.CurrentTime;
import com.example.aphw_1.fragments.MonthViewFragment;
import com.example.aphw_1.fragments.WeekViewFragment;

public class WeekViewPagerAdapter extends FragmentStateAdapter {

    private static int NUM_ITEMS = 6;

    public WeekViewPagerAdapter(FragmentActivity fa){
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        // 현재 년/월 로드
        CurrentTime currentTime = new CurrentTime();
        return new WeekViewFragment(currentTime.getYear(), currentTime.getMonth(), position); // 위치에 맞는 월(0 ~ 11 == 1월 ~ 12월)의 Fragment 객체를 생성

    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
