package com.example.myviewpaper;

import android.app.Application;
import android.content.Context;

/**
 * @author SYSTEM  on  2018/07/28 at 21:16
 * 邮箱：913305160@qq.com
 */
public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
