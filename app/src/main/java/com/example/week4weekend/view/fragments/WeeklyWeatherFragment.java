package com.example.week4weekend.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.week4weekend.R;
import com.example.week4weekend.model.datasource.remote.WeatherResponseRepository;
import com.example.week4weekend.model.datasource.remote.weeklyweather.WeeklyWeatherResponseObserver;
import com.example.week4weekend.model.datasource.results.weeklyresponse.List;
import com.example.week4weekend.model.datasource.results.weeklyresponse.WeeklyWeather;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.week4weekend.utils.CommonConstants.ON_BOARDING_COUNT;
import static com.example.week4weekend.utils.CommonConstants.ON_BOARDING_COUNT_DEFAULT;
import static com.example.week4weekend.utils.CommonConstants.SHARED_PREFERENCES;
import static com.example.week4weekend.utils.CommonConstants.SHARED_ZIP_DEFAULT;
import static com.example.week4weekend.utils.CommonConstants.ZIP_CODE_BUNDLE;

public class WeeklyWeatherFragment extends Fragment implements WeeklyWeatherResponseObserver.WeeklyWeatherResponseCallback {
    SharedPreferences sharedPreferences;
    ArrayList<List> days;
    RecyclerView recyclerView;
    WeeklyRecyclerAdapter adapter;
    WeatherResponseRepository weatherResponseRepository;
    ConstraintLayout layout;
    int zipCode;

    public WeeklyWeatherFragment() {
        // Required empty public constructor
    }

    public static WeeklyWeatherFragment newInstance() {
        return new WeeklyWeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            zipCode = bundle.getInt(ZIP_CODE_BUNDLE, SHARED_ZIP_DEFAULT);
            Log.d("Log.d", "Zipcode frag2: " + zipCode);
        }
        return inflater.inflate(R.layout.fragment_weekly_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.recyclerView);
        layout = view.findViewById(R.id.weeklyLayout);
        weatherResponseRepository = new WeatherResponseRepository();
        weatherResponseRepository.getWeeklyWeatherResponse(String.valueOf(zipCode), this);
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
    public void onSuccess(WeeklyWeather weeklyWeather) {
        Log.d("Log.d", "onSuccess2");
        days = weeklyWeather.getList();
        adapter = new WeeklyRecyclerAdapter(days);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        showSnackbar();
    }

    // This Snackbar is shown only the first three times the user enters the app
    // And is only shown after the request is successful
    private void showSnackbar() {
        int count = sharedPreferences.getInt(ON_BOARDING_COUNT, ON_BOARDING_COUNT_DEFAULT);
        if (count > 3)
            return;

        sharedPreferences.edit().putInt(ON_BOARDING_COUNT, ++count).apply();
        final Snackbar onBoardingSnack = Snackbar.make(layout, R.string.snack_message,
                Snackbar.LENGTH_INDEFINITE);
        onBoardingSnack.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBoardingSnack.dismiss();
            }
        });
        onBoardingSnack.show();
    }

    @Override
    public void onError(Throwable e) {
        Log.d("Log.d", "ERROR! " + e);
    }

    public void scaleChanged(boolean isCelsius) {
        adapter.scaleChanged(isCelsius);
    }
}
