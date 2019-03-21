package com.example.week4weekend.view.fragments;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.week4weekend.R;
import com.example.week4weekend.model.datasource.results.weeklyresponse.List;
import com.example.week4weekend.utils.Operations;

import java.text.ParseException;
import java.util.ArrayList;

public class WeeklyRecyclerAdapter extends RecyclerView.Adapter<WeeklyRecyclerAdapter.ViewHolder> {
    // This List object is a POJO that was automatically
    // generated to hold the data from the response.
    // And I wanted to avoid the problems of changing its name.
    // IT HAS NOTHING TO DO WITH java.util.List !!!!!
    ArrayList<List> days;

    public WeeklyRecyclerAdapter(ArrayList<List> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_daily_weather, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        List day = days.get(i);
        float max = day.getMain().getTempMax();
        float min = day.getMain().getTempMin();
        viewHolder.maxTemp.setText(Operations.formatScale(day.isCelsius, max));
        viewHolder.minTemp.setText(Operations.formatScale(day.isCelsius, min));
        try {
            String dayOfWeek = Operations.getDayOfTheWeek(day.getDtTxt());
            String hour = Operations.getHour(day.getDtTxt());
            viewHolder.dayLabel.setText(dayOfWeek);
            viewHolder.hourLabel.setText(hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (day.getWeather().get(0).getMain()) {
            case "Clouds":
                viewHolder.weatherIcon.setImageResource(R.drawable.ic_cloud_black_24dp);
                break;
            default:
                viewHolder.weatherIcon.setImageResource(R.drawable.ic_wb_sunny_black_24dp);
                break;
        }

        if ((max > 15 && !List.isCelsius) || (max > 59 && List.isCelsius)) {
            viewHolder.layout.setBackgroundColor(
                    viewHolder.itemView.getResources().
                            getColor(R.color.hot_weather_color));
        } else {
            viewHolder.layout.setBackgroundColor(
                    viewHolder.itemView.getResources().
                            getColor(R.color.cold_weather_color));
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void scaleChanged(boolean isCelsius) {
        List.isCelsius = !isCelsius;
        for (List day : days) {
            day.getMain().switchScale();
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView maxTemp, minTemp, hourLabel, dayLabel;
        ImageView weatherIcon;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maxTemp = itemView.findViewById(R.id.maxTemp);
            minTemp = itemView.findViewById(R.id.minTemp);
            dayLabel = itemView.findViewById(R.id.dayLabel);
            hourLabel = itemView.findViewById(R.id.hourLabel);
            layout = itemView.findViewById(R.id.weeklyLayout);
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
        }
    }
}
