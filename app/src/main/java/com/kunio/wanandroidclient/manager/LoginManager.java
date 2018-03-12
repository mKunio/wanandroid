package com.kunio.wanandroidclient.manager;

import com.kunio.wanandroidclient.util.SharedPreUtil;

import okhttp3.HttpUrl;

/**
 * Created by zhc on 2018/3/9 0009.
 */

public class LoginManager {
    public static boolean isLogin() {
        return SharedPreUtil.getCookies(HttpUrl.parse("http://www.wanandroid.com/")).size() > 1;
    }
}
