package com.example.customdatepicker2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener{

    private Date selectedDateGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowCalendar = findViewById(R.id.btnShowCalendar);
        btnShowCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


// Sample Input 2: Range from March 2024 to May 2024
                Calendar minDate2 = Calendar.getInstance();
                minDate2.set(Calendar.YEAR, 2024);
                minDate2.set(Calendar.MONTH, Calendar.JANUARY);
                minDate2.set(Calendar.DAY_OF_MONTH, 1);

                Calendar maxDate2 = Calendar.getInstance();
                maxDate2.set(Calendar.YEAR, 2024);
                maxDate2.set(Calendar.MONTH, Calendar.MARCH);
                maxDate2.set(Calendar.DAY_OF_MONTH, 31);

                if(selectedDateGlobal==null){
                    // Initialize the current date to the current system date without time
                    Calendar currentDateCalendar = Calendar.getInstance();
                    // Initialize the selected date to today
                    selectedDateGlobal = currentDateCalendar.getTime();
                }
                CalendarDialog calendarDialog2 = new CalendarDialog(minDate2, maxDate2,selectedDateGlobal);
                calendarDialog2.setOnDateSelectedListener(MainActivity.this);
                calendarDialog2.show(getSupportFragmentManager(), "customDialog");
            }
        });

    }

    @Override
    public void onDateSelected(Date selectedDate) {
        if (selectedDate != null) {
            selectedDateGlobal=selectedDate;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = sdf.format(selectedDate);
            Toast.makeText(this, "Selected Date: " + formattedDate, Toast.LENGTH_SHORT).show();
        }
    }
}