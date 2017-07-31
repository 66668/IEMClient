package com.xiaolin.model;

import android.text.TextUtils;

import com.xiaolin.app.Constants;
import com.xiaolin.bean.BaseBean;
import com.xiaolin.bean.CommonBean;
import com.xiaolin.bean.UpgradeBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.IMainModel;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.model.listener.OnUpgradeListener;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjy on 2017/7/31.
 */

public class MainModelImpl implements IMainModel {
    private static final String TAG = "MainActivity";

    /**
     * 检查更新
     *
     * @param listener
     */
    @Override
    public void checkUpdate(final OnUpgradeListener listener) {
        MyHttpService.Builder.getHttpServer().checkUpdate()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<UpgradeBean>>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.e(TAG, "MainModelImpl--onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.e(TAG, "MainModelImpl--onError: " + e.toString());
                        listener.onFailed("获取数据异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<UpgradeBean> bean) {
                        DebugUtil.d(TAG, "LoginModelImpl-onNext");
                        DebugUtil.d(TAG, bean.toString());


                        //处理返回结果
                        if (bean.getCode().equals("1")) {
                            //code = 1
                            listener.onSuccess(bean.getResult());
                        } else if (bean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onFailed(bean.getMessage(), new Exception("没有更新信息！"));
                        } else {

                        }
                    }
                });

    }

    /**
     * 退出
     *
     * @param listener
     */
    @Override
    public void quit(final OnCommonListener listener) {
        String storeId = SPUtils.getString(Constants.STORE_ID);
        String userName = SPUtils.getString(Constants.USRENAME);
        String deviceId = SPUtils.getString(Constants.DEVICEID);

        if (TextUtils.isEmpty(storeId) || TextUtils.isEmpty(storeId) || TextUtils.isEmpty(storeId)) {
            listener.onFailed("退出失败，sp没有值！", new Exception("MainModelImpl--quit--sp保存失败，没有获取到保存数值！"));
            DebugUtil.e("MainModelImpl--quit--sp保存失败，没有获取到保存数值！");
            return;
        }
        MyHttpService.Builder.getHttpServer().quit(storeId, userName, deviceId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed("onError！", (Exception) e);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        DebugUtil.d(TAG,bean.toString());
                        if (bean.getCode().equals("1")) {
                            listener.onSuccess(bean.getMessage());
                        }else{
                            listener.onFailed("退出失败！", new Exception("code=0"));
                        }

                    }
                });

    }
}
