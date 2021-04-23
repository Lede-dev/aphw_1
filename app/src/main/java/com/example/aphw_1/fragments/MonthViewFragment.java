package com.example.aphw_1.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.aphw_1.MainActivity;
import com.example.aphw_1.R;
import com.example.aphw_1.adapters.MonthViewCalendarAdapter;
import com.example.aphw_1.data.CurrentTime;
import com.example.aphw_1.utils.CalendarUtils;

import java.util.List;


public class MonthViewFragment extends Fragment {

    private int year;
    private int month;

    private static View tempView;

    /*
        출력할 년/월 정보를 입력하여 fragment 생성
     */
    public MonthViewFragment(int year, int month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_month_view, container, false);

        GridView calendar = fragment.findViewById(R.id.d_grid); // 그리드 뷰 객체 로드

        // GridView 생성
        List<Integer> days = CalendarUtils.getDays(year, month); // 날자 데이터 로드
        MonthViewCalendarAdapter adapt = new MonthViewCalendarAdapter(); // 뷰의 높이를 전달하여 커스텀 어댑터 객체 로드
        for (Integer day : days){
            adapt.addItem(year, month, day); // 어댑터에 데이터 추가
        }

        calendar.setAdapter(adapt); // 어댑터를 그리드 뷰 객체에 연결

        calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {   // 이 함수를 통해 이벤트 리스너 등록

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CurrentTime currentTime = new CurrentTime();

                int firstDayOfMonth = CalendarUtils.getFirstDay(currentTime.getYear(), currentTime.getMonth()); // 첫번째 일의 요일 1 ~ 7 (일 ~ 토)
                int daysOfMonth = CalendarUtils.getDay(currentTime.getYear(), currentTime.getMonth()); // 해당 월의 총 일 수

                int day = position - firstDayOfMonth + 2; // 클릭한 일을 계산

                if (day < 1 || day > daysOfMonth) return; // 계산된 일이 달력에 포함된 범위를 벗어났다면 종료

                Toast.makeText(MainActivity.getInstance(), year + "." + (month + 1) + "." + day, 0).show();      // toast message로 띄울 text

                if (tempView == null) tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정
                tempView.setBackground(getResources().getDrawable(R.drawable.month_view_border_disable)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                view.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                tempView = view; // 클릭한 뷰를 임시 뷰로 지정
            }
        });

        return fragment;
    }
}