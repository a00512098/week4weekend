package com.example.week4weekend.model.datasource.remote.weeklyweather;

import com.example.week4weekend.model.datasource.results.weeklyresponse.WeeklyWeather;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WeeklyWeatherResponseObserver implements Observer<WeeklyWeather> {
    private WeeklyWeather weeklyWeather;
    private WeeklyWeatherResponseCallback weatherResponseCallback;

    public WeeklyWeatherResponseObserver(WeeklyWeatherResponseCallback weatherResponseCallback) {
        this.weatherResponseCallback = weatherResponseCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(WeeklyWeather weeklyWeather) {
        this.weeklyWeather = weeklyWeather;
    }

    @Override
    public void onError(Throwable e) {
        weatherResponseCallback.onError(e);
    }

    @Override
    public void onComplete() {
        weatherResponseCallback.onSuccess(weeklyWeather);
    }

    public interface WeeklyWeatherResponseCallback {
        void onSuccess(WeeklyWeather weeklyWeather);

        void onError(Throwable e);
    }
}
