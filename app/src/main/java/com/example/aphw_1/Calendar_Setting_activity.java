package com.example.aphw_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aphw_1.adapters.MonthViewCalendarAdapter;
import com.example.aphw_1.adapters.MonthViewPagerAdapter;
import com.example.aphw_1.adapters.WeekViewPagerAdapter;
import com.example.aphw_1.data.CurrentTime;
import com.example.aphw_1.fragments.MonthViewBarFragment;
import com.example.aphw_1.fragments.MonthViewFragment;
import com.example.aphw_1.fragments.WeekViewBarFragment;
import com.example.aphw_1.utils.CalendarUtils;
import com.example.aphw_1.utils.FragmentID;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Calendar_Setting_activity extends AppCompatActivity implements OnMapReadyCallback {


    final static String TAG="SQLITEDBTEST";

    EditText mDate;
    EditText mTitle;
    EditText mLocation;
    EditText mNote;

    private DBHelper mDbHelper;


    String loc;
    EditText et;
    double a;
    double b;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_setting_activity);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        CurrentTime currentTime = new CurrentTime(); // 현재 년/월 로드
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);


        intent.putExtra("year", currentTime.getYear());
        intent.putExtra("month", currentTime.getMonth());



        int firstDayOfMonth = CalendarUtils.getFirstDay(currentTime.getYear(), currentTime.getMonth()); // 첫번째 일의 요일 1 ~ 7 (일 ~ 토)
        int position = currentTime.getPosition();


        int year = currentTime.getYear();
        int month = currentTime.getMonth();

        // Month에서 일정추가
        if (position < 100){

            int day = position - firstDayOfMonth + 2; // 클릭한 일을 계산

            TextView cal = findViewById(R.id.date);  // 아이디에 해당하는 텍스트 개체를 찾음
            cal.setText(year+"년 "+ (month+1) +"월 " + day + "일 ");

            TimePicker timePicker1 = findViewById(R.id.timepicker1);

            timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hour1, int min1) {

                    TimePicker timePicker2 = findViewById(R.id.timepicker2);

                    timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker view, int hour2, int min2) {


                            cal.setText(year+"년 "+ (month+1) +"월 " + day + "일 " + hour1 + "시 " + min1 + "분 " + "~ " + hour2 + "시 " + min2 + "분 " ); // 텍스트를 입력

                        }
                    });

                }
            });

        }

        // Week에서 일정추가
        else if (position >= 100){

            position = position - 100;

            int day = currentTime.getday();

            int time = position+1;
            if(time == 24) time = 0;

            TextView cal = findViewById(R.id.date);  // 아이디에 해당하는 텍스트 개체를 찾음
            cal.setText(year+"년 "+ (month+1) +"월 " + day + "일 " + position + "시 " + "~ " + time + "시 ");

        }



        Button backBtn = findViewById(R.id.cancle); // ID로부터 대응되는 객체를 찾음
        backBtn.setOnClickListener(new View.OnClickListener() {  // 이 함수를 통해 이벤트 리스너 등록

            @Override
            public void onClick(View v) {

                startActivity(intent); // 새 액티비티의 인스턴스를 시작
                finish(); // 종료                                                             

            }
        });

       mDate = (EditText)findViewById(R.id.date);
       mTitle = (EditText)findViewById(R.id.title);
       mLocation = (EditText)findViewById(R.id.location);
       mNote = (EditText)findViewById(R.id.note);


        mDbHelper = new DBHelper(this);


        Button saveBtn = findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecord();
            }
        });


    }



    private void getAddress() {

        et = (EditText)findViewById(R.id.location);
        loc = et.getText().toString();


        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(loc,1);
            if (addresses.size() >0) {
                Address bestResult = (Address) addresses.get(0);


                et.setText(String.format("[ %s , %s ]",
                        bestResult.getLatitude(),
                        bestResult.getLongitude()));

                a = bestResult.getLatitude();
                b = bestResult.getLongitude();


            }
        } catch (IOException e) {
            Log.e(getClass().toString(),"Failed in using Geocoder.", e);
            return;
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        Button getAddressButton = findViewById(R.id.find);
        getAddressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getAddress();

                LatLng find = new LatLng(a, b);
                googleMap.addMarker(new MarkerOptions().position(find));
                // move the camera
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(find, 15));

    }
});


    }


     private void insertRecord() {

            EditText Date = (EditText)findViewById(R.id.date);
            EditText Title = (EditText)findViewById(R.id.title);
            EditText Location = (EditText)findViewById(R.id.location);
            EditText Note = (EditText)findViewById(R.id.note);

            mDbHelper.insertUserBySQL(Date.getText().toString(),Title.getText().toString(), Location.getText().toString(), Note.getText().toString());

         }


}