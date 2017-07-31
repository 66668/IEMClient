package com.xiaolin.model;

import android.text.TextUtils;

import com.xiaolin.app.Constants;
import com.xiaolin.bean.BaseBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.IChangePsModel;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjy on 2017/7/31.
 */

public class ChangePsModelImpl implements IChangePsModel {
    private static final String TAG = "changeps";

    String storeUserId = SPUtils.getString(Constants.STORE_USER_ID);

    @Override
    public void postChangePs(String old, String newps, final OnCommonListener listener) {
        if (TextUtils.isEmpty(storeUserId)) {
            listener.onFailed("登录保存的参数获取失败！", new Exception("storeUserId获取失败"));
            return;

        }
        MyHttpService.Builder.getHttpServer().changeps(storeUserId, old, newps)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "ChangePsModelImpl-onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.d(TAG, "ChangePsModelImpl-onError");
                        DebugUtil.e(e.toString());
                        listener.onFailed("提交异常", (Exception) e);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        DebugUtil.d(TAG, "ChangePsModelImpl-onNext");
                        DebugUtil.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode().equals("1")) {
                            //code = 1
                            listener.onSuccess(bean.getMessage());
                        } else if (bean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onFailed(bean.getMessage(), new Exception("修改密码失败！"));
                        }

                    }
                });

    }
}
