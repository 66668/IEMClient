package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.bean.AttendStatusMonthBean;
import com.xiaolin.model.AttendModelImpl;
import com.xiaolin.model.imodel.IAttendModel;
import com.xiaolin.model.listener.OnAttendMontStateListener;
import com.xiaolin.ui.iview.IAttendMonthStateView;

/**
 * 考勤p层
 */

public class AttendPersenterImpl implements OnAttendMontStateListener {
    Context context;
    IAttendMonthStateView iAttendMonthStateView;
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
     * 月考勤状态
     */

    public void getAttendMonthState(String year, String month) {
        iAttendMonthStateView.showProgress();
        attendModel.getAttendStatusByMonth(year, month, this);

    }

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
}
