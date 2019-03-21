
package com.example.week4weekend.model.datasource.results.weeklyresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeeklyWeather implements Parcelable
{

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Float message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private ArrayList<List> list = null;
    @SerializedName("city")
    @Expose
    private City city;
    public final static Creator<WeeklyWeather> CREATOR = new Creator<WeeklyWeather>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WeeklyWeather createFromParcel(Parcel in) {
            return new WeeklyWeather(in);
        }

        public WeeklyWeather[] newArray(int size) {
            return (new WeeklyWeather[size]);
        }

    }
    ;

    protected WeeklyWeather(Parcel in) {
        this.cod = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((Float) in.readValue((Float.class.getClassLoader())));
        this.cnt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.list, (List.class.getClassLoader()));
        this.city = ((City) in.readValue((City.class.getClassLoader())));
    }

    public WeeklyWeather() {
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Float getMessage() {
        return message;
    }

    public void setMessage(Float message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cod);
        dest.writeValue(message);
        dest.writeValue(cnt);
        dest.writeList(list);
        dest.writeValue(city);
    }

    public int describeContents() {
        return  0;
    }

}
