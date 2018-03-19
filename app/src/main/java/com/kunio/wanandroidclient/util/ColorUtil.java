package com.kunio.wanandroidclient.util;

import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.support.annotation.ColorRes;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class ColorUtil {
    public static int getColorWithResId(@ColorRes int resId, Context context) {
        return context.getResources().getColor(resId);
    }

    public static int getRandomColor() {
        int red = (int) (Math.random() * 150 + 50);
        int green = (int) (Math.random() * 150 + 50);
        int blue = (int) (Math.random() * 150 + 50);
        return Color.rgb(red, green, blue);
    }
}
