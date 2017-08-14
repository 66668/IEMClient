package com.xiaolin.app;

import android.app.Application;

import com.xiaolin.http.HttpUtils;
import com.xiaolin.utils.DebugUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by sjy on 2017/4/18.
 */

public class MyApplication extends Application {
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

        // 极光推送 SDK初始化
        JPushInterface.setDebugMode(true);//设置打印日志，测试用
        JPushInterface.init(this);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}


