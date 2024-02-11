package com.example.customdatepicker2;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarDialog extends DialogFragment {
    private Calendar calendar;
    private Calendar minDate;
    private Calendar maxDate;
    private Date selectedDate;
    private RecyclerView recyclerViewDialogDates;

    private ViewPager2 viewPagerDialogDates;

    private OnDateSelectedListener onDateSelectedListener;

    private GestureDetector gestureDetector;

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.onDateSelectedListener = listener;
    }

    public CalendarDialog(Calendar minDate, Calendar maxDate,Date selectedDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calendar, container, false);
        TextView tvDialogMonthYear = view.findViewById(R.id.tvDialogMonthYear);
        Button btnDialogPrevMonth = view.findViewById(R.id.btnDialogPrevMonth);
        Button btnDialogNextMonth = view.findViewById(R.id.btnDialogNextMonth);
        Button btnDialogOK = view.findViewById(R.id.btnDialogOK);
        Button btnDialogCancel = view.findViewById(R.id.btnDialogCancel);

        if (selectedDate != null) {
            calendar.setTime(selectedDate);
        } else {
            calendar.setTime(minDate.getTime());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        tvDialogMonthYear.setText(formattedDate);

        viewPagerDialogDates = view.findViewById(R.id.viewPagerDialogDates);
        viewPagerDialogDates.setAdapter(new DatePagerAdapter(getMonthDays(calendar)));
        viewPagerDialogDates.setPageTransformer(new DepthPageTransformer());

        viewPagerDialogDates.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                calendar.add(Calendar.MONTH, position);
                updateDialogMonthYear(tvDialogMonthYear, calendar);
                btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
                btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
            }
        });

        btnDialogPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                updateDialogMonthYear(tvDialogMonthYear, calendar);
                btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
                btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
            }
        });

        btnDialogNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                updateDialogMonthYear(tvDialogMonthYear, calendar);
                btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
                btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
            }
        });

        btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
        btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);

        btnDialogOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateSelectedListener.onDateSelected(DateAdapter.selectedDate);
                dismiss();
            }
        });
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
//        View view = inflater.inflate(R.layout.dialog_calendar, container, false);
//        TextView tvDialogMonthYear = view.findViewById(R.id.tvDialogMonthYear);
////        recyclerViewDialogDates = view.findViewById(R.id.recyclerViewDialogDates);
//        Button btnDialogPrevMonth = view.findViewById(R.id.btnDialogPrevMonth);
//        Button btnDialogNextMonth = view.findViewById(R.id.btnDialogNextMonth);
//        Button btnDialogOK=view.findViewById(R.id.btnDialogOK);
//        Button btnDialogCancel=view.findViewById(R.id.btnDialogCancel);
//
//        if(selectedDate!=null)
//        {
//            calendar.setTime(selectedDate);
//        }
//        else
//        {
//            calendar.setTime(minDate.getTime());
//        }
//
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
//        String formattedDate = sdf.format(calendar.getTime());
//        tvDialogMonthYear.setText(formattedDate);
//
//
//        viewPagerDialogDates = view.findViewById(R.id.viewPagerDialogDates);
//
//        // Set up ViewPager2 with a custom PageTransformer for smooth swiping effect
//        viewPagerDialogDates.setAdapter(new DatePagerAdapter(getMonthDays(calendar)));
//        viewPagerDialogDates.setPageTransformer(new DepthPageTransformer());
//
//        // Handle swiping directly within ViewPager2
//        viewPagerDialogDates.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                // Update the calendar and UI when a new page is selected
//                calendar.add(Calendar.MONTH, position);
//                updateDialogMonthYear(tvDialogMonthYear, calendar);
//                btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
//                btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
//            }
//        });
//
//
////        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
////        recyclerViewDialogDates.setLayoutManager(layoutManager);
//
//
//
//        // Handle swiping directly within RecyclerView
////        recyclerViewDialogDates.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
////            float startX = 0;
////
////            @Override
////            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
////                switch (e.getAction()) {
////                    case MotionEvent.ACTION_DOWN:
////                        startX = e.getX();
////                        break;
////
////                    case MotionEvent.ACTION_UP:
////                        float endX = e.getX();
////                        float deltaX = startX - endX;
////
////                        if (Math.abs(deltaX) > 50) { // Adjust this threshold as needed
////                            if (deltaX > 0 && !isAfterMaxDate(calendar)) {
////                                calendar.add(Calendar.MONTH, 1);
////                            } else if (deltaX < 0 && !isBeforeMinDate(calendar)) {
////                                calendar.add(Calendar.MONTH, -1);
////                            }
////
////                            updateDialogMonthYear(tvDialogMonthYear, calendar);
////                            ((DateAdapter) recyclerViewDialogDates.getAdapter()).updateDates(getMonthDays(calendar));
////
////                            // Hide or show buttons based on updated date
////                            btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
////                            btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
////                        }
////                        break;
////                }
////
////                return false;
////            }
////
////            @Override
////            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
////                // Optional: handle touch events
////            }
////
////            @Override
////            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
////                // Optional: handle request disallowing intercept touch event
////            }
////        });
////
////        DateAdapter adapter = new DateAdapter(getMonthDays(calendar));
////        adapter.setSelectedDate(selectedDate);
////        recyclerViewDialogDates.setAdapter(adapter);
//
//        btnDialogPrevMonth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calendar.add(Calendar.MONTH, -1);
//                updateDialogMonthYear(tvDialogMonthYear, calendar);
////                ((DateAdapter) recyclerViewDialogDates.getAdapter()).updateDates(getMonthDays(calendar));
//
//
//
//
//                // Hide or show buttons based on updated date
//                btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
//                btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
//            }
//        });
//
//        btnDialogNextMonth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calendar.add(Calendar.MONTH, 1);
//                updateDialogMonthYear(tvDialogMonthYear, calendar);
////                ((DateAdapter) recyclerViewDialogDates.getAdapter()).updateDates(getMonthDays(calendar));
//
//                // Hide or show buttons based on updated date
//                btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
//                btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
//            }
//        });
//        // Initial visibility of buttons based on the current date
//        btnDialogPrevMonth.setVisibility(isBeforeMinDate(calendar) ? View.GONE : View.VISIBLE);
//        btnDialogNextMonth.setVisibility(isAfterMaxDate(calendar) ? View.GONE : View.VISIBLE);
//
//        btnDialogOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onDateSelectedListener.onDateSelected(DateAdapter.selectedDate);
//                dismiss();
//            }
//        });
//        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        return view;
    }




    private void updateDialogMonthYear(TextView tvDialogMonthYear, Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        tvDialogMonthYear.setText(formattedDate);

        // Update the adapter with the new month and year
//        ((DateAdapter) recyclerViewDialogDates.getAdapter()).updateDates(getMonthDays(calendar));
        // Set the selected date again
//        ((DateAdapter) recyclerViewDialogDates.getAdapter()).setSelectedDate(selectedDate);
    }

//    private List<Date> getMonthDays(Calendar calendar) {
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
//        int daysToAdjust = (firstDayOfMonth - Calendar.SUNDAY + 7) % 7;
//
//        List<Date> monthDays = new ArrayList<>();
//
//        for (int i = 1; i <= daysToAdjust; i++) {
//            monthDays.add(null);
//        }
//
//        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        for (int i = 1; i <= maxDays; i++) {
//            Date date = calendar.getTime();
//            date.setDate(i);
//
//            if (!date.before(minDate.getTime()) && !date.after(maxDate.getTime())) {
//                monthDays.add(date);
//            } else {
//                monthDays.add(null);
//            }
//
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        calendar.add(Calendar.MONTH, -1);
//        return monthDays;
//    }

    private List<List<Date>> getMonthDays(Calendar calendar) {
        List<List<Date>> allMonths = new ArrayList<>();

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        do {
            List<Date> monthDays = new ArrayList<>();
            int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
            int daysToAdjust = (firstDayOfMonth - Calendar.SUNDAY + 7) % 7;

            for (int i = 1; i <= daysToAdjust; i++) {
                monthDays.add(null);
            }

            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= maxDays; i++) {
                Date date = calendar.getTime();
                date.setDate(i);
                monthDays.add(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            allMonths.add(monthDays);

            // Move to the next month
            calendar.add(Calendar.MONTH, 1);
            Log.d( "getMonthDays: ",calendar.get(Calendar.MONTH)+"");
            Log.d( "getMonthDays: ",maxDate.get(Calendar.MONTH)+"");
            Log.d( "getMonthDays: ",calendar.get(Calendar.YEAR)+"");
            Log.d( "getMonthDays: ",maxDate.get(Calendar.YEAR)+"");
        } while ((calendar.get(Calendar.MONTH)<=maxDate.get(Calendar.MONTH))&&(calendar.get(Calendar.YEAR)<=maxDate.get(Calendar.YEAR))); // Change the condition here

        return allMonths;
    }



    private boolean isBeforeMinDate(Calendar date) {
        // Check if the date is before the minDate
        return (date.get(Calendar.YEAR) == minDate.get(Calendar.YEAR) &&
                date.get(Calendar.MONTH) == minDate.get(Calendar.MONTH));
    }

    private boolean isAfterMaxDate(Calendar date) {
        // Check if the date is after the maxDate
        return date.get(Calendar.YEAR) == maxDate.get(Calendar.YEAR) &&
                date.get(Calendar.MONTH) == maxDate.get(Calendar.MONTH);
    }
}
