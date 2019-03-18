package com.example.week4weekend.model.datasource.remote.currentweather;

import com.example.week4weekend.model.datasource.results.singleresponse.CurrentWeather;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CurrentWeatherResponseObserver implements Observer<CurrentWeather> {
    private CurrentWeather currentWeatherResponse;
    private CurrentWeatherResponseCallback weatherResponseCallback;

    CurrentWeatherResponseObserver(CurrentWeatherResponseCallback weatherResponseCallback) {
        this.weatherResponseCallback = weatherResponseCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CurrentWeather currentWeather) {
        this.currentWeatherResponse = currentWeather;
    }

    @Override
    public void onError(Throwable e) {
        weatherResponseCallback.onError(e);
    }

    @Override
    public void onComplete() {
        weatherResponseCallback.onSuccess(currentWeatherResponse);
    }

    public interface CurrentWeatherResponseCallback {
        void onSuccess(CurrentWeather currentWeather);

        void onError(Throwable e);
    }
}