package com.example.aphw_1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aphw_1.Hour;
import com.example.aphw_1.R;

import java.io.Serializable;

public class WeekViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_week_view, container, false);

        // id가 listview인 리스트뷰 객체를 얻어옴
        ListView listView = rootView.findViewById(R.id.listview);
        // 리스트뷰 객체에 Shakespear.TITLES 배열을 데이터원본으로 설정한 ArrayAdapter 객체를 연결
        listView.setAdapter(
                new ArrayAdapter<String>(
                        getActivity(),  // 현재 프래그먼트 연결된 액티비티
                        android.R.layout.simple_list_item_activated_1,
                        Hour.TITLES));  // 데이터 원본

        return rootView;
    }
}
