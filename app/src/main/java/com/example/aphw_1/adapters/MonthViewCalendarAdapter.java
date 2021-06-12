package com.example.aphw_1.adapters;

import android.content.Context;
import android.database.Cursor;
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
import com.example.aphw_1.utils.CalendarSqlData;
import com.example.aphw_1.utils.CalendarUtils;
import com.example.aphw_1.utils.DBHelper;

import java.util.ArrayList;

public class MonthViewCalendarAdapter extends BaseAdapter {

    ArrayList<ListItem> items = new ArrayList<>(); // 달력 날자
    Context context;

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
        TextView dayView = convertView.findViewById(R.id.dayView_format); // 날짜 뷰
        //TextView scheduleView = convertView.findViewById(R.id.schedule); // 스케쥴 뷰

        // 캘린더 그리드 높이 설정
        int height = CalendarUtils.getCalenderHeight()/6; // 캘린더 1칸 내부의 뷰 1개 높이
        dayView.setHeight(height); // 날짜 뷰 높이
        //scheduleView.setHeight(height-dayView.getHeight()-1); // 스케쥴 뷰 높이


        if (position+1 < firstDayOfMonth || firstDayOfMonth+daysOfMonth < position+2){ // 입력받은 일이 해당 달의 범위를 벗어난다면 실행
            
            // 배경 색 설정
            dayView.setBackgroundColor(Color.parseColor("#888888"));
            //scheduleView.setBackgroundColor(Color.parseColor("#888888"));
            // 글자 색 설정
            dayView.setTextColor(Color.parseColor("#404040"));
            //scheduleView.setTextColor(Color.parseColor("#404040"));

            // 텍스트 설정
            dayView.setText(Integer.toString(listItem.getDay()));
            return convertView;
        }

        int dayOfWeek = CalendarUtils.getDayOfWeek(listItem.getYear(), listItem.getMonth(), listItem.getDay()); // 입력받은 일의 요일을 계산
        
        String dateFormat = CalendarUtils.dateFormat(listItem.getYear(), listItem.getMonth(), listItem.getDay()); // db 조회를 위해 날짜를 String 으로 포멧팅

        DBHelper dbHelper = MainActivity.getDbHelper();
        Cursor data = dbHelper.getDataMatched(CalendarSqlData.Calendar.KEY_DATE, dateFormat);

        String text = " ";
        try {
            data.moveToFirst();
            // 첫번째 데이터가 존재한다면 출력
            text = "\n" + data.getString(data.getColumnIndex(CalendarSqlData.Calendar.KEY_TITLE));
            
            // 다음 데이터가 존재한다면 개수로 출력
            if (data.moveToNext()) {
                text = "\n...(" + data.getCount() + ")";
            }
            //scheduleView.setText(text); // 스케쥴 뷰 텍스트 설정

        } catch (Exception e) {

        }


        if (dayOfWeek == 1){ // 입력받은 일이 일요일일 때
            dayView.setTextColor(Color.parseColor("#ff0000")); // 글자 색을 빨간색으로 변경
            dayView.setText(listItem.getDay() + text);
            return convertView;
        }

        if (dayOfWeek == 7){ // 입력받은 일이 토요일일 때
            dayView.setTextColor(Color.parseColor("#0000ff")); // 글자 색을 파란색으로 변경
            dayView.setText(listItem.getDay() + text);
            return convertView;
        }

        // 입력받은 일이 일반적인 일일 때
        dayView.setText(listItem.getDay() + text);

        return convertView;
    }



}






