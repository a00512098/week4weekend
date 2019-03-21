package com.example.week4weekend.model.datasource.remote;

import com.example.week4weekend.model.datasource.results.singleresponse.CurrentWeather;
import com.example.week4weekend.model.datasource.results.weeklyresponse.WeeklyWeather;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.week4weekend.model.datasource.remote.CommonConstants.API_KEY;
import static com.example.week4weekend.model.datasource.remote.CommonConstants.APPID;
import static com.example.week4weekend.model.datasource.remote.CommonConstants.BASE_URL;
import static com.example.week4weekend.model.datasource.remote.CommonConstants.HOURLY;
import static com.example.week4weekend.model.datasource.remote.CommonConstants.METRIC;
import static com.example.week4weekend.model.datasource.remote.CommonConstants.UNITS;
import static com.example.week4weekend.model.datasource.remote.CommonConstants.WEEKLY;
import static com.example.week4weekend.model.datasource.remote.CommonConstants.ZIP;

public class RetrofitHelper {
    private static Retrofit retrofit = null;

    private static Retrofit initRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null)
            retrofit = initRetrofit();

        return retrofit;
    }

    // Methods for current weather
    private static ObservableInterface createCurrentWeatherResponseInterface() {
        return getRetrofitInstance().create(ObservableInterface.class);
    }

    public static Observable<CurrentWeather> getCurrentWeatherResponseObservable(String zipcode) {
        return createCurrentWeatherResponseInterface()
                .getCurrentWeatherObservable(
                        zipcode,
                        METRIC,
                        API_KEY
                );
    }

    // Methods for weekly weather
    private static ObservableInterface createWeeklyWeatherResponseInterface() {
        return getRetrofitInstance().create(ObservableInterface.class);
    }

    public static Observable<WeeklyWeather> getWeeklyWeatherResponseObservable(String zipcode) {
        return createWeeklyWeatherResponseInterface()
                .getWeeklyWeatherObservable(
                        zipcode,
                        METRIC,
                        API_KEY
                );
    }

    public interface ObservableInterface {
        @GET(HOURLY)
        Observable<CurrentWeather> getCurrentWeatherObservable(
                @Query(ZIP) String queryZipCode,
                @Query(UNITS) String queryUnits,
                @Query(APPID) String apiKey
        );

        @GET(WEEKLY)
        Observable<WeeklyWeather> getWeeklyWeatherObservable(
                @Query(ZIP) String queryZipCode,
                @Query(UNITS) String queryUnits,
                @Query(APPID) String apiKey
        );
    }
}
