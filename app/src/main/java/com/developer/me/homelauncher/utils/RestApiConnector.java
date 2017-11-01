package com.developer.me.homelauncher.utils;

import com.developer.me.homelauncher.models.weather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sanidhya on 7/8/2017.
 */

public interface RestApiConnector {

    @GET("/data/2.5/weather")

    Call<Weather> getWeatherInfo(
            @Query("APPID") String apiKey, @Query("lat") double currentLatitude, @Query("lon") double currentLongitude, @Query("units") String unit
    );

}
