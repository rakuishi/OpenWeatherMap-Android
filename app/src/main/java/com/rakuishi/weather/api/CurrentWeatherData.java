package com.rakuishi.weather.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakuishi on 15/04/24.
 */
public class CurrentWeatherData {

    @SerializedName("dt")
    private int mDt;

    @SerializedName("id")
    private int mId;

    @SerializedName("cod")
    private int mCod;

    @SerializedName("name")
    private String mName;

    @SerializedName("weather")
    private List<Weather> mWeatherList;

    @SerializedName("main")
    private Main mMain;

    @SerializedName("coord")
    private Coordinate mCoordinate;

    @SerializedName("wind")
    private Wind mWind;

    public CurrentWeatherData(int dt, int id, int cod, String name, List<Weather> weatherList,
                              Main main, Coordinate coordinate, Wind wind) {
        mDt = dt;
        mId = id;
        mCod = cod;
        mName = name;
        mWeatherList = weatherList;
        mMain = main;
        mCoordinate = coordinate;
        mWind = wind;
    }

    public int getDt() {
        return mDt;
    }

    public void setDt(int dt) {
        mDt = dt;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getCod() {
        return mCod;
    }

    public void setCod(int cod) {
        mCod = cod;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Weather> getWeatherList() {
        return mWeatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        mWeatherList = weatherList;
    }

    public Main getMain() {
        return mMain;
    }

    public void setMain(Main main) {
        mMain = main;
    }

    public Coordinate getCoordinate() {
        return mCoordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        mCoordinate = coordinate;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

    @Override
    public String toString() {
        return "dt: " + mDt + "\n" +
               "id: " + mId + "\n" +
               "cod: " + mCod + "\n" +
               "name: " + mName + "\n" +
               "weather: " + mWeatherList.toString() + "\n" +
               "main: " + mMain.toString() + "\n" +
               "coord: " + mCoordinate + "\n" +
               "wind: " + mWind;
    }

    class Weather {

        @SerializedName("id")
        private int mId;

        @SerializedName("main")
        private String mMain;

        @SerializedName("description")
        private String mDescription;

        @SerializedName("icon")
        private String mIcon;

        public Weather(int id, String main, String description, String icon) {
            mId = id;
            mMain = main;
            mDescription = description;
            mIcon = icon;
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getMain() {
            return mMain;
        }

        public void setMain(String main) {
            mMain = main;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getIcon() {
            return mIcon;
        }

        public void setIcon(String icon) {
            mIcon = icon;
        }

        @Override
        public String toString() {
            return "id: " + mId + "\n" +
                   "main: " + mMain + "\n" +
                   "description: " + mDescription + "\n" +
                   "icon: " + mIcon;
        }
    }

    class Main {

        @SerializedName("temp")
        private float mTemp;

        @SerializedName("humidity")
        private int mHumidity;

        @SerializedName("pressure")
        private int mPressure;

        @SerializedName("temp_min")
        private float mTempMin;

        @SerializedName("temp_max")
        private float mTempMax;

        public Main(float temp, int humidity, int pressure, float tempMin, float tempMax) {
            mTemp = temp;
            mHumidity = humidity;
            mPressure = pressure;
            mTempMin = tempMin;
            mTempMax = tempMax;
        }

        public float getTemp() {
            return mTemp;
        }

        public void setTemp(float temp) {
            mTemp = temp;
        }

        public int getHumidity() {
            return mHumidity;
        }

        public void setHumidity(int humidity) {
            mHumidity = humidity;
        }

        public int getPressure() {
            return mPressure;
        }

        public void setPressure(int pressure) {
            mPressure = pressure;
        }

        public float getTempMin() {
            return mTempMin;
        }

        public void setTempMin(float tempMin) {
            mTempMin = tempMin;
        }

        public float getTempMax() {
            return mTempMax;
        }

        public void setTempMax(float tempMax) {
            mTempMax = tempMax;
        }

        @Override
        public String toString() {
            return "temp: " + mTemp + "\n" +
                   "humidity: " + mHumidity + "\n" +
                   "pressure: " + mPressure + "\n" +
                   "temp_min: " + mTempMin + "\n" +
                   "temp_max: " + mTempMax;
        }
    }

    class Coordinate {

        @SerializedName("lon")
        private int mLongitude;

        @SerializedName("lat")
        private int mLatitude;

        public Coordinate(int longitude, int latitude) {
            mLongitude = longitude;
            mLatitude = latitude;
        }

        public int getLongitude() {
            return mLongitude;
        }

        public void setLongitude(int longitude) {
            mLongitude = longitude;
        }

        public int getLatitude() {
            return mLatitude;
        }

        public void setLatitude(int latitude) {
            mLatitude = latitude;
        }

        @Override
        public String toString() {
            return "lon: " + mLongitude + "\n" +
                   "lat: " + mLatitude;
        }
    }

    class Wind {

        @SerializedName("speed")
        private float mSpeed;

        @SerializedName("deg")
        private float mDeg;

        public Wind(float speed, float deg) {
            mSpeed = speed;
            mDeg = deg;
        }

        public float getSpeed() {
            return mSpeed;
        }

        public void setSpeed(float speed) {
            mSpeed = speed;
        }

        public float getDeg() {
            return mDeg;
        }

        public void setDeg(float deg) {
            mDeg = deg;
        }

        @Override
        public String toString() {
            return "speed: " + mSpeed + "\n" +
                   "deg: " + mDeg;
        }
    }
}
