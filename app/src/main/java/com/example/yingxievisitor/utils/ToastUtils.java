package com.example.yingxievisitor.utils;

import android.widget.Toast;

import com.example.yingxievisitor.app.MyApp;

public class ToastUtils {

    public static void show(CharSequence text) {
        Toast.makeText(MyApp.myApplication, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong( CharSequence text) {
        Toast.makeText(MyApp.myApplication, text, Toast.LENGTH_LONG).show();
    }

}
