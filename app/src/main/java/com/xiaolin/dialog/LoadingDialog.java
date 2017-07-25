package com.xiaolin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaolin.R;

/**
 * Created by sjy on 2017/7/24.
 */

public class LoadingDialog extends Dialog {
    private Context context;
    private boolean cancelable = true;
    private Callback callback = null;// 本类中的接口

    // 中断访问网络接口回调
    public interface Callback {
        // 接口方法
        public void update();
    }

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialog);//设置无背景的loading
        this.context = context;
        init();// 动画效果显示登录状态
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();// 动画效果显示登录状态
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        init();// 动画效果显示登录状态
    }

    //自定义构造
    public LoadingDialog(Context context, Callback callback, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.callback = callback;
        init();  // 调用下方法,动画效果显示登录状态
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    private void init() {
        LinearLayout contentView = new LinearLayout(context);
        contentView.setMinimumHeight(60);
        contentView.setOrientation(LinearLayout.HORIZONTAL);
        contentView.setGravity(Gravity.CENTER);
        ImageView img = new ImageView(context);

        //设置自定义图片
        img.setImageResource(R.mipmap.loading);
        //设置图片旋转动画
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate_repeat);
        img.setAnimation(anim);

        //关联
        contentView.addView(img);
        setContentView(contentView);
    }

    //back键修改
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && cancelable) {
            if (callback != null) {
                callback.update();//调用本类的接口方法
            }
            this.dismiss();
        }
        return true;
    }
}

