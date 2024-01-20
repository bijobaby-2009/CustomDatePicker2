package com.example.customdatepicker2;

import android.graphics.Color;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Date> dates;
    private Calendar currentDateCalendar; // Use Calendar to compare dates without considering time
    public static Date selectedDate;

    public DateAdapter(List<Date> dates) {
        this.dates = dates;

        // Initialize the current date to the current system date without time
        currentDateCalendar = Calendar.getInstance();
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentDateCalendar.set(Calendar.MINUTE, 0);
        currentDateCalendar.set(Calendar.SECOND, 0);
        currentDateCalendar.set(Calendar.MILLISECOND, 0);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Date date = dates.get(position);
        ((DateViewHolder) holder).bind(date);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void updateDates(List<Date> newDates) {
        dates.clear();
        dates.addAll(newDates);
        notifyDataSetChanged();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);

            // Add click listener here
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Date clickedDate = dates.get(position);

                    // Check if the clicked date is not earlier than or equal to the current date
                    if (clickedDate != null && !clickedDate.before(currentDateCalendar.getTime())) {
                        // Update the selected date
                        selectedDate = clickedDate;
                        notifyDataSetChanged(); // Refresh the view to update background colors

                    }
                }
            });

        }

        public void bind(Date date) {
            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
                tvDate.setText(sdf.format(date));

                // Disable click if the date is earlier than or equal to the current date
                itemView.setEnabled(!date.before(currentDateCalendar.getTime()));


                SimpleDateFormat sdfSelected = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault());
                SimpleDateFormat sdfItem = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault());
                String sdfSelectedDate=sdfSelected.format(selectedDate);
                String sdfItemDate=sdfItem.format(date);

                // Set background color for the selected date
                if (selectedDate != null && sdfItemDate.equals(sdfSelectedDate)) {
                    itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.selectedDateBackground));
                } else {
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                }
            } else {
                tvDate.setText("");
                itemView.setEnabled(false);
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
        notifyDataSetChanged();
    }
}
