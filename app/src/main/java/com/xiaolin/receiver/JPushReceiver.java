package com.xiaolin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xiaolin.app.Constants;
import com.xiaolin.ui.LoginActivity;
import com.xiaolin.ui.VisitorActivity;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 不管程序是否退出，都接受通知栏信息
 * <p>
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getExtras();
        String myContent = printBundle(bundle);

        //页面跳转
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            transferTo(context, myContent);
        }
    }


    // 获取自己需要的数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_ALERT)) {
                sb.append(bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void transferTo(Context context, String content) {

        DebugUtil.d(TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+content);

        if (SPUtils.getString(Constants.STORE_ID) == null || TextUtils.isEmpty(SPUtils.getString(Constants.STORE_ID))) {
            //在程序退出状态下获取通知，一些值无法获取，需要重新登录
            Intent intent = new Intent();
            intent.setClass(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return;
        }

        if (content.contains("新的申请需要审批")) {
            Intent intent = new Intent();
            intent.setClass(context, VisitorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}
