package com.example.weatherapiapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button btn1, btn2, btn3;
    EditText editText;
    ListView listView;
    RecyclerView rv;
    WeatherDataService weatherDataService;
    WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDataService = new WeatherDataService(MainActivity.this);
        rv = findViewById(R.id.rvId);
        btn1 = findViewById(R.id.btn1Id);
        btn2 = findViewById(R.id.btn2Id);
        btn3 = findViewById(R.id.btn3Id);
        editText = findViewById(R.id.writeTextEtId);
        listView = findViewById(R.id.listViewId);

        btn1.setOnClickListener(v -> {

            String cityName = editText.getText().toString();
            weatherDataService.getCityId(cityName, new WeatherDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Log.e(TAG, "onError: " + message);
                }

                @Override
                public void onResponse(String cityID) {
                    Log.d(TAG, "onCreate: cityFetchedId " + cityID);
                }
            });
        });

        btn2.setOnClickListener(v -> {
            String cityId = editText.getText().toString();
            weatherDataService.getCityForecastById(cityId, new WeatherDataService.ForeCastByIdResponseListener() {
                @Override
                public void onError(String message) {
                    Log.e(TAG, "onError: " + message);
                }

                @Override
                public void onResponse(List<WeatherReportModel> weatherReportModels) {
//                    Toast.makeText(MainActivity.this, weatherReportModels.toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " + weatherReportModels.toString());
                    runfunListView(weatherReportModels);
                }
            });
        });

        btn3.setOnClickListener(v -> {
            String cityName = editText.getText().toString();
            weatherDataService.getCityId(cityName, new WeatherDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Log.e(TAG, "onError: " + message);
                }

                @Override
                public void onResponse(String cityID) {
                    Log.d(TAG, "onCreate: cityFetchedId " + cityID);
                    weatherDataService.getCityForecastById(cityID, new WeatherDataService.ForeCastByIdResponseListener() {
                        @Override
                        public void onError(String message) {
                            Log.e(TAG, "onError: " + message);
                        }

                        @Override
                        public void onResponse(List<WeatherReportModel> weatherReportModels) {
//                            Toast.makeText(MainActivity.this, weatherReportModel.toString(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onResponse: " + weatherReportModels);
//                            runfunListView(weatherReportModels);

                            weatherAdapter = new WeatherAdapter(weatherReportModels);
                            rv.setAdapter(weatherAdapter);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                            rv.setLayoutManager(gridLayoutManager);
                            rv.setHasFixedSize(true);

                        }
                    });
                }
            });
        });
    }

    private void runfunListView(List<WeatherReportModel> weatherReportModels) {
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
        listView.setAdapter(adapter);
    }
}