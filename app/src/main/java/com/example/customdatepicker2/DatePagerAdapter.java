package com.example.customdatepicker2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Date;
import java.util.List;

public class DatePagerAdapter extends RecyclerView.Adapter<DatePagerAdapter.DateViewHolder> {
    private List<List<Date>> months;

    public DatePagerAdapter(List<List<Date>> months) {
        this.months = months;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_date_month, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        holder.bind(months.get(position));
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerViewMonth;

        public DateViewHolder(View itemView) {
            super(itemView);
            recyclerViewMonth = itemView.findViewById(R.id.recyclerViewMonth);
        }

        public void bind(List<Date> monthDates) {
            DateAdapter adapter = new DateAdapter(monthDates);
            recyclerViewMonth.setLayoutManager(new GridLayoutManager(itemView.getContext(), 7));
            recyclerViewMonth.setAdapter(adapter);
        }
    }
}
