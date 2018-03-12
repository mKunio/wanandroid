package com.kunio.wanandroidclient.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class NetUtil {
    public static boolean isNetConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        return !(networkinfo == null || !networkinfo.isAvailable());
    }
}
