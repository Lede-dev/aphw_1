package com.example.aphw_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aphw_1.adapters.MonthViewPagerAdapter;
import com.example.aphw_1.adapters.WeekViewPagerAdapter;
import com.example.aphw_1.data.CalendarData;
import com.example.aphw_1.fragments.MonthViewBarFragment;
import com.example.aphw_1.fragments.WeekViewBarFragment;
import com.example.aphw_1.utils.CalendarUtils;
import com.example.aphw_1.utils.EditorState;
import com.example.aphw_1.utils.FragmentID;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    // 외부 class에서 사용하기 위한 Instance 반환
    public static MainActivity getInstance(){
        return instance;
    }

    // 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 주간-월간 전환 intent // 이전-이후 년월 전환 intent
    public Intent createIntent(int year, int month, FragmentID view, Class cls) {
        return createIntent(year, month, view.getID(), cls);
    }

    public Intent createIntent(int year, int month, int view, Class cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("view", view);
        return intent;
    }

    // 메뉴 클릭 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        CalendarData calendarData = new CalendarData(); // 현재 년/월 로드
        int id = getIntent().getIntExtra("view", FragmentID.MONTH.getID()); // 출력중인 fragment id 불러오기

        switch (item.getItemId()) {
            //이전 년/월로 이동
            case R.id.left_button:

                // 월간 fragment일 때
                if (id == FragmentID.MONTH.getID()) {
                    startActivity(createIntent(calendarData.getYear()-1, calendarData.getMonth(), FragmentID.MONTH, MainActivity.class)); // 새로운 Intent를 만들어서 Activity 시작
                    finish(); // 기존 Activity 종료
                }
                
                // 주간 fragment일 때
                else if (id == FragmentID.WEEK.getID()) {
                    int month = calendarData.getMonth() - 1;
                    int year = calendarData.getYear();
                    if (month < 0) {
                        year--; month = 11;
                    }
                    startActivity(createIntent(year, month, FragmentID.WEEK, MainActivity.class)); // 새로운 Intent를 만들어 Activity 시작
                    finish(); // 기존 Activity 종료
                }
                return true;

            // 이후 년/월로 이동
            case R.id.right_button:

                if (id == FragmentID.MONTH.getID()) {
                    startActivity(createIntent(calendarData.getYear()+1, calendarData.getMonth(), FragmentID.MONTH, MainActivity.class)); // 새로운 Intent를 만들어 Activity 시작
                    finish(); // 기존 Activity 종료
                }
                else if (id == FragmentID.WEEK.getID()) {
                    int month = calendarData.getMonth() + 1;
                    int year = calendarData.getYear();
                    if (month > 11) {
                        year++; month = 0;
                    }
                    startActivity(createIntent(year, month, FragmentID.WEEK, MainActivity.class)); // 새로운 Intent를 만들어 Activity 시작
                    finish(); // 기존 Activity 종료
                }
                return true;

            // 월간 달력 전환
            case R.id.action_monthview:
                startActivity(createIntent(calendarData.getYear(), calendarData.getMonth(), FragmentID.MONTH, MainActivity.class)); // 새로운 Intent를 만들어 Activity 시작
                finish(); // 기존 Activity 종료
                return true;

            // 주간 달력 전환
            case R.id.action_weekview:
                startActivity(createIntent(calendarData.getYear(), calendarData.getMonth(), FragmentID.WEEK, MainActivity.class)); // 새로운 Intent를 만들어 Activity 시작
                finish(); // 기존 Activity 종료
                return true;
            
            // 일정 입력 액티비티
            case R.id.calendar:
                if (CalendarData.getDay() == -1) {
                    Toast.makeText(MainActivity.getInstance(), "일정을 추가할 위치를 선택해주세요" ,Toast.LENGTH_SHORT).show();      // toast message로 띄울 text
                    return true;
                }
                startActivity(createIntent(calendarData.getYear(), calendarData.getMonth(), id, Calendar_Setting_activity.class));
                CalendarData.setEditorState(EditorState.INSERT); // 일정 추가
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 화면 회전 리스너
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // 세로모드 변경
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            // 세로모드일 때 캘린더 높이 오차 설정
            CalendarUtils.setErrorHeight();

            
        }

        // 가로모드 변경
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // 가로모드일 때 캘린더 높이 오차 설정
            CalendarUtils.setErrorHeightInLandScape();

        }

    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_main); // Fragment를 불러오기 위해 새로 제작된 new_activity_main.xml 사용

        // Activity가 생성될 때 생성된 Activtiy를 저장, 다른 class에서 Activity에 접근하기 위함
        instance = this;

        // 가로모드일 때 달력 높이 오차 설정
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CalendarUtils.setErrorHeightInLandScape();
        }

        // 세로모드일 때 달력 높이 오차 설정
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            CalendarUtils.setErrorHeight();
        }

        Intent intent = getIntent();  // 인텐트를 받아 사용

        int year = intent.getIntExtra("year", Calendar.getInstance().get(Calendar.YEAR));  // 값을 읽음, 해당 되는 이름의 벨류가 없을 때 현재 년도를 리턴
        int month = intent.getIntExtra("month", Calendar.getInstance().get(Calendar.MONTH));  // 값을 읽음, 해당 되는 이름의 벨류가 없을 때 현재 달을 리턴

        CalendarData.setYear(year); // 현재 년도를 설정
        CalendarData.setMonth(month); // 현재 월을 설정
        CalendarData.setDay(-1); // 현재 일을 선택되지 않은 상태로 초기화

        // 툴 바를 앱 바로 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar); // 툴 바 로드
        myToolbar.setTitle(CalendarData.getYear() + "년 " + (CalendarData.getMonth()+1) +"월"); // 툴 바의 타이틀, 불러온 달은 0~11월 이기 때문에 (month+1)을 하여 1~12월로 현재 달을 출력
        setSupportActionBar(myToolbar); // 툴 바를 앱 바로 설정

        // 출력할 Fragment 생성
        int view = intent.getIntExtra("view", FragmentID.MONTH.getID()); // 출력할 fragmentID 로드, 해당 되는 이름의 벨류가 없을 때 MonthView ID 리턴

        // 출력할 뷰가 MonthView 일 때
        if (view == FragmentID.MONTH.getID()){

            // Bar Fragment 생성
            getSupportFragmentManager().beginTransaction().add(R.id.bar_container, new MonthViewBarFragment()).commit();

            // pager adapter 객체 설정
            ViewPager2 vpPager = findViewById(R.id.vpPager); // pager 로드
            FragmentStateAdapter adapter = new MonthViewPagerAdapter(this); // pager adapter 로드
            vpPager.setAdapter(adapter); // pager와 adapter를 연결

            // 출력할 페이지를 현재 달에 맞는 페이지로 설정
            vpPager.setCurrentItem(month);

            // pager callback
            vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    CalendarData.setMonth(position); // 현재 월을 저장
                    CalendarData.setPage(position); // pager 페이지를 저장
                    myToolbar.setTitle(CalendarData.getYear() + "년 " + (CalendarData.getMonth()+1) +"월"); // 툴 바의 타이틀, 불러온 달은 0~11월 이기 때문에 (month+1)을 하여 1~12월로 현재 달을 출력

                }
            });

        }

        // 출력할 뷰가 WeekView 일 때
        else if (view == FragmentID.WEEK.getID()){

            // Bar Fragment 생성
            getSupportFragmentManager().beginTransaction().add(R.id.bar_container, new WeekViewBarFragment()).commit();

            // pager adapter 객체 설정
            ViewPager2 vpPager = findViewById(R.id.vpPager); // pager 로드
            FragmentStateAdapter adapter = new WeekViewPagerAdapter(this); // pager adapter 로드
            vpPager.setAdapter(adapter); // pager와 adapter를 연결

            int day = Calendar.getInstance().get(Calendar.DATE); // 현재 날짜를 로드
            int firstDayOfWeek = CalendarUtils.getFirstDay(year, month); // 현재 월의 첫번째 일을 로드
            int page = (day + firstDayOfWeek)/7; // 출력할 페이지 계산

            // 출력할 페이지를 현재 날짜에 맞는 페이지로 설정
            vpPager.setCurrentItem(page);
            CalendarData.setPage(page); // 현재 페이지 위치 저장

            // pager callback
            vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    CalendarData.setPage(position); // pager 페이지를 저장
                }
            });

        }

    }

}




