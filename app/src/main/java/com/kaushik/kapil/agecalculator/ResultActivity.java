package com.kaushik.kapil.agecalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView Age_d,Age_m,Age_y,Extra_days,Extra_hours,Extra_minutes,Extra_months,Extra_second,Extra_weeks,Extra_years, Next_days, Next_months;
    ImageButton back,shareData;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Age_y = findViewById(R.id.Age_years);
        Age_m = findViewById(R.id.Age_months);
        Age_d = findViewById(R.id.Age_days);
        Next_months = findViewById(R.id.Next_months);
        Next_days = findViewById(R.id.Next_days);
        Extra_years = findViewById(R.id.Extra_years);
        Extra_months = findViewById(R.id.Extra_months);
        Extra_weeks = findViewById(R.id.Extra_weeks);
        Extra_days = findViewById(R.id.Extra_days);
        Extra_hours =findViewById(R.id.Extra_hours);
        Extra_minutes = findViewById(R.id.Extra_minutes);
        Extra_second = findViewById(R.id.Extra_second);
        shareData = findViewById(R.id.shareData);
        back= findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResultActivity.this.onBackPressed();
            }
        });
        Intent intent = getIntent();
        ArrayList<Integer> arrayList = intent.getIntegerArrayListExtra(MainActivity.EXTRA_NUMBER);
        assert arrayList != null;
        int years = arrayList.get(0);
        int months = (years * 12) + arrayList.get(1);
        long days = Math.round((long) arrayList.get(2) + (((double) months) * 30.436875d));
        int weeks = (int) (days / 7);
        long hours = 24 * days;
        long min = hours * 60;
        long sec = 60 * min;
        int nextbirthmonth = arrayList.get(3);
        int nextbirthday = arrayList.get(4);
        int nextyear = arrayList.get(5);
        String str = "/";
        final String ShareDataString = "My birthdate is " +
                nextbirthday +
                str +
                nextbirthmonth +
                str +
                nextyear +
                ".\nI am " +
                arrayList.get(0) +
                " Years " +
                arrayList.get(1) +
                " Months " +
                arrayList.get(2) +
                " Days Old.\nDownload this app to calculate your age => " +
                MainActivity.app_link;
        shareData.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent shareintent = new Intent("android.intent.action.SEND");
                shareintent.setType("text/plain");
                shareintent.putExtra("android.intent.extra.SUBJECT", "subject");
                shareintent.putExtra("android.intent.extra.TEXT", ShareDataString);
                startActivity(Intent.createChooser(shareintent, "Share Via"));
            }
        });
        LocalDate today = LocalDate.now();
        LocalDate nextBday = LocalDate.of(nextyear, nextbirthmonth, nextbirthday).withYear(today.getYear());
        if (nextBday.isBefore(today) || nextBday.isEqual(today)) {
            nextBday = nextBday.plusYears(1);
        }
        Period p = Period.between(today, nextBday);
        Next_months.setText(Integer.toString(p.getMonths()));
        Next_days.setText(Integer.toString(p.getDays()));
        Age_y.setText(Integer.toString(years));
        Age_m.setText(Integer.toString(arrayList.get(1)));
        Age_d.setText(Integer.toString(arrayList.get(2)));
        Extra_years.setText(Integer.toString(years));
        Extra_months.setText(Integer.toString(months));
        Extra_days.setText(Integer.toString(Math.round(days)));
        Extra_hours.setText(Long.toString(Math.round(hours)));
        Extra_weeks.setText(Integer.toString(Math.round(weeks)));
        Extra_minutes.setText(Long.toString(Math.round(min)));
        Extra_second.setText(Long.toString(Math.round(sec)));
    }
}
