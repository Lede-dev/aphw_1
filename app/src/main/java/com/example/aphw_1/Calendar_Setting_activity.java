package com.example.aphw_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.aphw_1.adapters.MonthViewCalendarAdapter;
import com.example.aphw_1.data.CurrentTime;
import com.example.aphw_1.fragments.MonthViewFragment;
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

        int year = currentTime.getYear();
        int month = currentTime.getMonth();
        MonthViewCalendarAdapter monthViewCalendarAdapter = new MonthViewCalendarAdapter();
        int day = 0;

        TextView cal = findViewById(R.id.title);  // 아이디에 해당하는 텍스트 개체를 찾음
        cal.setText(year+"년 "+ (month+1) +"월 " + day + "일 ");

        TimePicker timePicker = findViewById(R.id.timepiker1);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int min) {

                cal.setText(year+"년 "+ (month+1) +"월 " + day + "일 " + hour + "시 " + min + "분 " ); // 텍스트를 입력

            }
        });





        Button backBtn = findViewById(R.id.cancle); // ID로부터 대응되는 객체를 찾음
        backBtn.setOnClickListener(new View.OnClickListener() {  // 이 함수를 통해 이벤트 리스너 등록

            @Override
            public void onClick(View v) {

                startActivity(intent); // 새 액티비티의 인스턴스를 시작
                finish(); // 종료

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
}