package com.xiaolin.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaolin.R;
import com.xiaolin.app.MyApplication;

/**
 * 在代码中要打印log,就直接DebugUtil.debug(....).
 * 然后如果发布的时候,就直接把这个类的DEBUG 改成false,这样所有的log就不会再打印在控制台.
 */

public class DebugUtil {
    public static final String TAG = "SJY";
    public static final boolean DEBUG = true;

    //toast弹窗提示
    public static void toastLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    //toast弹窗提示
    public static void ToastShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    //View的顶部Snackbar显示
    public static void SnackbarShow(@NonNull View view, String content) {
        Snackbar.make(view, "加载数据失败！", Snackbar.LENGTH_SHORT).show();//底部提示
    }

    // 自定义toast
    public static void toastInMiddle(Context context, String str) {
        LayoutInflater inflater = LayoutInflater.from(MyApplication.getInstance());
        View view = inflater.inflate(R.layout.toast_in_middle, null);
        TextView chapterNameTV = (TextView) view.findViewById(R.id.toast);
        chapterNameTV.setText(str);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String tag, String error) {

        if (DEBUG) {

            Log.e(tag, error);
        }
    }

    public static void e(String error) {

        if (DEBUG) {

            Log.e(TAG, error);
        }
    }
}