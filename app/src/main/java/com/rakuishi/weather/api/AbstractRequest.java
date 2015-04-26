package com.rakuishi.weather.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.rakuishi.weather.util.UrlUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class AbstractRequest<T> extends Request<T> {

    public static final String TAG = AbstractRequest.class.getSimpleName();

    private final Gson mReader = new Gson();
    private final Class<T> mClazz;
    private final Map<String, String> mParams;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;
    private final WeatherErrorListener mErrorListener;

    private WeatherError mWeatherError;

    public AbstractRequest(Builder<?, T> builder) {
        super(builder.getMethod(), builder.getUrl(), null);

        mClazz = builder.getClazz();
        mParams = builder.getParams();
        mHeaders = builder.getHeaders();
        mListener = builder.getListener();
        mErrorListener = builder.getErrorListener();
    }

    /**
     * Returns a Map of parameters to be used for a POST or PUT request.
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null && response != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mErrorListener != null) {
            if (mWeatherError != null) {
                mErrorListener.onErrorResponse(mWeatherError);
            } else {
                mErrorListener.onErrorResponse(new WeatherError(error));
            }
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            final String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            final JsonElement element = mReader.fromJson(json, JsonObject.class);
            final T object = (T) mReader.fromJson(element, mClazz);
            mWeatherError = mReader.fromJson(element, WeatherError.class);

            if (object != null && !mWeatherError.isError()) {
                return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.error(new ParseError());
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        NetworkResponse response = volleyError.networkResponse;
        if (response != null && response.data != null) {
            try {
                final String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                final JsonElement element = mReader.fromJson(json, JsonObject.class);
                mWeatherError = mReader.fromJson(element, WeatherError.class);

                if (mWeatherError != null && mWeatherError.isError()) {
                    mErrorListener.onErrorResponse(mWeatherError);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return volleyError;
    }

    public static class Builder<J extends Builder, T> {

        protected final Class<T> mClazz;
        protected final int mMethod;
        protected final String mPath;

        protected String mBaseUrl;
        protected Map<String, String> mParams = new HashMap<>();
        protected Map<String, String> mHeaders = new HashMap<>();
        protected RequestQueue mRequestQueue;
        protected Response.Listener<T> mListener;
        protected WeatherErrorListener mErrorListener;

        public Builder(Class<T> clazz, int method, String path) {
            mClazz = clazz;
            mMethod = method;
            mPath = path;
        }

        public final J setBaseUrl(String baseUrl) {
            mBaseUrl = baseUrl;
            return (J) this;
        }

        public final J addParam(String key, String value) {
            mParams.put(key, value);
            return (J) this;
        }

        public final J addHeader(String key, String value) {
            mHeaders.put(key, value);
            return (J) this;
        }

        public final J setRequestQueue(RequestQueue requestQueue) {
            mRequestQueue = requestQueue;
            return (J) this;
        }

        public final J setListener(Response.Listener listener) {
            mListener = listener;
            return (J) this;
        }

        public final J setErrorListener(WeatherErrorListener listener) {
            mErrorListener = listener;
            return (J) this;
        }

        public final Class<T> getClazz() {
            return mClazz;
        }

        public final int getMethod() {
            return mMethod;
        }

        public final String getUrl() {
            if (mMethod == Method.GET) {
                return mBaseUrl + mPath + UrlUtils.getQuery(mParams);
            } else {
                return mBaseUrl + mPath;
            }
        }

        public final Map<String, String> getParams() {
            return mParams;
        }

        public final Map<String, String> getHeaders() {
            return mHeaders;
        }

        public final Response.Listener<T> getListener() {
            return mListener;
        }

        public final WeatherErrorListener getErrorListener() {
            return mErrorListener;
        }

        public final AbstractRequest<T> build() {
            final AbstractRequest<T> request = new AbstractRequest<>(this);
            mRequestQueue.add(request);
            return request;
        }
    }
}
