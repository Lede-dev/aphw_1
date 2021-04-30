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

public class WeekViewCalendarAdapter extends BaseAdapter {

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
            convertView = inflater.inflate(R.layout.weekview_grid, parent, false);
        }


        //xml에 TextView를 참조
        TextView view = convertView.findViewById(R.id.weekview_grid_format);


        // 입력받은 일이 일반적인 일일 때
        view.setText(Integer.toString(listItem.getDay()));

        return convertView;
    }



}






