package com.example.aphw_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
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

import com.example.aphw_1.data.CalendarData;
import com.example.aphw_1.utils.CalendarSqlData;
import com.example.aphw_1.utils.CalendarUtils;
import com.example.aphw_1.utils.DBHelper;
import com.example.aphw_1.utils.EditorState;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Calendar_Setting_activity extends AppCompatActivity implements OnMapReadyCallback {

    final private int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 100;

    private static DBHelper dbHelper;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_setting_activity);

        dbHelper = new DBHelper(this); // load database helper
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // load fused location client

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        int year = CalendarData.getYear(); // 년
        int month = CalendarData.getMonth(); // 월
        int day = CalendarData.getDay(); // 일
        int hour = CalendarData.getHour(); // 시간
        int minute = CalendarData.getMinute(); // 분
        
        int endHour = CalendarData.getEndHour(); // 종료 시간
        int endMinute = CalendarData.getEndMinute(); // 종료 분

        String title = String.format("%d년 %d월 %d일 %d시", year, (month+1), day, hour); // 일정 제목
        String note = ""; // 일정 메모

        getLastLocation(); // 마지막으로 알려진 위치 로드하여 저장
        
        // 일정 수정 시 데이터 로드
        if (CalendarData.getEditorState() == EditorState.UPDATE) {
            Cursor data = MainActivity.getDbHelper().getDataMatched(CalendarSqlData.Calendar._ID, CalendarData.getId()); // id로 database 로드
            data.moveToFirst();
            try {
                hour = data.getInt(data.getColumnIndex(CalendarSqlData.Calendar.KEY_START_HOUR)); // 일정 시작 시간 로드
                minute = data.getInt(data.getColumnIndex(CalendarSqlData.Calendar.KEY_START_MINUTE)); // 일정 시작 분 로드
                endHour = data.getInt(data.getColumnIndex(CalendarSqlData.Calendar.KEY_END_HOUR)); // 일정 종료 시간 로드
                endMinute = data.getInt(data.getColumnIndex(CalendarSqlData.Calendar.KEY_END_MINUTE)); // 일정 종료 분 로드

                title = data.getString(data.getColumnIndex(CalendarSqlData.Calendar.KEY_TITLE)); // 타이틀 로드
                note = data.getString(data.getColumnIndex(CalendarSqlData.Calendar.KEY_NOTE)); // 메모 로드

                double lat = data.getDouble(data.getColumnIndex(CalendarSqlData.Calendar.KEY_LATITUDE)); // 위도 로드
                double lng = data.getDouble(data.getColumnIndex(CalendarSqlData.Calendar.KEY_LONGITUDE)); // 경도 로드
                CalendarData.setLat(lat); // 위도 설정
                CalendarData.setLng(lng); // 경도 설정
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.schedule_toolbar); // 툴 바 로드
        toolbar.setTitle(String.format("%d년 %d월 %d일", year, (month+1), day)); // 툴 바의 타이틀, 불러온 달은 0~11월 이기 때문에 (month+1)을 하여 1~12월로 현재 달을 출력
        setSupportActionBar(toolbar);

        // 제목 입력
        TextView printDate = findViewById(R.id.title);
        printDate.setText(title);

        // 메모
        TextView printNote = findViewById(R.id.note);
        printNote.setText(note);

        // 시작 시간을 조절
        TimePicker startTime = findViewById(R.id.timepicker1);
        startTime.setCurrentHour(hour);
        startTime.setCurrentMinute(minute);

        // 종료 시간을 조절
        TimePicker endTime = findViewById(R.id.timepicker2);
        endTime.setCurrentHour(endHour);
        endTime.setCurrentMinute(endMinute);

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

                int startHour = startTime.getCurrentHour();
                int startMinute = startTime.getCurrentMinute();
                int endHour = endTime.getCurrentHour();
                int endMinute = endTime.getCurrentMinute();

                // 시간이 정상적으로 등록되어있는지 확인
                if (CalendarUtils.timeCheck(startHour, startMinute, endHour, endMinute)) {
                    // 데이터 추가
                    if (CalendarData.getEditorState() == EditorState.INSERT) { // data를 추가할 때
                        insertData();
                        Toast.makeText(MainActivity.getInstance(), "새로운 일정을 추가하였습니다", Toast.LENGTH_SHORT).show();
                    }

                    // 기존 데이터 업데이트
                    else { // data를 업데이트할 때
                        updateData(CalendarData.getId());
                        Toast.makeText(MainActivity.getInstance(), "일정을 업데이트 하였습니다", Toast.LENGTH_SHORT).show();
                    }
                    finish(); // 현재 액티비티 종료
                    restartMainActivity(); // 메인 액티비티 재시작
                }

                else {
                    Toast.makeText(MainActivity.getInstance(), "일정의 종료 시간이 시작 시간보다 빠릅니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 삭제 버튼 클릭 시 일정 삭제
        Button deleteBtn = findViewById(R.id.delete);

        // 일정 추가 상태일때 텍스트 색 변경
        if (CalendarData.getEditorState() == EditorState.INSERT)
            deleteBtn.setTextColor(Color.parseColor("#888888"));

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CalendarData.getEditorState() == EditorState.UPDATE) { // db를 업데이트 할 때
                    registerScheduleRemoverDialog(); // 삭제 다이얼로그 등록
                }
            }
        });

    }

    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // 4. 마지막으로 알려진 위치(location 객체)를 얻음.
                if (location != null) {
                    CalendarData.setLat(location.getLatitude()); // 위도 저장
                    CalendarData.setLng(location.getLongitude()); // 경도 저장
                    
                }
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

        // 현재 위치 or 저장된 위치로 카메라 이동
        LatLng find = new LatLng(CalendarData.getLat(), CalendarData.getLng());
        googleMap.addMarker(new MarkerOptions().position(find)); 
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(find, 15));
        
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

        String date = CalendarUtils.dateFormat(); // 현재 시간을 문자열로 포멧팅
        EditText title = (EditText)findViewById(R.id.title);
        EditText location = (EditText)findViewById(R.id.location);
        EditText note = (EditText)findViewById(R.id.note);

        TimePicker startTime = findViewById(R.id.timepicker1);
        TimePicker endTime = findViewById(R.id.timepicker2);

        dbHelper.insertCalendarData(date, startTime.getCurrentHour(), startTime.getCurrentMinute(), endTime.getCurrentHour(), endTime.getCurrentMinute(),
                 title.getText().toString(), CalendarData.getLat(), CalendarData.getLng(), note.getText().toString());

    }

    // db 업데이트
    private void updateData(String id) {

        String date = CalendarUtils.dateFormat(); // 현재 시간을 문자열로 포멧팅
        EditText title = (EditText)findViewById(R.id.title);
        EditText location = (EditText)findViewById(R.id.location);
        EditText note = (EditText)findViewById(R.id.note);

        TimePicker startTime = findViewById(R.id.timepicker1);
        TimePicker endTime = findViewById(R.id.timepicker2);

        dbHelper.updateCalendarData(id, date, startTime.getCurrentHour(), startTime.getCurrentMinute(), endTime.getCurrentHour(), endTime.getCurrentMinute(),
                title.getText().toString(), CalendarData.getLat(), CalendarData.getLng(), note.getText().toString());
    }
    
    // data 삭제
    private void deleteData(String id) {
        dbHelper.deleteCalendarData(id);
    }

    private void registerScheduleRemoverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("일정을 삭제할까요?");

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(CalendarData.getId()); // id에 해당하는 정보를 db에서 삭제
                Toast.makeText(MainActivity.getInstance(), "일정을 삭제하였습니다", Toast.LENGTH_SHORT).show();
                finish(); // 현재 액티비티 종료
                restartMainActivity(); // 메인 액티비티 재시작
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void restartMainActivity() {
        finish();
        startActivity(getIntent().setClass(this, MainActivity.class).putExtra("view", CalendarData.getFragmentID().getID()));
    }

}