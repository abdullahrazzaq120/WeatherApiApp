package com.example.weatherapiapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class WeatherHolder extends RecyclerView.ViewHolder {

    ImageView stateImage;
    TextView stateName, temp, minTv, maxTv, dateTv;

    public WeatherHolder(@NonNull View itemView) {
        super(itemView);

        stateImage = itemView.findViewById(R.id.stateImageId);
        stateName = itemView.findViewById(R.id.stateId);
        temp = itemView.findViewById(R.id.tempId);
        minTv = itemView.findViewById(R.id.minId);
        maxTv = itemView.findViewById(R.id.maxId);
        dateTv = itemView.findViewById(R.id.dateId);
    }
}
