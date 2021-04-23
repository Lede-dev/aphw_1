package com.example.aphw_1.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.aphw_1.MainActivity;
import com.example.aphw_1.R;
import com.example.aphw_1.data.ListItem;
import com.example.aphw_1.utils.CalendarUtils;

import java.util.ArrayList;

public class MonthViewCalendarAdapter extends BaseAdapter {

    ArrayList<ListItem> items = new ArrayList<>(); // 달력 날자
    Context context;

    public static int ROW_NUMBER = 6;
    GridView mGv;

    public MonthViewCalendarAdapter() {

    }

    public MonthViewCalendarAdapter(GridView gv) {
        this.mGv = gv;
    }

    public void addItem(int year, int month, int day){
        items.add(new ListItem(year, month, day));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);

        //activity_main을 inflate하고 convertView를 참조
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_view, parent, false);
        }

        int firstDayOfMonth = CalendarUtils.getFirstDay(listItem.getYear(), listItem.getMonth()); // 현재 년도, 현재 달의 첫번째 일의 요일
        int daysOfMonth = CalendarUtils.getDay(listItem.getYear(), listItem.getMonth()); // 현재 년도 현재 달의 마지막 일

        //xml에 TextView를 참조
        TextView view = convertView.findViewById(R.id.dayView_format);

        // 캘린더 그리드 높이 설정
        // 캘린더 높이 / 캘린더 행(6)
        view.setHeight(CalendarUtils.getCalenderHeight()/6);

        if (position+1 < firstDayOfMonth || firstDayOfMonth+daysOfMonth < position+2){ // 입력받은 일이 해당 달의 범위를 벗어난다면 실행
            view.setBackgroundColor(Color.parseColor("#b0916f")); // 배경 색을 변경
            view.setTextColor(Color.parseColor("#404040")); // 글자 색을 회색으로 변경
            view.setText(Integer.toString(listItem.getDay()));
            return convertView;
        }

        int dayOfWeek = CalendarUtils.getDayOfWeek(listItem.getYear(), listItem.getMonth(), listItem.getDay()); // 입력받은 일의 요일을 계산

        if (dayOfWeek == 1){ // 입력받은 일이 일요일일 때
            view.setTextColor(Color.parseColor("#ff0000")); // 글자 색을 빨간색으로 변경
            view.setText(Integer.toString(listItem.getDay()));
            return convertView;
        }

        if (dayOfWeek == 7){ // 입력받은 일이 토요일일 때
            view.setTextColor(Color.parseColor("#0000ff")); // 글자 색을 파란색으로 변경
            view.setText(Integer.toString(listItem.getDay()));
            return convertView;
        }

        // 입력받은 일이 일반적인 일일 때
        view.setText(Integer.toString(listItem.getDay()));

        return convertView;
    }



}






