package com.kunio.wanandroidclient.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kunio.wanandroidclient.AndroidApplication;

/**
 * Created by zhc on 2017/8/16 0016.
 * changed by zhc on 2018/1/24
 * 以前的Toast显示方式全部设定为过时，统一Toast的显示方式
 * {@link #showMessage(String)}
 */

public class ToastUtil {
    private static Toast toast;

    /**
     * {@link #showMessage(String)}
     *
     * @param context context
     */
    @Deprecated
    public static void showError(Context context, String message) {
        if (context == null)
            return;
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(13);
        toast.show();
    }

    /**
     * {@link #showMessage(String)}
     *
     * @param context context
     */
    @Deprecated
    public static void showError(Context context) {
        if (context == null)
            return;
        Toast toast = Toast.makeText(context, "网络好像出问题了", Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(13);
        toast.show();
    }

    /**
     * {@link #showMessage(String)}
     *
     * @param context context
     */
    @Deprecated
    public static void showEmpty(Context context) {
        if (context == null)
            return;
        Toast toast = Toast.makeText(context, "暂时没有数据，下拉刷新试试吧", Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(13);
        toast.show();
    }

    /**
     * {@link #showMessage(String)}
     *
     * @param context context
     * @param content content
     */
    @Deprecated
    public static void showText(Context context, String content) {
        if (context == null)
            return;
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(13);
        toast.show();
    }

    /**
     * toast显示方式全部由此方法代替
     *
     * @param message message
     */
    public static void showMessage(String message) {
        if (toast == null) {
            toast = Toast.makeText(AndroidApplication.getContext(), message, Toast.LENGTH_SHORT);
            View view = toast.getView();
            TextView text = view.findViewById(android.R.id.message);
            text.setTextSize(13);
        } else
            toast.setText(message);
        toast.show();
    }
}
