package com.example.weatherapiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {

    List<WeatherReportModel> weatherReportModels;

    public WeatherAdapter(List<WeatherReportModel> weatherReportModels) {
        this.weatherReportModels = weatherReportModels;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_deign, parent, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        holder.stateName.setText(weatherReportModels.get(position).getWeather_state_name());
        holder.dateTv.setText(weatherReportModels.get(position).getApplicable_date());
        holder.minTv.setText(String.valueOf(weatherReportModels.get(position).getMin_temp()));
        holder.maxTv.setText(String.valueOf(weatherReportModels.get(position).getMax_temp()));
        holder.temp.setText(String.valueOf(weatherReportModels.get(position).getThe_temp()));

        if (weatherReportModels.get(position).getWeather_state_name().equals("Heavy Rain")) {
            holder.stateImage.setImageResource(R.drawable.heavyrain);
        }
        if (weatherReportModels.get(position).getWeather_state_name().equals("Showers")) {
            holder.stateImage.setImageResource(R.drawable.shower);
        }
        if (weatherReportModels.get(position).getWeather_state_name().equals("Clear")) {
            holder.stateImage.setImageResource(R.drawable.clear);
        }
        if (weatherReportModels.get(position).getWeather_state_name().equals("Light Cloud")) {
            holder.stateImage.setImageResource(R.drawable.light_cloud);
        }
    }

    @Override
    public int getItemCount() {
        return weatherReportModels.size();
    }
}
