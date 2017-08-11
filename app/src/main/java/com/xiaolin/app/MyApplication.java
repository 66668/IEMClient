package com.xiaolin.app;

import android.app.Application;

import com.xiaolin.http.HttpUtils;
import com.xiaolin.utils.DebugUtil;

/**
 * Created by sjy on 2017/4/18.
 */

public class MyApplication extends Application {
    private final static String sdcardDirName = "ISS";//内存根目录名
    private static MyApplication MyApplication;
    private boolean isLogin = false;

    public static MyApplication getInstance() {
        return MyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;
        //正式打包，将DebugUtil.DEBUG值设为 false
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}


