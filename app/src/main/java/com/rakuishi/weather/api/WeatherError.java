package com.rakuishi.weather.api;

import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rakuishi on 15/04/26.
 */
public class WeatherError {

    @SerializedName("cod")
    private String mCode;

    @SerializedName("message")
    private String mMessage;

    public WeatherError(String code, String message) {
        mCode = code;
        mMessage = message;
    }

    public WeatherError(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        mCode = String.valueOf(response.statusCode);
        mMessage = error.getMessage() == null ? "" : error.getMessage();
    }

    public String getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isError() {
        return mCode != null && mCode.length() > 0 && mMessage != null && mMessage.length() > 0;
    }

    @Override
    public String toString() {
        return "code: " + mCode + "\n" +
               "message: " + mMessage;
    }
}
