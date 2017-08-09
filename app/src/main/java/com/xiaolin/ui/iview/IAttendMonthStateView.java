package com.xiaolin.ui.iview;

import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.bean.AttendStatusMonthBean;

import java.util.ArrayList;

/**
 * 月考勤view
 */

public interface IAttendMonthStateView {
    void showProgress();

    void hideProgress();

    void postSuccessShow(AttendStatusMonthBean bean);

    void postFaild(String msg, Exception e);

    void cacheDateSuccess(ArrayList<AttendDaysOFMonthStateBean> listStateBean);
}
