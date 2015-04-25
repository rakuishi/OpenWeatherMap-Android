package com.rakuishi.weather.api;

import com.android.volley.Request;

import java.lang.reflect.Type;

/**
 * Created by rakuishi on 15/04/24.
 */
public final class CurrentWeatherDataRequest extends
        AbstractRequest.Builder<CurrentWeatherDataRequest, CurrentWeatherData> {

    public CurrentWeatherDataRequest() {
        super(CurrentWeatherData.class, Request.Method.GET, "/weather");
    }

    public CurrentWeatherDataRequest setCity(String city) {
        return addParam("q", city);
    }
}
