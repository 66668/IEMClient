package com.xiaolin.ui.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xiaolin.dialog.LoadingDialog;
import com.xiaolin.receiver.ExitAppReceiver;

/**
 * Created by sjy on 2017/7/24.
 */

public class BaseActivity extends AppCompatActivity {
    // 关闭程序的类
    private ExitAppReceiver exitAppReceiver = new ExitAppReceiver();
    // 对应的Action
    public static final String EXIT_APP_ACTION = Intent.ACTION_CLOSE_SYSTEM_DIALOGS;//某一个包名也可？

    //自定义弹窗 加载
    public LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        registerExitRecevier();// 先注册广播
    }

    private void initView() {
        //弹窗显示登录状态
        loadingDialog = new LoadingDialog(BaseActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);//弹窗之外触摸无效
        loadingDialog.setCancelable(true);//true:可以按返回键back取消

        //
    }

    //注册 退出功能 广播
    private void registerExitRecevier() {
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        this.registerReceiver(exitAppReceiver, exitFilter);
    }

    //onDestroy调用
    private void unRegisterExitReceiver() {
        this.unregisterReceiver(exitAppReceiver);//取消注册
    }

    /**
     * Activity
     *
     * @param newClass
     */
    // (1)页面跳转重载
    public void startActivity(Class<?> newClass) {
        Intent intent = new Intent(this, newClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // (2)
    public void startActivity(Class<?> newClass, Bundle extras) {
        Intent intent = new Intent(this, newClass);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出程序的广播
        unRegisterExitReceiver();
    }
    /**
     * sendMessage的重载
     */
    // 01
    protected void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    // 02
    protected void sendMessage(int what) {
        handler.sendEmptyMessage(what);
    }

    // 03
    public void sendMessage(int what, Object obj) {
        handler.sendMessage(handler.obtainMessage(what, obj));
    }

    /**
     * handler sendMessage的处理
     */
    @SuppressLint("HandlerLeak") // 确保类内部的handler不含有对外部类的隐式引用
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 调用下边的方法处理信息
            BaseActivity.this.handleMessage(msg);
        }
    };

    protected void handleMessage(Message msg) {
        switch (msg.what) {

        }
    }
}
