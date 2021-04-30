package com.example.aphw_1.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aphw_1.MainActivity;
import com.example.aphw_1.adapters.WeekViewGridAdapter;
import com.example.aphw_1.R;

public class WeekViewFragment extends Fragment {

    private int year;
    private int month;
    private static View tempView;
    private static View tempView2;
    private static View tempView3;

    public WeekViewFragment(int year, int month) {
        this.year = year;
        this.month = month;
    }

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



        // 텍스트뷰
        TextView textView_sun = fragment.findViewById(R.id.daybar_sun);
        TextView textView_mon = fragment.findViewById(R.id.daybar_mon);
        TextView textView_tue = fragment.findViewById(R.id.daybar_tue);
        TextView textView_wed = fragment.findViewById(R.id.daybar_wed);
        TextView textView_thu = fragment.findViewById(R.id.daybar_thu);
        TextView textView_fri = fragment.findViewById(R.id.daybar_fri);
        TextView textView_sat = fragment.findViewById(R.id.daybar_sat);






        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {   // 이 함수를 통해 이벤트 리스너 등록

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(MainActivity.getInstance(), "position=" + position , 0).show();      // toast message로 띄울 text

                if (tempView == null) {

                    tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                    tempView3 = textView_sun;
                    tempView3 = textView_mon;
                    tempView3 = textView_tue;
                    tempView3 = textView_wed;
                    tempView3 = textView_thu;
                    tempView3 = textView_fri;
                    tempView3 = textView_sat;

                    tempView2 = textView_sun;
                    tempView2 = textView_mon;
                    tempView2 = textView_tue;
                    tempView2 = textView_wed;
                    tempView2 = textView_thu;
                    tempView2 = textView_fri;
                    tempView2 = textView_sat;
                }

                tempView.setBackground(getResources().getDrawable(R.drawable.month_view_border_disable)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                view.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                tempView = view; // 클릭한 뷰를 임시 뷰로 지정


                textView_sun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (tempView3 == null) {

                            tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                            tempView3 = textView_sun;
                            tempView3 = textView_mon;
                            tempView3 = textView_tue;
                            tempView3 = textView_wed;
                            tempView3 = textView_thu;
                            tempView3 = textView_fri;
                            tempView3 = textView_sat;

                            tempView2 = textView_sun;
                            tempView2 = textView_mon;
                            tempView2 = textView_tue;
                            tempView2 = textView_wed;
                            tempView2 = textView_thu;
                            tempView2 = textView_fri;
                            tempView2 = textView_sat;
                        }

                        tempView3.setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                        v.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                        tempView3 = v; // 클릭한 뷰를 임시 뷰로 지정


                    }
                });


                textView_mon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (tempView3 == null) {

                            tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                            tempView3 = textView_sun;
                            tempView3 = textView_mon;
                            tempView3 = textView_tue;
                            tempView3 = textView_wed;
                            tempView3 = textView_thu;
                            tempView3 = textView_fri;
                            tempView3 = textView_sat;

                            tempView2 = textView_sun;
                            tempView2 = textView_mon;
                            tempView2 = textView_tue;
                            tempView2 = textView_wed;
                            tempView2 = textView_thu;
                            tempView2 = textView_fri;
                            tempView2 = textView_sat;
                        }

                        tempView3.setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                        v.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                        tempView3 = v; // 클릭한 뷰를 임시 뷰로 지정


                    }
                });


                textView_tue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (tempView3 == null) {

                            tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                            tempView3 = textView_sun;
                            tempView3 = textView_mon;
                            tempView3 = textView_tue;
                            tempView3 = textView_wed;
                            tempView3 = textView_thu;
                            tempView3 = textView_fri;
                            tempView3 = textView_sat;

                            tempView2 = textView_sun;
                            tempView2 = textView_mon;
                            tempView2 = textView_tue;
                            tempView2 = textView_wed;
                            tempView2 = textView_thu;
                            tempView2 = textView_fri;
                            tempView2 = textView_sat;
                        }

                        tempView3.setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                        v.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                        tempView3 = v; // 클릭한 뷰를 임시 뷰로 지정


                    }
                });


                textView_wed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (tempView3 == null) {

                            tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                            tempView3 = textView_sun;
                            tempView3 = textView_mon;
                            tempView3 = textView_tue;
                            tempView3 = textView_wed;
                            tempView3 = textView_thu;
                            tempView3 = textView_fri;
                            tempView3 = textView_sat;

                            tempView2 = textView_sun;
                            tempView2 = textView_mon;
                            tempView2 = textView_tue;
                            tempView2 = textView_wed;
                            tempView2 = textView_thu;
                            tempView2 = textView_fri;
                            tempView2 = textView_sat;
                        }

                        tempView3.setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                        v.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                        tempView3 = v; // 클릭한 뷰를 임시 뷰로 지정


                    }
                });


                textView_thu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (tempView3 == null) {

                            tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                            tempView3 = textView_sun;
                            tempView3 = textView_mon;
                            tempView3 = textView_tue;
                            tempView3 = textView_wed;
                            tempView3 = textView_thu;
                            tempView3 = textView_fri;
                            tempView3 = textView_sat;

                            tempView2 = textView_sun;
                            tempView2 = textView_mon;
                            tempView2 = textView_tue;
                            tempView2 = textView_wed;
                            tempView2 = textView_thu;
                            tempView2 = textView_fri;
                            tempView2 = textView_sat;
                        }

                        tempView3.setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                        v.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                        tempView3 = v; // 클릭한 뷰를 임시 뷰로 지정


                    }
                });


                textView_fri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (tempView3 == null) {

                            tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                            tempView3 = textView_sun;
                            tempView3 = textView_mon;
                            tempView3 = textView_tue;
                            tempView3 = textView_wed;
                            tempView3 = textView_thu;
                            tempView3 = textView_fri;
                            tempView3 = textView_sat;

                            tempView2 = textView_sun;
                            tempView2 = textView_mon;
                            tempView2 = textView_tue;
                            tempView2 = textView_wed;
                            tempView2 = textView_thu;
                            tempView2 = textView_fri;
                            tempView2 = textView_sat;


                        }

                        tempView3.setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                        v.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                        tempView3 = v; // 클릭한 뷰를 임시 뷰로 지정


                    }
                });


                textView_sat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (tempView3 == null) {

                            tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                            tempView3 = textView_sun;
                            tempView3 = textView_mon;
                            tempView3 = textView_tue;
                            tempView3 = textView_wed;
                            tempView3 = textView_thu;
                            tempView3 = textView_fri;
                            tempView3 = textView_sat;

                            tempView2 = textView_sun;
                            tempView2 = textView_mon;
                            tempView2 = textView_tue;
                            tempView2 = textView_wed;
                            tempView2 = textView_thu;
                            tempView2 = textView_fri;
                            tempView2 = textView_sat;

                        }

                        tempView3.setBackground(getResources().getDrawable(R.drawable.date_view_border)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                        v.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable)); // 클릭한 일의 배경 색을 변경
                        tempView3 = v; // 클릭한 뷰를 임시 뷰로 지정


                    }
                });

                // 격자 클릭시 해당되는 날짜 배경색 변경

                if (tempView2 == null) {



                    tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정

                    tempView3 = textView_sun;
                    tempView3 = textView_mon;
                    tempView3 = textView_tue;
                    tempView3 = textView_wed;
                    tempView3 = textView_thu;
                    tempView3 = textView_fri;
                    tempView3 = textView_sat;

                    tempView2 = textView_sun;
                    tempView2 = textView_mon;
                    tempView2 = textView_tue;
                    tempView2 = textView_wed;
                    tempView2 = textView_thu;
                    tempView2 = textView_fri;
                    tempView2 = textView_sat;


                }else if (position % 7 == 0){

                    tempView2.setBackground(getResources().getDrawable(R.drawable.date_view_border));
                    textView_sun.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable));
                    tempView2 = textView_sun;

                }else if (position % 7 == 1){

                    tempView2.setBackground(getResources().getDrawable(R.drawable.date_view_border));
                    textView_mon.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable));
                    tempView2 = textView_mon;

                }else if (position % 7 == 2){

                    tempView2.setBackground(getResources().getDrawable(R.drawable.date_view_border));
                    textView_tue.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable));
                    tempView2 = textView_tue;

                }else if (position % 7 == 3){

                    tempView2.setBackground(getResources().getDrawable(R.drawable.date_view_border));
                    textView_wed.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable));
                    tempView2 = textView_wed;

                }else if (position % 7 == 4){

                    tempView2.setBackground(getResources().getDrawable(R.drawable.date_view_border));
                    textView_thu.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable));
                    tempView2 = textView_thu;

                }else if (position % 7 == 5){

                    tempView2.setBackground(getResources().getDrawable(R.drawable.date_view_border));
                    textView_fri.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable));
                    tempView2 = textView_fri;

                }else if (position % 7 == 6){

                    tempView2.setBackground(getResources().getDrawable(R.drawable.date_view_border));
                    textView_sat.setBackground(getResources().getDrawable(R.drawable.month_view_border_enable));
                    tempView2 = textView_sat;

                }

            }
        });

        return fragment;


    }


}
