package com.example.aphw_1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aphw_1.R;

import java.util.ArrayList;
import java.util.List;

public class WeekViewGridAdapter extends BaseAdapter {

    ArrayList<String> values = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return values.size();
    }

    public void addItem(String value){
        values.add(value);
    }

    public void setItem(List<String> values){
        this.values.addAll(values);
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        String time = values.get(position);

        //weekview_timebar를 inflate하고 convertView를 참조
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.weekview_grid, parent, false);
        }

        //xml에 TextView를 참조
        TextView view = convertView.findViewById(R.id.weekview_grid_format);

        return convertView;
    }

}
