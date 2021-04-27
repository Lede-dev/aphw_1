package com.example.aphw_1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aphw_1.R;
import com.example.aphw_1.data.ListItem;

import java.util.ArrayList;
import java.util.List;

public class WeekViewTimeBarAdapter extends BaseAdapter {

    ArrayList<String> times = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return times.size();
    }

    public void addItem(String time){
        times.add(time);
    }

    public void setItem(List<String> times){
        this.times.addAll(times);
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        String time = times.get(position);

        //weekview_timebar를 inflate하고 convertView를 참조
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.weekview_timebar, parent, false);
        }

        //xml에 TextView를 참조
        TextView view = convertView.findViewById(R.id.weekview_timebar_format);

        return convertView;
    }
}
