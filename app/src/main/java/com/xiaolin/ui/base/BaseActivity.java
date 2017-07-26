package com.xiaolin.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xiaolin.dialog.LoadingDialog;

/**
 * Created by sjy on 2017/7/24.
 */

public class BaseActivity extends AppCompatActivity {
    public LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        //弹窗显示登录状态
        loadingDialog = new LoadingDialog(BaseActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);//弹窗之外触摸无效
        loadingDialog.setCancelable(true);//true:可以按返回键back取消
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

}
