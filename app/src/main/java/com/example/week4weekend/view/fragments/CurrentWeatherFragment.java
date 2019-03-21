package com.example.week4weekend.view.fragments;

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

import com.example.week4weekend.R;
import com.example.week4weekend.model.datasource.remote.currentweather.CurrentWeatherResponseObserver;
import com.example.week4weekend.model.datasource.remote.WeatherResponseRepository;
import com.example.week4weekend.model.datasource.results.singleresponse.CurrentWeather;
import com.example.week4weekend.utils.Operations;

import static com.example.week4weekend.utils.CommonConstants.SHARED_ZIP_DEFAULT;
import static com.example.week4weekend.utils.CommonConstants.ZIP_CODE_BUNDLE;

public class CurrentWeatherFragment extends Fragment
        implements CurrentWeatherResponseObserver.CurrentWeatherResponseCallback,
                    View.OnClickListener{

    OnInteractionListener interactionListener;
    TextView weatherLabel, cityLabel;
    ImageView weatherIcon;
    ConstraintLayout layout;
    boolean isCelsius;
    float currentTemp;

    WeatherResponseRepository currentWeatherResponseRepository;
    int zipCode;

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
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
            zipCode = bundle.getInt(ZIP_CODE_BUNDLE, SHARED_ZIP_DEFAULT);
            Log.d("Log.d", "Zipcode frag: " + zipCode);
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

        currentWeatherResponseRepository = new WeatherResponseRepository();
        currentWeatherResponseRepository.getCurrentWeatherResponse(String.valueOf(zipCode), this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            interactionListener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onSuccess(CurrentWeather weather) {
        if (isAdded()) {
            Log.d("Log.d", "isAdded");
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
        } else {
            Log.d("Log.d", "isNotAdded");
        }
    }

    @Override
    public void onError(Throwable e) {
        interactionListener.onZipCodeNotFound();
        Log.d("Log.d", e.toString());
    }

    @Override
    public void onClick(View v) {
        changeScale();
    }

    private void changeScale() {
        currentTemp = Operations.changeScale(isCelsius, currentTemp);
        weatherLabel.setText(Operations.formatScale(isCelsius, currentTemp));
//        String format = isCelsius ? getString(R.string.f_f) : getString(R.string.f_c);
//        weatherLabel.setText(String.format(format, currentTemp));
        isCelsius = !isCelsius;
        interactionListener.onScaleChange(isCelsius);
    }

    public interface OnInteractionListener {
        void onScaleChange(boolean isCelsius);
        void onZipCodeNotFound();
    }
}
