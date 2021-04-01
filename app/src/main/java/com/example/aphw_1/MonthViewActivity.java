package com.example.aphw_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthViewActivity extends AppCompatActivity {

    int year;
    int month;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();  // 인텐트를 받아 사용

        year = intent.getIntExtra("year", -1);   // 값을 읽음, 해당 되는 이름의 벨류가 없을 때 -1 리턴
        month = intent.getIntExtra("month", -1);  // 값을 읽음, 해당 되는 이름의 벨류가 없을 때 -1 리턴

        if (year == -1 || month == -1) {  // year나 month가 -1이면
            month = Calendar.getInstance().get(Calendar.MONTH);  // 현재 달을 가져옴
            year = Calendar.getInstance().get(Calendar.YEAR);    // 현재 년도를 가져옴

        }


        TextView yearMonth = findViewById(R.id.year_month);  // 아이디에 해당하는 텍스트 개체를 찾음
        yearMonth.setText(year + "년 " + (month + 1) + "월"); // 텍스트를 입력


        // GridView 생성
        List<String> days = getDays(year, month); // 날자 데이터 로드
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days); // 어댑터 생성
        GridView calendar = findViewById(R.id.d_grid); // 그리드 뷰 객체 로드
        calendar.setAdapter(adapt); // 어댑터를 그리드 뷰 객체에 연결


        // 다음 버튼을 눌렀을 때

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


        // 일을 클릭했을 때

        GridView DGrid = findViewById(R.id.d_grid);    // ID로부터 대응되는 객체를 찾음
        DGrid.setOnItemClickListener(new GridView.OnItemClickListener() {   // 이 함수를 통해 이벤트 리스너 등록

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(MonthViewActivity.this, year + "." +
                        (month + 1) + "." + position, 0).show();      // toast massage로 띄울 text

            }
        });
    }


    /**
     * 입력한 년도가 윤년인지 판단, 윤년이라면 true를 반환한다.
     *
     * @param year
     * @return boolean
     */

    private Boolean isLeapYear(Integer year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) return true;
        return false;
    }

    /**
     * 입력한 년도, 입력한 월의 첫번째 날자의 요일을 반환.
     *
     * @param year
     * @param month 0 ~ 11
     * @return int 1 ~ 7 (일 ~ 토)
     */

    private Integer getFirstDay(int year, int month) {
        Calendar cal = java.util.Calendar.getInstance(); // 새로운 Calendar 객체 생성
        cal.set(Calendar.YEAR, year); // 입력받은 년도를 Calendar 객체에 입력
        cal.set(Calendar.MONTH, month); // 입력받은 월을 Calendar 객체에 입력
        cal.set(Calendar.DATE, 1); // 입력받은 년도, 월의 첫번째 일을 Calendar 객체에 입력
        return cal.get(Calendar.DAY_OF_WEEK); // 입력받은 월의 첫번째 일의 요일을 반환
    }

    /**
     * 입력한 년도, 입력한 월의 마지막 날자의 요일을 반환
     *
     * @param year
     * @param month 0 ~ 11
     * @return int 1 ~ 7 (일 ~ 토)
     */

    private Integer getLastDay(int year, int month) {
        Calendar cal = java.util.Calendar.getInstance(); // 새로운 Calendar 객체 생성
        cal.set(Calendar.YEAR, year); // 입력받은 년도를 Calendar 객체에 입력
        cal.set(Calendar.MONTH, month); // 입력받은 월을 Calendar 객체에 입력
        cal.set(Calendar.DATE, getDay(year, month)); // 입력받은 년도, 월의 마지막 일을 Calendar 객체에 입력
        return cal.get(Calendar.DAY_OF_WEEK); // 입력받은 월의 마지막 일의 요일을 반환
    }

    /**
     * 입력한 년도, 입력한 월이 몇 일 까지 존재하는지 반환
     *
     * @param year
     * @param month 0 ~ 11
     * @return int 28, 29, 30, 31
     */
    private Integer getDay(int year, int month) {
        if (month == 4 || month == 6 || month == 9 || month == 11) return 30; // 4, 6, 9, 11 : 30일
        if (month == 2) { // 2월 윤년 : 29일, 2월 윤년X : 28일
            if (isLeapYear(year)) return 29;
            return 28;
        }
        return 31; // 나머지 31일
    }

    /**
     * 7 x 7 크기의 GridView에 출력할 날자가 포함된 배열을 생성
     *
     * @param year
     * @param month 0 ~ 11
     * @return String[49]
     */
    private List<String> getDays(int year, int month) {
        int firstDayOfMonth = getFirstDay(year, month); // 첫번째 일의 요일 1 ~ 7 (일 ~ 토)
        int daysOfMonth = getDay(year, month + 1); // 해당 월의 총 일 수

        System.out.println(firstDayOfMonth);

        List<String> days = new ArrayList<>(); // 날자 리스트 생성

        for (int i = 0; i < firstDayOfMonth - 1; i++) {
            days.add(""); // 리스트에 첫번째 날자 이전의 공백 추가
        }


        for (int j = 1; j <= daysOfMonth; j++) {
            days.add(Integer.toString(j)); // 리스트에 첫번째 날자부터 추가
        }

        return days;
    }

}




