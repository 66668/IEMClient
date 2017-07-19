package com.xiaolin.bean.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.linzhi.isis.R;
import com.linzhi.isis.app.Constants;
import com.linzhi.isis.http.MyHttpService;
import com.linzhi.isis.http.cache.ACache;
import com.linzhi.isis.ui.ConferenceActivity;
import com.linzhi.isis.utils.SPUtils;
import com.linzhi.isis.utils.ToastUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登录监听
 * Created by sjy on 2017/4/18.
 */

public class LoginBtnListener {
    final String TAG = "Login";
    private User user;
    private Activity context;
    private ProgressDialog dialog;
    private ACache aCache;//缓存

    public LoginBtnListener(User user, Activity context, ProgressDialog dialog) {
        this.context = context;
        this.user = user;
        this.dialog = dialog;
    }

    //登录事件
    public void onBtnClick(View view) {
        //判断是否有网
        Log.d(TAG, "onBtnClick: user.userName=" + user.userName + "--user.password=" + user.password);
        //非空
        if (TextUtils.isEmpty(user.userName) || TextUtils.isEmpty(user.password)) {
            showToast("用户名或密码为空");
        } else {
            dialog.show();
            //异步操作

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    forLogin();
                }
            }, 1000);
        }
    }

    private void forLogin() {

        //        //源码方式
         MyHttpService.Builder.getHttpServer().login(user.userName, user.password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean<UserInfoBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(LoginBean<UserInfoBean> userInfoBeanLoginBean) {
                        Log.d(TAG, "onNext: " + userInfoBeanLoginBean.getCode() + userInfoBeanLoginBean.getResult().toString());
                        if (userInfoBeanLoginBean.getCode().equals("1")) {//code=1


                            //sp 缓存登录信息
                            SPUtils.putString(Constants.USRENAME,user.userName);
                            SPUtils.putString(Constants.PASSWORD,user.password);

                            //缓存 保存返回信息
                            aCache = ACache.get(context);
                            aCache.put(Constants.STORE_USER_ID, userInfoBeanLoginBean.getResult().getStoreUserId());
                            aCache.put(Constants.STORE_ID, userInfoBeanLoginBean.getResult().getStoreID());
                            aCache.put(Constants.EMPLOYEE_ID, userInfoBeanLoginBean.getResult().getEmployeeID());


                            dialog.dismiss();

                            //跳转界面
                            Intent intent = new Intent(context, ConferenceActivity.class);
                            context.startActivity(intent);
                            //自定义 两个 Activity 切换时的动画
                            context.overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                            context.finish();

                        } else {//code = 0
                            dialog.dismiss();
                            ToastUtils.ShortToast(context, userInfoBeanLoginBean.getMessage());
                        }
                    }
                });

    }

    private void showToast(final String content) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

}
