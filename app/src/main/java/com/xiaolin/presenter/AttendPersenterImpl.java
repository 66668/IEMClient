package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.bean.AttendDaysOFMonthBean;
import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.bean.AttendStatusMonthBean;
import com.xiaolin.model.AttendModelImpl;
import com.xiaolin.model.imodel.IAttendModel;
import com.xiaolin.model.listener.OnAttendDayDetailListener;
import com.xiaolin.model.listener.OnAttendDayOfMonthStateListener;
import com.xiaolin.model.listener.OnAttendMonthStateListener;
import com.xiaolin.ui.iview.IAttendDayView;
import com.xiaolin.ui.iview.IAttendMonthStateView;

import java.util.ArrayList;

/**
 * 考勤p层
 */

public class AttendPersenterImpl {
    Context context;
    IAttendMonthStateView iAttendMonthStateView;
    IAttendDayView attendDayView;
    IAttendModel attendModel;

    /**
     * 月考勤状态
     *
     * @param context
     * @param view
     */
    public AttendPersenterImpl(Context context, IAttendMonthStateView view) {
        this.context = context;
        this.iAttendMonthStateView = view;
        attendModel = new AttendModelImpl();
    }


    public AttendPersenterImpl(Context context, IAttendDayView view) {
        this.context = context;
        this.attendDayView = view;
        attendModel = new AttendModelImpl();
    }

    /**
     * 月考勤状态(月份使用)
     */

    public synchronized void getAttendMonthState(String year, String month) {
        iAttendMonthStateView.showProgress();
        attendModel.getAttendStatusByMonth(year, month, new OnAttendMonthStateListener() {
            @Override
            public void onAttendMontStateSuccess(AttendStatusMonthBean bean) {
                iAttendMonthStateView.hideProgress();
                iAttendMonthStateView.postSuccessShow(bean);
            }

            @Override
            public void onAttendMontStateFailed(String msg, Exception e) {
                iAttendMonthStateView.hideProgress();
                iAttendMonthStateView.postFaild(msg, e);
            }
        });

    }

    /**
     * 月考勤状态(月份使用)
     * 三个月的记录(无加载dialog)
     */

    public synchronized void getAttendMonthOfThreeState(String year, String month) {
        iAttendMonthStateView.showProgress();
        attendModel.getAttendSateOFThree(year, month, new OnAttendDayOfMonthStateListener() {

            @Override
            public void onAttendDaysSuccess(ArrayList<AttendDaysOFMonthStateBean> list) {
                iAttendMonthStateView.hideProgress();
                iAttendMonthStateView.cacheDateSuccess(list);
            }

            @Override
            public void onAttendDaysFailed(String msg, Exception e) {
                iAttendMonthStateView.hideProgress();
                iAttendMonthStateView.postFaild(msg, e);
            }
        });

    }

    /**
     * 获取月考勤记录
     */
    //    public void getAttendListOfMonth(String year, String month) {
    //        attendDayView.showProgress();
    //        attendModel.getAttendList(year, month, new OnAttendDayOfMonthListener() {
    //            @Override
    //            public void onAttendDaysSuccess(ArrayList<AttendDaysOFMonthBean> list) {
    //                attendDayView.hideProgress();
    //                attendDayView.postSuccessUse(list);
    //            }
    //
    //            @Override
    //            public void onAttendDaysFailed(String msg, Exception e) {
    //                attendDayView.hideProgress();
    //                attendDayView.postFaild(msg, e);
    //            }
    //        });
    //    }

    /**
     * 获取月考勤记录的状态记录（月日历使用）
     */
    public synchronized void getAttendStateList(String year, String month) {
        attendDayView.showProgress();
        attendModel.getAttendSateList(year, month, new OnAttendDayOfMonthStateListener() {

            @Override
            public void onAttendDaysSuccess(ArrayList<AttendDaysOFMonthStateBean> list) {
                attendDayView.hideProgress();
                attendDayView.postSuccessUse(list);
            }

            @Override
            public void onAttendDaysFailed(String msg, Exception e) {
                attendDayView.hideProgress();
                attendDayView.postFaild(msg, e);
            }
        });
    }

    /**
     * 获取日历日记录
     */
    public synchronized void getAttendDayDetail(String year, String month, String day) {
        attendDayView.showProgress();
        attendModel.getAttendDetailDay(year, month, day, new OnAttendDayDetailListener() {
            @Override
            public void onAttendDaysSuccess(AttendDaysOFMonthBean bean) {
                attendDayView.hideProgress();
                attendDayView.postSuccessShow(bean);
            }

            @Override
            public void onAttendDaysFailed(String msg, Exception e) {
                attendDayView.hideProgress();
                attendDayView.postFaild(msg, e);
            }
        });

    }

}
