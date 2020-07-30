package com.kaushik.kapil.agecalculator;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NUMBER = "com.kaushik.kapil.agecalculator.EXTRA_NUMBER";
    public static final String app_link = "https://drive.google.com/drive/folders/1Rv0YOat3kTSwshoVQVy_xe13pxKaPId2?usp=sharing";
    static String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    @SuppressLint("StaticFieldLeak")
    static EditText Bdays , Bmonths , Byears,Tdays,Tmonths,Tyears;
    @SuppressLint("StaticFieldLeak")
    static TextView dayToday,dayToday2;
    ImageView Bcal,Tcal;
    Button calculate,clear;
    ImageButton coffee;
    Toolbar toolbar;
    View view;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        Tdays =  findViewById(R.id.Tdays);
        Tmonths =  findViewById(R.id.Tmonths);
        Tyears =  findViewById(R.id.Tyears);
        Bdays =  findViewById(R.id.Bdays);
        Bmonths = findViewById(R.id.Bmonths);
        Byears =  findViewById(R.id.Byears);
        calculate =  findViewById(R.id.calculate);
        clear = findViewById(R.id.clear);
        coffee = findViewById(R.id.coffee);
        coffee.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse("https://www.buymeacoffee.com/kapil"));
                startActivity(i);
            }
        });
        dayToday = findViewById(R.id.dayToday);
        dayToday2=findViewById(R.id.dayToday2);
        dayToday2.setVisibility(View.GONE);
        Tcal = findViewById(R.id.tcal);
        Bcal = findViewById(R.id.bcal);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        Tdays.setText(Integer.toString(calendar.get(5)));
        Tmonths.setText(Integer.toString(month));
        Tyears.setText(Integer.toString(year));
        dayToday.setText(weeks[calendar.get(7) - 1]);
        Tcal.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new DatePickerFragment1().show(getSupportFragmentManager(), "DATE_PICKER_1");
            }
        });
        Bcal.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new DatePickerFragment2().show(getSupportFragmentManager(), "DATE_PICKER_1");
            }
        });
        clear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String str = "";
                Bdays.setText(str);
                Bmonths.setText(str);
                Byears.setText(str);
                dayToday2.setVisibility(View.GONE);
            }
        });

        calculate.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) throws NumberFormatException {
                String str = "";
                int tday = Integer.parseInt(Tdays.getText().toString());
                int tmonth = Integer.parseInt(Tmonths.getText().toString());
                int tyear = Integer.parseInt(Tyears.getText().toString());
                try {
                    int bday = Integer.parseInt(Bdays.getText().toString());
                    int bmonth = Integer.parseInt(Bmonths.getText().toString());
                    int byear = Integer.parseInt(Byears.getText().toString());
                    LocalDate T = LocalDate.of(tyear, tmonth, tday);
                    LocalDate B = LocalDate.of(byear, bmonth, bday);
                    if (T.isBefore(B)) {
                        try {
                            Toast.makeText(MainActivity.this, "Invalid Birth Date", Toast.LENGTH_SHORT).show();
                            Bdays.setText(str);
                            Bmonths.setText(str);
                            Byears.setText(str);
                            dayToday2.setVisibility(View.GONE);
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, "Please fill all entries", Toast.LENGTH_SHORT).show();
                        } catch (DateTimeException e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Bdays.setText(str);
                            Bmonths.setText(str);
                            Byears.setText(str);
                            dayToday2.setVisibility(View.GONE);
                        }
                    } else {
                        Period period = Period.between(B, T);
                        int days = period.getDays();
                        int months = period.getMonths();
                        int years = period.getYears();
                        ArrayList<Integer> arrayList = new ArrayList<>(6);
                        arrayList.add(years);
                        arrayList.add(months);
                        arrayList.add(days);
                        arrayList.add(Integer.parseInt(String.valueOf(bmonth)));
                        arrayList.add(Integer.parseInt(String.valueOf(bday)));
                        arrayList.add(Integer.parseInt(String.valueOf(byear)));
                        try {
                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                            intent.putExtra(EXTRA_NUMBER, arrayList);
                            startActivity(intent);
                        } catch (DateTimeException e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Bdays.setText(str);
                            Bmonths.setText(str);
                            Byears.setText(str);
                            dayToday2.setVisibility(View.GONE);
                        }
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Please fill all entries", Toast.LENGTH_SHORT).show();
                } catch (DateTimeException e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Bdays.setText(str);
                    Bmonths.setText(str);
                    Byears.setText(str);
                    dayToday2.setVisibility(View.GONE);
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about :
                        about();
                        return true;
                    case R.id.feedback :
                        try {
                            feedback();
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.rate :
                        rateUs();
                        return true;
                    case R.id.share :
                        share();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void onBackPressed() {
        Snackbar.make(this.view, (CharSequence) "Are you sure want to exit :(  ?", 1000).setAction((CharSequence) "Exit", (OnClickListener) new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        }).show();
    }

    public void feedback() throws UnsupportedEncodingException {
        String url = "https://api.whatsapp.com/send?phone=+918826218854&text=" +
                URLEncoder.encode("Feedback for Age Calculator Application\n\n", "UTF-8");
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void rateUs() {
        Toast.makeText(this, "link coming soon", Toast.LENGTH_SHORT).show();
    }

    public void share() {
        Intent shareintent = new Intent("android.intent.action.SEND");
        shareintent.setType("text/plain");
        shareintent.putExtra("android.intent.extra.SUBJECT", "subject");
        shareintent.putExtra("android.intent.extra.TEXT", app_link);
        startActivity(Intent.createChooser(shareintent, "Share Via"));
    }

    public void about() {
        Intent likeIng = new Intent("android.intent.action.VIEW", Uri.parse("https://www.instagram.com/i_likekapil/"));
        likeIng.setPackage("com.instagram.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "search i_likekapil in Instagram", Toast.LENGTH_SHORT).show();
        }
    }

    public static class DatePickerFragment1 extends DialogFragment implements OnDateSetListener {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, calendar.get(1), calendar.get(2), calendar.get(5));
        }

        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(1, year);
            c.set(2, month);
            c.set(5, dayOfMonth);
            dayToday.setText(MainActivity.weeks[c.get(7) - 1]);
            Tdays.setText(Integer.toString(dayOfMonth));
            Tmonths.setText(Integer.toString(month + 1));
            Tyears.setText(Integer.toString(year));
        }
    }

    public static class DatePickerFragment2 extends DialogFragment implements OnDateSetListener {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),this, calendar.get(1), calendar.get(2), calendar.get(5));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(1, year);
            c.set(2, month);
            dayToday2.setVisibility(View.VISIBLE);
            dayToday2.setText(MainActivity.weeks[c.get(7) - 1]);
            Bdays.setText(Integer.toString(dayOfMonth));
            Bmonths.setText(Integer.toString(month + 1));
            Byears.setText(Integer.toString(year));
        }
    }

}
