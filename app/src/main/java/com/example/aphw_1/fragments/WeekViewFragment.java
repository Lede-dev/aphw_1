package com.example.aphw_1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.aphw_1.adapters.WeekViewGridAdapter;
import com.example.aphw_1.R;

public class WeekViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_week_view, container, false);

        // 그리드뷰
        GridView grid = fragment.findViewById(R.id.weekview_grid_container);

        // 어댑터
        WeekViewGridAdapter gridAdapter = new WeekViewGridAdapter();

        // 어댑터에 데이터 추가
        for (int i=0; i<168; i++)
            gridAdapter.addItem(" ");

        // 그리드뷰에 어댑터 연결
        grid.setAdapter(gridAdapter);

        return fragment;
    }
}
