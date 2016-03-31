package com.humphrey.boomshare.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class SharedPreferencesUtils {

    public static String getStringFromSharedPreference(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);

        return sp.getString(key, null);
    }

    public static void putStringToSharedPreference(Context context, String key, String str) {

        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);

        sp.edit().putString(key, str).commit();
    }
}
