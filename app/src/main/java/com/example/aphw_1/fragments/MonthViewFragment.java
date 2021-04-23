package com.example.aphw_1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.aphw_1.R;
import com.example.aphw_1.adapters.MonthViewCalendarAdapter;
import com.example.aphw_1.data.CurrentTime;
import com.example.aphw_1.utils.CalendarUtils;

import java.io.Serializable;
import java.util.List;


public class MonthViewFragment extends Fragment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_month_view, container, false);

        // 현재 년/월 정보 로드
        CurrentTime currentTime = new CurrentTime();

        // 현재 년/월
        int year = currentTime.getYear();
        int month = currentTime.getMonth();

        // GridView 생성
        List<Integer> days = CalendarUtils.getDays(year, month); // 날자 데이터 로드
        MonthViewCalendarAdapter adapt = new MonthViewCalendarAdapter(); // 커스텀 어댑터 객체 로드
        for (Integer day : days){
            adapt.addItem(year, month, day); // 어댑터에 데이터 추가
        }
        GridView calendar = fragment.findViewById(R.id.d_grid); // 그리드 뷰 객체 로드
        calendar.setAdapter(adapt); // 어댑터를 그리드 뷰 객체에 연결

        return fragment;
    }
}