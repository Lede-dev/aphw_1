package com.example.aphw_1.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.aphw_1.MainActivity;
import com.example.aphw_1.adapters.WeekViewCalendarAdapter;
import com.example.aphw_1.R;
import com.example.aphw_1.data.ClickedView;
import com.example.aphw_1.data.CalendarData;
import com.example.aphw_1.utils.CalendarSqlData;
import com.example.aphw_1.utils.CalendarUtils;

import java.util.List;

public class WeekViewFragment extends Fragment {

    private int year;
    private int month;
    private static int position;


    public WeekViewFragment(int year, int month, int position) {
        this.year = year;
        this.month = month;
        this.position = position;
    }

    public static int getPosition(){
        return position;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_week_view, container, false);

        // 그리드뷰
        GridView grid = fragment.findViewById(R.id.weekview_grid_container);

        List<Integer> days = CalendarUtils.getDays(year, month); // 날짜 데이터 로드

        // 어댑터
        WeekViewCalendarAdapter gridAdapter = new WeekViewCalendarAdapter();

        // 어댑터에 데이터 추가
        for (int i=0; i<168; i++){
            try {
                String dateFormat = CalendarUtils.dateFormat(CalendarData.getYear(), CalendarData.getMonth(), days.get((position*7) + (i%7)));
                String hour = Integer.toString(i/7);
                Cursor data = MainActivity.getDbHelper().getDataMatched(CalendarSqlData.Calendar.KEY_DATE, CalendarSqlData.Calendar.KEY_START_HOUR, dateFormat, hour);
                data.moveToFirst();
                String title = data.getString(data.getColumnIndex(CalendarSqlData.Calendar.KEY_TITLE));
                if (data.moveToNext()) {
                    title = "...(" + data.getCount() + ")";
                }
                if (title != null) {
                    gridAdapter.addItem(title);
                }

            } catch (Exception e) {
                gridAdapter.addItem(" ");
            }
        }

        // 그리드뷰에 어댑터 연결
        grid.setAdapter(gridAdapter);

        TextView[] dayBar = new TextView[7];

        // 텍스트뷰
        dayBar[0] = fragment.findViewById(R.id.daybar_sun); // 일요일
        dayBar[1] = fragment.findViewById(R.id.daybar_mon); // 월요일
        dayBar[2] = fragment.findViewById(R.id.daybar_tue); // 화요일
        dayBar[3] = fragment.findViewById(R.id.daybar_wed); // 수요일
        dayBar[4] = fragment.findViewById(R.id.daybar_thu); // 목요일
        dayBar[5] = fragment.findViewById(R.id.daybar_fri); // 금요일
        dayBar[6] = fragment.findViewById(R.id.daybar_sat); // 토요일


        // dayBar에 추가할 날짜 로드
        List<Integer> dayBar_days = CalendarUtils.getDays(year, month);


        // dayBar에 날짜 배치
        for (int i=0; i<7; i++){
            dayBar[i].setText(Integer.toString(dayBar_days.get(7*position + i)));
        }



        // 일요일 클릭
        dayBar[0].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정
                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[0].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정
            }

        });

        // 월요일 클릭
        dayBar[1].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정
                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[1].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[1]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정
            }

        });

        // 화요일 클릭
        dayBar[2].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정
                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[2].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[2]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정
            }

        });

        // 수요일 클릭
        dayBar[3].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정
                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[3].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[3]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정
            }

        });

        // 목요일 클릭
        dayBar[4].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정
                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[4].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[4]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정
            }

        });

        // 금요일 클릭
        dayBar[5].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정
                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[5].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[5]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정
            }

        });

        // 토요일 클릭
        dayBar[6].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정
                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[6].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[6]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정
            }

        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {   // 이 함수를 통해 이벤트 리스너 등록

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (ClickedView.getClickedView() == null) ClickedView.setClickedView(view); // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                ClickedView.getClickedView().setBackground(getResources().getDrawable(R.drawable.month_view_border_disable)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                view.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                ClickedView.setClickedView(view); // 클릭한 뷰를 임시 뷰로 지정

                // 격자 클릭시 해당되는 날짜 배경색 변경
                if (ClickedView.getWeekView_dayBar() == null) ClickedView.setWeekView_dayBar(dayBar[0]); // 이전에 클릭한 뷰에 매칭되는 뷰가 없다면 설정

                int dayBarPosition = position % 7;

                ClickedView.getWeekView_dayBar().setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰에 매칭되는 뷰 테두리 비활성화 상태로 변경
                dayBar[dayBarPosition].setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 뷰에 매칭되는 뷰 테두리 설정
                ClickedView.setWeekView_dayBar(dayBar[dayBarPosition]); // 이전에 클릭한 뷰에 매칭되는 뷰를 현재 클릭한 뷰에 매칭되는 뷰로 설정

                List<Integer> dayBar_days = CalendarUtils.getDays(year, month);
                int day = dayBar_days.get((CalendarData.getPage()*7) + (position%7));
                int hour = position/7; // 시간

                CalendarData.setPosition(position); // 클릭한 포지션 저장
                CalendarData.setDay(day); // 클릭한 일을 저장
                CalendarData.setHour(hour); // 시간
                CalendarData.setMinute(0); // 분
                CalendarData.setEndHour(hour+1 > 23 ? 0 : hour+1); // 종료 시간
                CalendarData.setEndMinute(0); // 종료 분
            }

        });

        return fragment;


    }


}
