package com.xiaolin.model;

import android.text.TextUtils;

import com.xiaolin.app.Constants;
import com.xiaolin.bean.AttendStatusMonthBean;
import com.xiaolin.bean.CommonBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.IAttendModel;
import com.xiaolin.model.listener.OnAttendMontStateListener;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;

import java.io.File;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * m层，m接口层
 */

public class AttendModelImpl implements IAttendModel {
    private final String TAG = "calendar";

    /**
     * 获取月考勤状态
     */

    @Override
    public void getAttendStatusByMonth(String year, String month, final OnAttendMontStateListener listener) {
        String employeeID = SPUtils.getString(Constants.EMPLOYEE_ID);
        String storeId = SPUtils.getString(Constants.STORE_ID);
        if (TextUtils.isEmpty(employeeID) || TextUtils.isEmpty(storeId)) {
            DebugUtil.e("数据存储异常，没有获取到存储数据！");
            return;
        }

        MyHttpService.Builder.getHttpServer().getAttendStateMonth(storeId, employeeID, year, month)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<AttendStatusMonthBean>>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.d(TAG, "onError");
                        listener.onAttendMontStateFailed("数据异常！", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<AttendStatusMonthBean> bean) {
                        DebugUtil.d(TAG, "onNext");
                        if (bean.getCode().equals("1")) {
                            listener.onAttendMontStateSuccess(bean.getResult());
                        } else {
                            listener.onAttendMontStateFailed(bean.getMessage(), new Exception("没有该月的数据！"));
                        }

                    }
                });

    }


    /**
     *
     */
    public void maddVisitor(String jsonstr, File file, OnCommonListener listener) {

    }
}
