package com.kunio.wanandroidclient;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class AndroidApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static Context context;
    private static List<Activity> list = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        registerActivityLifecycleCallbacks(this);
    }

    public static Context getContext() {
        return context;
    }

    public static void exitAPP() {
        for (Activity activity : list) {
            if (activity != null) {
                activity.finish();
            }
        }
//        System.out.println("最终退出activity" + list.size());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        list.add(activity);
//        System.out.println("打开一个activity" + list.size());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        System.out.println("关闭一个activity之前" + list.size());
        list.remove(activity);
//        System.out.println("关闭打开一个activity之后" + list.size());
        if (list.size() == 0) {
            //当所有activity都finish之后，将JVM退出
            System.exit(0);
        }
    }
}
