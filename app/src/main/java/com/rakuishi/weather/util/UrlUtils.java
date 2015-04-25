package com.rakuishi.weather.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rakuishi on 15/04/25.
 */
public class UrlUtils {

    public static String getQuery(Map<String, String> params) {
        List<String> list = new ArrayList<>();

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                list.add(key + "=" + value);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + "UTF-8", e);
        }

        return list.size() > 0 ?  "?" + TextUtils.join("&", list) : "";
    }
}
