package com.example.aphw_1.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aphw_1.Calendar_Setting_activity;
import com.example.aphw_1.MainActivity;
import com.example.aphw_1.R;
import com.example.aphw_1.adapters.MonthViewCalendarAdapter;
import com.example.aphw_1.data.ClickedView;
import com.example.aphw_1.data.CalendarData;
import com.example.aphw_1.utils.CalendarSqlData;
import com.example.aphw_1.utils.CalendarUtils;
import com.example.aphw_1.utils.DBHelper;
import com.example.aphw_1.utils.EditorState;
import com.example.aphw_1.utils.FragmentID;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonthViewFragment extends Fragment {

    private int year;
    private int month;

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
        MonthViewCalendarAdapter adapt = new MonthViewCalendarAdapter();
        for (Integer day : days){
            adapt.addItem(year, month, day); // 어댑터에 데이터 추가
        }
        
        calendar.setAdapter(adapt); // 어댑터를 그리드 뷰 객체에 연결

        calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {   // 이 함수를 통해 이벤트 리스너 등록

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int firstDayOfMonth = CalendarUtils.getFirstDay(CalendarData.getYear(), CalendarData.getMonth()); // 첫번째 일의 요일 1 ~ 7 (일 ~ 토)
                int daysOfMonth = CalendarUtils.getDay(CalendarData.getYear(), CalendarData.getMonth()); // 해당 월의 총 일 수

                int day = position - firstDayOfMonth + 2; // 클릭한 일을 계산

                if (day < 1 || day > daysOfMonth) return; // 계산된 일이 달력에 포함된 범위를 벗어났다면 종료

                Toast.makeText(MainActivity.getInstance(), year + "." + (month + 1) + "." + day, 0).show();      // toast message로 띄울 text

                if (ClickedView.getClickedView() == null) ClickedView.setClickedView(view); // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정
                ClickedView.getClickedView().setBackground(getResources().getDrawable(R.drawable.month_view_border_disable)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                view.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                ClickedView.setClickedView(view); // 클릭한 뷰를 임시 뷰로 지정

                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // 현재시간

                CalendarData.setPosition(position); // 클릭한 포지션을 저장
                CalendarData.setDay(day); // 클릭한 일을 저장
                CalendarData.setHour(hour); // 현재 시간을 저장
                CalendarData.setMinute(0); // 분
                CalendarData.setEndHour(hour+1 > 23 ? 0 : hour+1); // 종료 시간
                CalendarData.setEndMinute(0); // 종료 분
                
            }
        });


        return fragment;
    }
}