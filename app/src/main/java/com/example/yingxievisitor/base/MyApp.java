package com.example.yingxievisitor.base;

import android.app.Application;
import android.content.Context;

/**
 *初始化尽量放在这边，提前初始化
 */
public class MyApp extends Application {
    public static Context myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=getApplicationContext();
    }
}
