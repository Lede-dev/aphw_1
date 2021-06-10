package com.example.aphw_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.aphw_1.data.CalendarData;
import com.example.aphw_1.utils.DBHelper;
import com.example.aphw_1.utils.EditorState;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Calendar_Setting_activity extends AppCompatActivity implements OnMapReadyCallback {

    private static DBHelper dbHelper;

    double lat;
    double lng;

    public Intent createIntent() {
        Intent intent = getIntent();
        intent.setClass(this, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_setting_activity);

        dbHelper = new DBHelper(this); // load database helper

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.schedule_toolbar); // 툴 바 로드
        toolbar.setTitle(String.format("%d년 %d월 %d일", CalendarData.getYear(), (CalendarData.getMonth()+1), CalendarData.getDay())); // 툴 바의 타이틀, 불러온 달은 0~11월 이기 때문에 (month+1)을 하여 1~12월로 현재 달을 출력
        setSupportActionBar(toolbar);

        int year = CalendarData.getYear(); // 년
        int month = CalendarData.getMonth(); // 월
        int day = CalendarData.getDay(); // 일
        int hour = CalendarData.getHour(); // 시간

        TextView printDate = findViewById(R.id.title);
        printDate.setText(String.format("%d년 %d월 %d일 %d시", year, month, day, hour));

        // 시작 시간을 조절
        TimePicker startTime = findViewById(R.id.timepicker1);
        startTime.setCurrentHour(hour);
        startTime.setCurrentMinute(0);

        // 종료 시간을 조절
        TimePicker endTime = findViewById(R.id.timepicker2);
        endTime.setCurrentHour(hour+1 > 23 ? 0 : hour+1);
        endTime.setCurrentMinute(0);

        // 취소 버튼 클릭시 이전 화면으로 이동
        Button backBtn = findViewById(R.id.cancle); // 이전 버튼 객체
        backBtn.setOnClickListener(new View.OnClickListener() {  // 이 함수를 통해 이벤트 리스너 등록
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // 저장 버튼을 누르면 데이터베이스에 저장
        Button saveBtn = findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 데이터 추가
                if (CalendarData.getEditorState() == EditorState.INSERT) {
                    insertData();
                }
                
                // 기존 데이터 업데이트
                else {
                    String id = " ";
                    updateData(id);
                }
                
                finish(); // 현재 액티비티 종료
            }
        });

        // 삭제 버튼 클릭 시 일정 삭제
        Button deleteBtn = findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CalendarData.getEditorState() == EditorState.UPDATE) {
                    String id = "";
                    deleteData(id);
                }
                finish(); // 현재 액티비티 종료
            }
        });

    }

    private void searchAddress() {

        // 주소를 입력하면 loc에 스트링으로 저장
        EditText et = (EditText)findViewById(R.id.location);
        String loc = et.getText().toString();


        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(loc,1);
            if (addresses.size() >0) {
                Address bestResult = (Address) addresses.get(0);

                CalendarData.setLat(bestResult.getLatitude()); // 위도 저장
                CalendarData.setLng(bestResult.getLongitude()); // 경도 저장

            }
        } catch (IOException e) {
            Log.e(getClass().toString(),"Failed in using Geocoder.", e);
            return;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // 주소 찾기 버튼을 누르면 입력한 주소의 위도, 경도로 마커와 카메라 이동
        Button getAddressButton = findViewById(R.id.find);
        getAddressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                searchAddress();
                LatLng find = new LatLng(CalendarData.getLat(), CalendarData.getLng());
                googleMap.addMarker(new MarkerOptions().position(find));
                // move the camera
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(find, 15));

            }
        });
    }

    // data 추가
    private void insertData() {
         CalendarData calendarData = new CalendarData();

         String date = calendarData.getYear() + "." + calendarData.getMonth() + "." + calendarData.getDay() + "." + calendarData.getHour();
         EditText title = (EditText)findViewById(R.id.title);
         EditText location = (EditText)findViewById(R.id.location);
         EditText note = (EditText)findViewById(R.id.note);

         dbHelper.insertCalendarData(date,title.getText().toString(), location.getText().toString(), note.getText().toString());

    }

    // db 업데이트
    private void updateData(String id) {
        CalendarData calendarData = new CalendarData();

        String date = calendarData.getYear() + "." + calendarData.getMonth() + "." + calendarData.getDay() + "." + calendarData.getHour();
        EditText title = (EditText)findViewById(R.id.title);
        EditText location = (EditText)findViewById(R.id.location);
        EditText note = (EditText)findViewById(R.id.note);

        dbHelper.updateCalendarData(id, date,title.getText().toString(), location.getText().toString(), note.getText().toString());
    }
    
    // data 삭제
    private void deleteData(String id) {
        dbHelper.deleteCalendarData(id);
    }

}