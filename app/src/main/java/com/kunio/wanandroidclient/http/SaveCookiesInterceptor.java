package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.util.SharedPreUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhc on 2018/3/9 0009.
 */

public class SaveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
//        List<String> headers = response.headers("Set-Cookie");
        if (request.headers().names().contains("m_login_request")) {
            List<Cookie> cookies = Cookie.parseAll(request.url(), response.headers());
            if (!cookies.isEmpty()) {
                Set<String> cookie = new HashSet<>();
                for (Cookie c : cookies) {
                    cookie.add(c.toString());
                }
                SharedPreUtil.saveCookies(request.url(),cookie);
            }
        }
//        for (Cookie cookie : cookies) {
//            String string = cookie.toString();
//            System.out.println("toString:"+string);
//            System.out.println("parse:"+Cookie.parse(request.url(),string));
//        }
        return response;
    }
}
