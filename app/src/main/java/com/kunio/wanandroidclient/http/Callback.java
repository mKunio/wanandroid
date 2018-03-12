package com.kunio.wanandroidclient.http;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface Callback<T> {
    void onSuccess(T result);

    void onFailed(String msg);
}
