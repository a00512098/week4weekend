package com.example.week4weekend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.week4weekend.model.datasource.remote.currentweather.CurrentWeatherResponseObserver;
import com.example.week4weekend.model.datasource.remote.currentweather.CurrentWeatherResponseRepository;
import com.example.week4weekend.model.datasource.results.singleresponse.CurrentWeather;

public class HourlyWeatherFragment extends Fragment
        implements CurrentWeatherResponseObserver.CurrentWeatherResponseCallback,
                    View.OnClickListener{

    TextView weatherLabel, cityLabel;
    ImageView weatherIcon;
    ConstraintLayout layout;
    boolean isCelsius;
    float currentTemp;

    CurrentWeatherResponseRepository currentWeatherResponseRepository;
    int zipcode;

    public static HourlyWeatherFragment newInstance() {
        return new HourlyWeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            zipcode = bundle.getInt("zipcode", -1);
            Log.d("Log.d", "Zipcode frag: " + zipcode);
        }
        return inflater.inflate(R.layout.fragment_hourly_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherIcon = view.findViewById(R.id.watherImage);
        weatherLabel = view.findViewById(R.id.currentWeather);
        cityLabel = view.findViewById(R.id.cityName);
        layout = view.findViewById(R.id.hourlyWeatherLayout);
        isCelsius = true;

        currentWeatherResponseRepository = new CurrentWeatherResponseRepository();
        currentWeatherResponseRepository.getCurrentWeatherResponse(String.valueOf(zipcode), this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSuccess(CurrentWeather weather) {
        Log.d("Log.d", "onSuccess");
        currentTemp = weather.getMain().getTemp();
        weatherLabel.setText(String.format(getString(R.string.f_c), currentTemp));
        weatherLabel.setOnClickListener(this);

        switch (weather.getWeather().get(0).getMain()) {
            case "Clouds":
                weatherIcon.setImageResource(R.drawable.ic_cloud_black_24dp);
                break;
            default:
                weatherIcon.setImageResource(R.drawable.ic_wb_sunny_black_24dp);
                break;
        }

        if (currentTemp > 15) {
            layout.setBackgroundColor(getResources().getColor(R.color.hot_weather_color));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.cold_weather_color));
        }

        cityLabel.setText(weather.getName());
    }

    @Override
    public void onError(Throwable e) {
        Log.d("Log.d", e.toString());
    }

    @Override
    public void onClick(View v) {
        changeScale();
    }

    private void changeScale() {
        if (isCelsius) {
            isCelsius = false;
            currentTemp = (currentTemp * 9/5) + 32;
            weatherLabel.setText(String.format(getString(R.string.f_f), currentTemp));
        } else {
            isCelsius = true;
            currentTemp = (currentTemp - 32) * 5/9;
            weatherLabel.setText(String.format(getString(R.string.f_c), currentTemp));
        }
    }
}
