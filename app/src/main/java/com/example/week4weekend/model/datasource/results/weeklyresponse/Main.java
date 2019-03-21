
package com.example.week4weekend.model.datasource.results.weeklyresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.week4weekend.utils.Operations;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable
{

    @SerializedName("temp")
    @Expose
    private Float temp;
    @SerializedName("temp_min")
    @Expose
    private Float tempMin;
    @SerializedName("temp_max")
    @Expose
    private Float tempMax;
    @SerializedName("pressure")
    @Expose
    private Float pressure;
    @SerializedName("sea_level")
    @Expose
    private Float seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private Float grndLevel;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("temp_kf")
    @Expose
    private Float tempKf;
    public final static Creator<Main> CREATOR = new Creator<Main>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        public Main[] newArray(int size) {
            return (new Main[size]);
        }

    }
    ;

    protected Main(Parcel in) {
        this.temp = ((Float) in.readValue((Float.class.getClassLoader())));
        this.tempMin = ((Float) in.readValue((Float.class.getClassLoader())));
        this.tempMax = ((Float) in.readValue((Float.class.getClassLoader())));
        this.pressure = ((Float) in.readValue((Float.class.getClassLoader())));
        this.seaLevel = ((Float) in.readValue((Float.class.getClassLoader())));
        this.grndLevel = ((Float) in.readValue((Float.class.getClassLoader())));
        this.humidity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.tempKf = ((Float) in.readValue((Integer.class.getClassLoader())));
    }

    public Main() {
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getTempMin() {
        return tempMin;
    }

    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

    public Float getTempMax() {
        return tempMax;
    }

    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Float seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Float getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Float grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getTempKf() {
        return tempKf;
    }

    public void setTempKf(Float tempKf) {
        this.tempKf = tempKf;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(temp);
        dest.writeValue(tempMin);
        dest.writeValue(tempMax);
        dest.writeValue(pressure);
        dest.writeValue(seaLevel);
        dest.writeValue(grndLevel);
        dest.writeValue(humidity);
        dest.writeValue(tempKf);
    }

    public int describeContents() {
        return  0;
    }

    public void switchScale() {
        tempMax = Operations.changeScale(List.isCelsius, tempMax);
        tempMin = Operations.changeScale(List.isCelsius, tempMin);
    }

}
