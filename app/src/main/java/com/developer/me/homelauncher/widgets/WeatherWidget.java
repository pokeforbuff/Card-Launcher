package com.developer.me.homelauncher.widgets;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.me.homelauncher.R;
import com.developer.me.homelauncher.constants.Constants;
import com.developer.me.homelauncher.models.weather.Weather;
import com.developer.me.homelauncher.utils.RestApiConnector;
import com.developer.me.homelauncher.utils.RetrofitBuilder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sanidhya on 7/8/2017.
 */

public class WeatherWidget extends android.support.v4.app.Fragment {

    private View widgetView;
    private TextView weatherCurrentTemp, weatherType, weatherLocation, weatherMaxMinTemp;
    private ImageView weatherTypeIcon;
    private String iconUri, type, location;
    private FusedLocationProviderClient fusedLocationClient;
    private Double temp;
    private Double currentLongitude, currentLatitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        widgetView = inflater.inflate(R.layout.widget_weather, container, false);
        initObjects();
        initViews();
        getCurrentLocation();
        return widgetView;
    }

    private void initObjects() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        currentLatitude= 0d;
        currentLongitude= 0d;
    }

    private void initViews() {
        weatherCurrentTemp = widgetView.findViewById(R.id.weather_current_temp_textview);
        weatherLocation = widgetView.findViewById(R.id.weather_location_textview);
        weatherType = widgetView.findViewById(R.id.weather_type_textview);
        weatherTypeIcon = widgetView.findViewById(R.id.weather_type_icon_imageview);
        weatherMaxMinTemp = widgetView.findViewById(R.id.weather_max_min_temp_textview);
    }

    private void getWeatherFromApi(Double currentLatitude,Double currentLongitude) {
        RestApiConnector apiConnector = RetrofitBuilder.getClient(Constants.BASE_WEATHER_URL).create(RestApiConnector.class);
        Call<Weather> call = apiConnector.getWeatherInfo(Constants.WEATHER_API_KEY, currentLatitude, currentLongitude, "metric");
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                if (response.body() != null) {
                    temp = response.body().getMainDetails().getCurrentTemp();
                    type = response.body().getType().get(0).getMain();
                    location = response.body().getName();
                    iconUri = "http://openweathermap.org/img/w/" + response.body().getType().get(0).getIcon() + ".png";
                }
                weatherCurrentTemp.setText(temp.toString() + "°C");
                weatherLocation.setText(" " + location);
                weatherType.setText(type);
                weatherMaxMinTemp.setText(response.body().getMainDetails().getTempMax().toString() + "°C / " + response.body().getMainDetails().getTempMin().toString() + "°C");
                Picasso.with(getActivity()).load(iconUri).into(weatherTypeIcon);
            }

            @Override
            public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Could not connect to weather service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLatitude = location.getLatitude();
                                currentLongitude = location.getLongitude();
                                getWeatherFromApi(currentLatitude,currentLongitude);
                            }
                        }
                    });
        } else getLocationPermission();
    }

}
