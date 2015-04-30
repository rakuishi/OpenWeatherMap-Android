package com.rakuishi.weather.api;

import com.android.volley.RequestQueue;

/**
 * Created by rakuishi on 15/04/23.
 */
public class OpenWeatherMapAPIClient {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    private static final String API_KEY  = null;

    private final RequestQueue mRequestQueue;

    public OpenWeatherMapAPIClient(RequestQueue queue) {
        mRequestQueue = queue;
    }

    public CurrentWeatherDataRequest getCurrentWeatherData() {
        return addDefaultHeader(new CurrentWeatherDataRequest());
    }

    protected <T extends AbstractRequest.Builder<T, ?>> T addDefaultHeader(T request) {
        if (API_KEY != null) {
            request.addHeader("x-api-key", API_KEY);
        }
        return request.setRequestQueue(mRequestQueue).setBaseUrl(BASE_URL);
    }
}
