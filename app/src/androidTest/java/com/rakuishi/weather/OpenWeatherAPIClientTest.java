package com.rakuishi.weather;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.rakuishi.weather.api.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by rakuishi on 15/04/25.
 */
public class OpenWeatherAPIClientTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = OpenWeatherAPIClientTest.class.getSimpleName();

    private MainActivity mActivity;

    public OpenWeatherAPIClientTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public OpenWeatherAPIClientTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testCurrentWeatherDataRequest() throws InterruptedException {
        Context context = mActivity.getApplicationContext();
        final OpenWeatherMapAPIClient client = new OpenWeatherMapAPIClient(Volley.newRequestQueue(context));
        final CountDownLatch latch = new CountDownLatch(1);

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                client.getCurrentWeatherData().setCity("fukuoka,jp")
                    .setListener(new Response.Listener<CurrentWeatherData>() {
                         @Override
                         public void onResponse(CurrentWeatherData response) {
                             assertTrue(TAG, response.getName().equals("Fukuoka-Shi"));
                             latch.countDown();
                         }
                     })
                    .setErrorListener(new WeatherErrorListener() {
                        @Override
                        public void onErrorResponse(WeatherError error) {
                            latch.countDown();
                        }
                    })
                    .build();
            }
        });

        boolean result = latch.await(10, TimeUnit.SECONDS);
        assertEquals(TAG, true, result);
    }

    public void testCurrentWeatherDataRequestNotFoundCity() throws InterruptedException {
        Context context = mActivity.getApplicationContext();
        final OpenWeatherMapAPIClient client = new OpenWeatherMapAPIClient(Volley.newRequestQueue(context));
        final CountDownLatch latch = new CountDownLatch(1);

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                client.getCurrentWeatherData().setCity("fukuka,jp")
                    .setListener(new Response.Listener<CurrentWeatherData>() {
                        @Override
                        public void onResponse(CurrentWeatherData response) {
                            Log.d(TAG, response.toString());
                            latch.countDown();
                        }
                    })
                    .setErrorListener(new WeatherErrorListener() {
                        @Override
                        public void onErrorResponse(WeatherError error) {
                            Log.d(TAG, error.toString());
                            assertNotNull(TAG, error);
                            assertTrue(TAG, error.getCode().equals("404"));
                            assertTrue(TAG, error.getMessage().equals("Error: Not found city"));
                            latch.countDown();
                        }
                    })
                    .build();
            }
        });

        boolean result = latch.await(10, TimeUnit.SECONDS);
        assertEquals(TAG, true, result);
    }
}
