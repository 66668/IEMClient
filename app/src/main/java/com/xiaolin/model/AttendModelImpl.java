package com.xiaolin.model;

import android.text.TextUtils;

import com.xiaolin.app.Constants;
import com.xiaolin.bean.AttendDaysOFMonthBean;
import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.bean.AttendStatusMonthBean;
import com.xiaolin.bean.CommonBean;
import com.xiaolin.bean.CommonListBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.IAttendModel;
import com.xiaolin.model.listener.OnAttendDayDetailListener;
import com.xiaolin.model.listener.OnAttendDayOfMonthStateListener;
import com.xiaolin.model.listener.OnAttendMonthStateListener;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;

import java.util.ArrayList;

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
    public void getAttendStatusByMonth(String year, String month, final OnAttendMonthStateListener listener) {
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
                        DebugUtil.d(TAG, "getAttendStatusByMonth--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.d(TAG, "getAttendStatusByMonth--onError");
                        listener.onAttendMontStateFailed("数据异常！", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<AttendStatusMonthBean> bean) {
                        DebugUtil.d(TAG, "getAttendStatusByMonth--onNext");

                        if (bean.getCode().equals("1")) {
                            listener.onAttendMontStateSuccess(bean.getResult());
                        } else {
                            listener.onAttendMontStateFailed(bean.getMessage(), new Exception("没有该月的数据！"));
                        }

                    }
                });

    }

    /**
     * 获取日历月记录的状态记录
     *
     * @param year
     * @param month
     * @param listener
     */

    @Override
    public void getAttendSateList(String year, String month, final OnAttendDayOfMonthStateListener listener) {
        String employeeID = SPUtils.getString(Constants.EMPLOYEE_ID);
        String storeId = SPUtils.getString(Constants.STORE_ID);
        if (TextUtils.isEmpty(employeeID) || TextUtils.isEmpty(storeId)) {
            DebugUtil.e("数据存储异常，没有获取到存储数据！");
            return;
        }
        MyHttpService.Builder.getHttpServer().getAttendStateList(storeId, employeeID, year, month)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<AttendDaysOFMonthStateBean>>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "getAttendSateList--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.e(TAG, "getAttendSateList--onError:" + e.toString());
                        listener.onAttendDaysFailed("获取数据异常", (Exception) e);

                    }

                    @Override
                    public void onNext(CommonListBean<AttendDaysOFMonthStateBean> listBean) {
                        DebugUtil.d(TAG, "getAttendSateList--onNext");


                        if (listBean.getCode().equals("0")) {
                            listener.onAttendDaysFailed(listBean.getMessage(), new Exception("没有该月的状态数据！"));
                        } else {
                            listener.onAttendDaysSuccess((ArrayList<AttendDaysOFMonthStateBean>) listBean.getResult());
                        }
                    }
                });


    }

    /**
     * 获取日历日记录
     */

    @Override
    public void getAttendDetailDay(String year, String month, String day, final OnAttendDayDetailListener listener_2) {
        String employeeID = SPUtils.getString(Constants.EMPLOYEE_ID);
        String storeId = SPUtils.getString(Constants.STORE_ID);
        if (TextUtils.isEmpty(employeeID) || TextUtils.isEmpty(storeId)) {
            DebugUtil.e("数据存储异常，没有获取到存储数据！");
            return;
        }
        MyHttpService.Builder.getHttpServer().getDayDetail(storeId, employeeID, year, month, day)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<AttendDaysOFMonthBean>>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "getAttendDetailDay--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.e(TAG, "getAttendDetailDay--onError:" + e.toString());
                        listener_2.onAttendDaysFailed("获取数据异常", (Exception) e);

                    }

                    @Override
                    public void onNext(CommonBean<AttendDaysOFMonthBean> bean) {
                        DebugUtil.d(TAG, "getAttendDetailDay--onNext");


                        if (bean.getCode().equals("0")) {
                            listener_2.onAttendDaysFailed(bean.getMessage(), new Exception("没有该日的数据！"));
                        } else {
                            listener_2.onAttendDaysSuccess(bean.getResult());
                        }
                    }
                });
    }

}
