package com.example.aphw_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import android.widget.Toast;

import com.example.aphw_1.adapters.AdpCalendar;
import com.example.aphw_1.utils.UtlCalendar;

import java.util.Calendar;
import java.util.List;

public class MonthViewActivity extends AppCompatActivity {

    int year;
    int month;

    View tempView;

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

                Toast.makeText(MonthViewActivity.this, year + "." +
                        (month + 1) + "." + day, 0).show();      // toast message로 띄울 text

                if (tempView == null) tempView = view; // 이전에 클릭한 뷰가 없다면 임시 뷰를 현재 뷰로 지정
                tempView.setBackground(getResources().getDrawable(R.drawable.calendar_border_disable)); // 이전에 클릭한 뷰를 테두리 비활성화 상태로 변경
                view.setBackground(getResources().getDrawable(R.drawable.calendar_border_enable)); // 클릭한 일의 배경 색을 변경
                tempView = view; // 클릭한 뷰를 임시 뷰로 지정
            }
        });

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

    }

}




