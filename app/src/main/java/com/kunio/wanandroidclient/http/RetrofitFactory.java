package com.kunio.wanandroidclient.http;

import androidx.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.kunio.wanandroidclient.util.SharedPreUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhc on 2018/3/2 0002.
 */

public class RetrofitFactory {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
                    // 当只有登陆的请求会重新存储cookie，利用拦截器判断登陆请求，在拦截器中存储，此处不存储
                }

                @Override
                public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                    Set<String> cookies = SharedPreUtil.getCookies(url);
                    List<Cookie> cookie = new ArrayList<>();
                    for (String s : cookies) {
                        cookie.add(Cookie.parse(url, s));
                    }
                    return cookie;
                }
            })
            .addInterceptor(new SaveCookiesInterceptor())
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
    private static Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static Retrofit getDefault() {
        return retrofit;
    }
}
