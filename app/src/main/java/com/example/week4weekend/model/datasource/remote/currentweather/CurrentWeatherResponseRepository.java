package com.example.week4weekend.model.datasource.remote.currentweather;

import com.example.week4weekend.model.datasource.remote.RetrofitHelper;
import com.example.week4weekend.model.datasource.remote.currentweather.CurrentWeatherResponseObserver.CurrentWeatherResponseCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CurrentWeatherResponseRepository {

    public void getCurrentWeatherResponse(String search, CurrentWeatherResponseCallback callback) {
        RetrofitHelper.getCurrentWeatherResponseObservable(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CurrentWeatherResponseObserver(callback));
    }
}