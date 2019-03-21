package com.example.week4weekend.model.datasource.remote;

import com.example.week4weekend.model.datasource.remote.RetrofitHelper;
import com.example.week4weekend.model.datasource.remote.currentweather.CurrentWeatherResponseObserver;
import com.example.week4weekend.model.datasource.remote.currentweather.CurrentWeatherResponseObserver.CurrentWeatherResponseCallback;
import com.example.week4weekend.model.datasource.remote.weeklyweather.WeeklyWeatherResponseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.week4weekend.model.datasource.remote.weeklyweather.WeeklyWeatherResponseObserver.*;

public class WeatherResponseRepository {

    public void getCurrentWeatherResponse(String zipCode, CurrentWeatherResponseCallback callback) {
        RetrofitHelper.getCurrentWeatherResponseObservable(zipCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CurrentWeatherResponseObserver(callback));
    }

    public void getWeeklyWeatherResponse(String zipCode, WeeklyWeatherResponseCallback callback) {
        RetrofitHelper.getWeeklyWeatherResponseObservable(zipCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WeeklyWeatherResponseObserver(callback));
    }
}