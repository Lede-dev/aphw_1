package com.example.aphw_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.aphw_1.data.CurrentTime;
import com.example.aphw_1.fragments.MonthViewFragment;
import com.example.aphw_1.fragments.WeekViewFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 메뉴 클릭 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        CurrentTime currentTime = new CurrentTime(); // 현재 시간 로드
        Intent intent = new Intent(this, MainActivity.class);  // 인텐트 생성

        // intent에 전달할 년/월 입력
        intent.putExtra("year", currentTime.getYear());
        intent.putExtra("month", currentTime.getMonth());


        switch (item.getItemId()) {
            // 설정 아이콘 클릭 시
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "action_settings", Toast.LENGTH_SHORT).show();
                return true;

            // 월간 달력 전환
            case R.id.action_monthview:
                intent.putExtra("view", "month"); // intent에 전달할 fragment 입력
                startActivity(intent); // 새로운 Activity 시작
                return true;

            // 주간 달력 전환
            case R.id.action_weekview:
                intent.putExtra("view", "week"); // intent에 전달할 fragment 입력
                startActivity(intent); // 새로운 Activity 시작
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.new_activity_main); // Fragment를 불러오기 위해 새로 제작된 xml 사용

        Intent intent = getIntent();  // 인텐트를 받아 사용

        int year = intent.getIntExtra("year", -1);  // 값을 읽음, 해당 되는 이름의 벨류가 없을 때 -1 리턴
        int month = intent.getIntExtra("month", -1);  // 값을 읽음, 해당 되는 이름의 벨류가 없을 때 -1 리턴

        if (year == -1 || month == -1) {  // year나 month가 -1이면
            month = Calendar.getInstance().get(Calendar.MONTH);  // 현재 달을 가져옴
            year = Calendar.getInstance().get(Calendar.YEAR);    // 현재 년도를 가져옴
        }

        // 년/월 정보를 담고있는 CurrentTime 객체 생성
        CurrentTime currentTime = new CurrentTime(year, month);

        // 툴 바를 앱 바로 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar); // 툴 바 로드
        myToolbar.setTitle(currentTime.getYear() + "년 " + (currentTime.getMonth()+1) +"월"); // 툴 바의 타이틀, 불러온 달은 0~11월 이기 때문에 (month+1)을 하여 1~12월로 현재 달을 출력
        setSupportActionBar(myToolbar); // 툴 바를 앱 바로 설정

        // 출력할 Fragment 생성
        String view = intent.getStringExtra("view"); // 출력할 fragment 설정

        // 출력할 뷰가 WeekView
        if (view != null && view.equalsIgnoreCase("week")) getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new WeekViewFragment()).commit();
        
        // 출력할 뷰가 MonthView거나 설정되어있지 않음
        else getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MonthViewFragment()).commit();


        /* 변경됨
         TextView yearMonth = findViewById(R.id.year_month);  // 아이디에 해당하는 텍스트 개체를 찾음
         yearMonth.setText(year + "년 " + (month + 1) + "월"); // 텍스트를 입력
         */

        /* 변경됨


        // GridView 생성
        List<Integer> days = UtlCalendar.getDays(year, month); // 날자 데이터 로드
        AdpCalendar adapt = new AdpCalendar(); // 커스텀 어댑터 객체 로드
        for (Integer day : days){
            adapt.addItem(year, month, day); // 어댑터에 데이터 추가
        }
        GridView calendar = findViewById(R.id.d_grid); // 그리드 뷰 객체 로드
        calendar.setAdapter(adapt); // 어댑터를 그리드 뷰 객체에 연결

        // 일을 클릭했을 때
        calendar.setOnItemClickListener(new GridView.OnItemClickListener() {   // 이 함수를 통해 이벤트 리스너 등록

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int firstDayOfMonth = UtlCalendar.getFirstDay(year, month); // 첫번째 일의 요일 1 ~ 7 (일 ~ 토)
                int daysOfMonth = UtlCalendar.getDay(year, month); // 해당 월의 총 일 수

                int day = position - firstDayOfMonth + 2; // 클릭한 일을 계산

                if (day < 1 || day > daysOfMonth) return; // 계산된 일이 달력에 포함된 범위를 벗어났다면 종료

                Toast.makeText(MainActivity.this, year + "." +
                        (month + 1) + "." + day, 0).show();      // toast message로 띄울 text

                if (tempView == null) tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정
                tempView.setBackground(getResources().getDrawable(R.drawable.calendar_border_disable)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                view.setBackground(getResources().getDrawable(R.drawable.calendar_border_enable)); // 클릭한 일의 배경 색을 변경
                tempView = view; // 클릭한 뷰를 임시 뷰로 지정
            }
        });
        
         */

        // 다음 버튼을 눌렀을 때

        /* 변경됨
        Button nextBtn = findViewById(R.id.next);  // ID로부터 대응되는 객체를 찾음
        nextBtn.setOnClickListener(new View.OnClickListener() {  // 이 함수를 통해 이벤트 리스너 등록

            @Override
            public void onClick(View v) {

                if (++month > 11) { // month를 1 증가시킨 값이 범위를 벗어나는지 확인
                    month = 0; // month 를 1월로 설정
                    year++; // year를 1 증가
                }

                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("year", year);       //extra에 year 값 저장
                intent.putExtra("month", month);  // extra에 month 값 저장
                startActivity(intent); //새 액티비티의 인스턴스를 시작
                finish(); // 종료

            }
        });


        // 이전 버튼을 눌렀을 때

        Button backBtn = findViewById(R.id.back); // ID로부터 대응되는 객체를 찾음
        backBtn.setOnClickListener(new View.OnClickListener() {  // 이 함수를 통해 이벤트 리스너 등록

            @Override
            public void onClick(View v) {

                if (--month < 0) { // month를 1 감소시킨 값이 범위를 벗어나는지 확인
                    month = 11; // month 를 12월로 설정
                    year--; // year를 1 감소
                }

                Intent intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                intent.putExtra("year", year);        // extra에 year 값 저장
                intent.putExtra("month", month);   // extra에 month 값 저장
                startActivity(intent); // 새 액티비티의 인스턴스를 시작
                finish(); // 종료

            }
        });
        */
    }

}




