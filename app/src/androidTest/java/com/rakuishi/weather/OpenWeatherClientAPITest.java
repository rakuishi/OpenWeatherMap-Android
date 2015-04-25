package com.rakuishi.weather;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.rakuishi.weather.api.CurrentWeatherData;
import com.rakuishi.weather.api.OpenWeatherMapClientAPI;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by rakuishi on 15/04/25.
 */
public class OpenWeatherClientAPITest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = OpenWeatherClientAPITest.class.getSimpleName();

    private MainActivity mActivity;

    public OpenWeatherClientAPITest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public OpenWeatherClientAPITest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testCurrentWeatherDataRequest() throws InterruptedException {
        Context context = mActivity.getApplicationContext();
        final OpenWeatherMapClientAPI client = new OpenWeatherMapClientAPI(Volley.newRequestQueue(context));
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
                     }
                ).build();
            }
        });

        boolean result = latch.await(10, TimeUnit.SECONDS);
        assertEquals(TAG, true, result);
    }
}
