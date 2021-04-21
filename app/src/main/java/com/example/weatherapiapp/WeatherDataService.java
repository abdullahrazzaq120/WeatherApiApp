package com.example.weatherapiapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String API_LINK = "https://www.metaweather.com/api/location/search/?query=";
    public static final String API_LINK_FOR_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    private static final String TAG = "WeatherDataService";
    Context context;
    String cityId;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public interface ForeCastByIdResponseListener {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityId(String cityName, VolleyResponseListener volleyResponseListener) {

        String url = API_LINK + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(context.getPackageName(), "onResponse: " + response.toString());

                        try {
                            JSONObject cityWeatherInfo = response.getJSONObject(0);
                            cityId = cityWeatherInfo.getString("woeid");
                            Toast.makeText(context, "City Id is " + cityId, Toast.LENGTH_SHORT).show();

                            volleyResponseListener.onResponse(cityId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(context.getPackageName(), "onErrorResponse: " + error.getMessage());
                volleyResponseListener.onError(error.getMessage());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCityForecastById(String cityId, ForeCastByIdResponseListener foreCastByIdResponseListener) {

        List<WeatherReportModel> weatherReportModelArrayList = new ArrayList<>();

        String url = API_LINK_FOR_WEATHER_BY_ID + cityId;

        //get the json object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray consolidated_weather = response.getJSONArray("consolidated_weather");

//                            JSONObject jsonObject_first_day = (JSONObject) consolidated_weather.get(0);

                            for (int i = 0; i < consolidated_weather.length(); i++) {

                                WeatherReportModel weatherReportModel = new WeatherReportModel();
                                JSONObject jsonObject_all_days = (JSONObject) consolidated_weather.get(i);

                                weatherReportModel.setWeather_state_name(jsonObject_all_days.getString("weather_state_name"));
                                weatherReportModel.setWeather_state_abbr(jsonObject_all_days.getString("weather_state_abbr"));
                                weatherReportModel.setWind_direction_compass(jsonObject_all_days.getString("wind_direction_compass"));
                                weatherReportModel.setCreated(jsonObject_all_days.getString("created"));
                                weatherReportModel.setApplicable_date(jsonObject_all_days.getString("applicable_date"));
                                weatherReportModel.setMin_temp(jsonObject_all_days.getLong("min_temp"));
                                weatherReportModel.setMax_temp(jsonObject_all_days.getLong("max_temp"));
                                weatherReportModel.setThe_temp(jsonObject_all_days.getLong("the_temp"));
                                weatherReportModel.setWind_direction(jsonObject_all_days.getLong("wind_direction"));
                                weatherReportModel.setAir_pressure(jsonObject_all_days.getLong("air_pressure"));
                                weatherReportModel.setVisibility(jsonObject_all_days.getLong("visibility"));
                                weatherReportModel.setId(jsonObject_all_days.getInt("id"));
                                weatherReportModel.setHumidity(jsonObject_all_days.getInt("humidity"));
                                weatherReportModel.setPredictability(jsonObject_all_days.getInt("predictability"));

                                weatherReportModelArrayList.add(weatherReportModel);
                            }

                            foreCastByIdResponseListener.onResponse(weatherReportModelArrayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                foreCastByIdResponseListener.onError(error.getMessage());
            }
        });

        MySingleton.getInstance(context).

                addToRequestQueue(request);
    }
}
