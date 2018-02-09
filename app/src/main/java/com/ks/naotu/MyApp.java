package com.ks.naotu;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Admin on 2018/2/7 0007 10:03.
 * Author: kang
 * Email: kangsafe@163.com
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
