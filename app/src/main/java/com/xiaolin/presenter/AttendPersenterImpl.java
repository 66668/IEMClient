package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.bean.AttendDaysOFMonthBean;
import com.xiaolin.bean.AttendStatusMonthBean;
import com.xiaolin.model.AttendModelImpl;
import com.xiaolin.model.imodel.IAttendModel;
import com.xiaolin.model.listener.OnAttendDayDetailListener;
import com.xiaolin.model.listener.OnAttendDayOfMonthListener;
import com.xiaolin.model.listener.OnAttendMonthStateListener;
import com.xiaolin.ui.iview.IAttendDayView;
import com.xiaolin.ui.iview.IAttendMonthStateView;

import java.util.List;

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

    /**
     * 日历日考勤
     *
     * @param context
     * @param view
     */
    public AttendPersenterImpl(Context context, IAttendDayView view) {
        this.context = context;
        this.attendDayView = view;
        attendModel = new AttendModelImpl();
    }

    /**
     * 月考勤状态
     */

    public void getAttendMonthState(String year, String month) {
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
     * 获取月考勤记录
     */
    public void getAttendListOfMonth(String year, String month) {
        attendDayView.showProgress();
        attendModel.getAttendList(year, month, new OnAttendDayOfMonthListener() {
            @Override
            public void onAttendDaysSuccess(List<AttendDaysOFMonthBean> list) {
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
    public void getAttendDayDetail(String year, String month, String day) {
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
