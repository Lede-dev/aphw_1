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
import com.example.aphw_1.adapters.WeekViewGridAdapter;
import com.example.aphw_1.R;
import com.example.aphw_1.data.CurrentTime;
import com.example.aphw_1.utils.CalendarUtils;

public class WeekViewFragment extends Fragment {


    private static View tempView;


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


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {   // 이 함수를 통해 이벤트 리스너 등록

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(MainActivity.getInstance(), "position=" + position , 0).show();      // toast message로 띄울 text

                if (tempView == null) tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정
                tempView.setBackground(getResources().getDrawable(R.drawable.month_view_border_disable)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                view.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                tempView = view; // 클릭한 뷰를 임시 뷰로 지정

            }
        });

        return fragment;


    }


}
