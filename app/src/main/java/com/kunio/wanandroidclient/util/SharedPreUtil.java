package com.kunio.wanandroidclient.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.kunio.wanandroidclient.AndroidApplication;

import java.util.HashSet;
import java.util.Set;

import okhttp3.HttpUrl;

/**
 * Created by zhc on 2017/8/17 0017.
 */

public class SharedPreUtil {
    private static final String DEFAULT_SHARED_PREFERENCES = "sharedPreferences";

    public static void saveCookies(HttpUrl url, Set<String> cookies) {
        SharedPreferences preferences = AndroidApplication.getContext().getSharedPreferences(DEFAULT_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(url.host(), cookies);
        editor.apply();
    }

    public static Set<String> getCookies(HttpUrl url) {
        SharedPreferences preferences = AndroidApplication.getContext().getSharedPreferences(DEFAULT_SHARED_PREFERENCES, 0);
        Set<String> cookies = preferences.getStringSet(url.host(), new HashSet<String>());
        return cookies;
    }

    public static boolean saveUserName(String name) {
        SharedPreferences preferences = AndroidApplication.getContext().getSharedPreferences(DEFAULT_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", name);
        return editor.commit();
    }

    public static String getUserName() {
        SharedPreferences preferences = AndroidApplication.getContext().getSharedPreferences(DEFAULT_SHARED_PREFERENCES, 0);
        String username = preferences.getString("username", "");
        return username;
    }

    public static void clearCookie() {
        SharedPreferences preferences = AndroidApplication.getContext().getSharedPreferences(DEFAULT_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(HttpUrl.parse("http://www.wanandroid.com/").host(), new HashSet<String>());
        editor.apply();
    }
}
